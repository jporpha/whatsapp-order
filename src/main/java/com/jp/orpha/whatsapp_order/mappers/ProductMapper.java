package com.jp.orpha.whatsapp_order.mappers;

import com.jp.orpha.whatsapp_order.dtos.ProductDto;
import com.jp.orpha.whatsapp_order.dtos.ProductItemDto;
import com.jp.orpha.whatsapp_order.dtos.PublicCatalogDto;
import com.jp.orpha.whatsapp_order.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toDto(Product entity);

    @Mapping(target = "createdAt", ignore = true)
    Product toEntity(ProductDto dto);

    // Product -> ProductItemDto
    ProductItemDto toPublicItem(Product entity);

    // List<Product> -> List<ProductItemDto>
    List<ProductItemDto> toPublicItems(List<Product> entities);

    // ⚠️ Implementación manual para evitar el error Iterable -> non-iterable
    default PublicCatalogDto toPublicCatalog(List<Product> entities) {
        PublicCatalogDto dto = new PublicCatalogDto();
        dto.setProducts(toPublicItems(entities)); // reutiliza el mapping de lista
        // storeName y currency se setean en el service, como ya lo haces
        return dto;
    }
}

