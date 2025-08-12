package com.jp.orpha.whatsapp_order.services.impl;

import com.jp.orpha.whatsapp_order.dtos.ProductDto;
import com.jp.orpha.whatsapp_order.entities.Product;
import com.jp.orpha.whatsapp_order.exceptions.NotFoundException;
import com.jp.orpha.whatsapp_order.mappers.ProductMapper;
import com.jp.orpha.whatsapp_order.repositories.ProductRepository;
import com.jp.orpha.whatsapp_order.services.ProductService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repo;
    private final ProductMapper mapper;

    @Override
    public ProductDto create(ProductDto dto) {
        Product entity = mapper.toEntity(dto);
        entity.setId(null);
        return mapper.toDto(repo.save(entity));
    }

    @Override
    public ProductDto update(Long id, ProductDto dto) {
        Product e = repo.findById(id).orElseThrow(() -> new NotFoundException("Producto no encontrado"));
        e.setName(dto.getName());
        e.setDescription(dto.getDescription());
        e.setPrice(dto.getPrice());
        e.setCurrency(dto.getCurrency());
        e.setAvailable(Boolean.TRUE.equals(dto.getAvailable()));
        e.setPosition(dto.getPosition());
        return mapper.toDto(repo.save(e));
    }

    @Override
    public void delete(Long id) { repo.deleteById(id); }

    @Override
    @Transactional(readOnly = true)
    public ProductDto get(Long id) {
        return mapper.toDto(repo.findById(id).orElseThrow(() -> new NotFoundException("Producto no encontrado")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> list(boolean onlyAvailable) {
        return (onlyAvailable ? repo.findAllByAvailableTrueOrderByPositionAscIdAsc()
                : repo.findAllByOrderByPositionAscIdAsc())
                .stream().map(mapper::toDto).toList();
    }
}

