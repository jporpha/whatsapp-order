package com.jp.orpha.whatsapp_order.services.impl;

import com.jp.orpha.whatsapp_order.dtos.ItemDto;
import com.jp.orpha.whatsapp_order.dtos.OrderDraftRequestDto;
import com.jp.orpha.whatsapp_order.dtos.OrderDraftResponseDto;
import com.jp.orpha.whatsapp_order.dtos.PublicCatalogDto;
import com.jp.orpha.whatsapp_order.entities.OrderDraft;
import com.jp.orpha.whatsapp_order.entities.OrderDraftItem;
import com.jp.orpha.whatsapp_order.entities.Product;
import com.jp.orpha.whatsapp_order.entities.Settings;
import com.jp.orpha.whatsapp_order.entities.MessageTemplate;
import com.jp.orpha.whatsapp_order.exceptions.BusinessException;
import com.jp.orpha.whatsapp_order.exceptions.NotFoundException;
import com.jp.orpha.whatsapp_order.mappers.ProductMapper;
import com.jp.orpha.whatsapp_order.repositories.MessageTemplateRepository;
import com.jp.orpha.whatsapp_order.repositories.OrderDraftRepository;
import com.jp.orpha.whatsapp_order.repositories.ProductRepository;
import com.jp.orpha.whatsapp_order.repositories.SettingsRepository;
import com.jp.orpha.whatsapp_order.services.OrderDraftService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderDraftServiceImpl implements OrderDraftService {

    private final ProductRepository productRepository;
    private final SettingsRepository settingsRepository;
    private final MessageTemplateRepository templateRepository;
    private final OrderDraftRepository orderDraftRepository;
    private final ProductMapper productMapper;

    private static final DecimalFormat CLP_FORMAT;
    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("es", "CL"));
        symbols.setGroupingSeparator('.'); // separador de miles
        symbols.setDecimalSeparator(',');  // decimal (no lo usaremos, pero queda configurado)
        CLP_FORMAT = new DecimalFormat("#,###", symbols);
    }

    @Override
    @Transactional(readOnly = true)
    public PublicCatalogDto getPublicCatalog() {
        Settings s = settingsRepository.findAll().stream().findFirst()
                .orElseGet(() -> {
                    Settings def = new Settings();
                    def.setStoreName("Mi Tienda");
                    def.setCurrency("CLP");
                    def.setAdminPhone("56912345678");
                    return def;
                });
        var products = productRepository.findAllByAvailableTrueOrderByPositionAscIdAsc();
        PublicCatalogDto dto = productMapper.toPublicCatalog(products);
        dto.setStoreName(s.getStoreName());
        dto.setCurrency(s.getCurrency());
        return dto;
    }

    @Override
    public OrderDraftResponseDto generateOrder(OrderDraftRequestDto request) {
        if (request.getItems().isEmpty()) throw new BusinessException("El pedido debe incluir al menos un ítem");

        // Cargar productos y validar disponibilidad
        Map<Long, Product> productMap = new HashMap<>();
        BigDecimal total = BigDecimal.ZERO;
        List<OrderDraftItem> items = new ArrayList<>();

        for (ItemDto it : request.getItems()) {
            Product p = productRepository.findById(it.getProductId())
                    .orElseThrow(() -> new NotFoundException("Producto no encontrado: " + it.getProductId()));
            if (!p.isAvailable()) throw new BusinessException("Producto no disponible: " + p.getName());
            BigDecimal subtotal = p.getPrice().multiply(BigDecimal.valueOf(it.getQuantity()));
            total = total.add(subtotal);

            OrderDraftItem odi = new OrderDraftItem();
            odi.setProductId(p.getId());
            odi.setProductNameSnapshot(p.getName());
            odi.setUnitPriceSnapshot(p.getPrice());
            odi.setQuantity(it.getQuantity());
            odi.setSubtotal(subtotal);
            items.add(odi);
            productMap.put(p.getId(), p);
        }


        Settings settings = settingsRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new BusinessException("Debe configurar Settings antes de generar pedidos"));



        // Obtener plantilla activa o usar una por defecto
        String template = templateRepository.findFirstByActiveTrueOrderByIdAsc()
                .map(MessageTemplate::getTemplateText)
                .orElse("""
                        *Pedido de {storeName}*
                        Cliente: {customerName} ({customerPhone})
                        Entrega: {deliveryMode}{addressLine}
                        --------
                        {items}
                        --------
                        Total: {total} {currency}
                        Nota: {note}
                        """);

        String itemsText = buildItemsText(items, settings.getCurrency());
        String totalFormatted = CLP_FORMAT.format(total);
        String addressLine = (request.getAddress() != null && !request.getAddress().isBlank())
                ? " - Dirección: " + request.getAddress() : "";
        String note = (request.getNote() == null || request.getNote().isBlank()) ? "-" : request.getNote();

        String message = template
                .replace("{storeName}", settings.getStoreName())
                .replace("{customerName}", request.getCustomerName())
                .replace("{customerPhone}", request.getCustomerPhone())
                .replace("{deliveryMode}", request.getDeliveryMode().name())
                .replace("{addressLine}", addressLine)
                .replace("{items}", itemsText)
                .replace("{total}", totalFormatted)
                .replace("{currency}", settings.getCurrency())
                .replace("{note}", note);

        String encoded = URLEncoder.encode(message, StandardCharsets.UTF_8);
        String link = "https://wa.me/" + settings.getAdminPhone() + "?text=" + encoded;

        OrderDraft draft = new OrderDraft();
        draft.setCustomerName(request.getCustomerName());
        draft.setCustomerPhone(request.getCustomerPhone());
        draft.setAddress(request.getAddress());
        draft.setDeliveryMode(request.getDeliveryMode());
        draft.setNote(request.getNote());
        draft.setTotal(total);
        draft.setGeneratedText(message);
        draft.setWhatsAppLink(link);

        // relacionar items -> draft
        OrderDraft finalDraft = draft;
        items.forEach(i -> i.setOrderDraft(finalDraft));
        draft.setItems(items);

        draft = orderDraftRepository.save(draft);

        OrderDraftResponseDto resp = new OrderDraftResponseDto();
        resp.setOrderId(draft.getId());
        resp.setGeneratedText(draft.getGeneratedText());
        resp.setWhatsAppLink(draft.getWhatsAppLink());
        resp.setTotal(draft.getTotal());

        List<ItemDto> itemDtos = items.stream().map(i -> {
            ItemDto dto = new ItemDto();
            dto.setProductId(i.getProductId());
            dto.setProductName(i.getProductNameSnapshot());
            dto.setQuantity(i.getQuantity());
            dto.setUnitPrice(i.getUnitPriceSnapshot());
            dto.setSubtotal(i.getSubtotal());
            return dto;
        }).toList();

        resp.setItems(itemDtos);

        return resp;
    }

    private String buildItemsText(List<OrderDraftItem> items, String currency) {
        StringBuilder sb = new StringBuilder();
        for (OrderDraftItem i : items) {
            sb.append("- ").append(i.getProductNameSnapshot())
                    .append(" x ").append(i.getQuantity())
                    .append(" = ")
                    .append(CLP_FORMAT.format(i.getSubtotal()))
                    .append(" ").append(currency)
                    .append("\n");
        }
        return sb.toString().trim();
    }
}