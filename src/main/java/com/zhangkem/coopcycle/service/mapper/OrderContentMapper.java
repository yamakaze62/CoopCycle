package com.zhangkem.coopcycle.service.mapper;

import com.zhangkem.coopcycle.domain.Order;
import com.zhangkem.coopcycle.domain.OrderContent;
import com.zhangkem.coopcycle.domain.Product;
import com.zhangkem.coopcycle.service.dto.OrderContentDTO;
import com.zhangkem.coopcycle.service.dto.OrderDTO;
import com.zhangkem.coopcycle.service.dto.ProductDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderContent} and its DTO {@link OrderContentDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderContentMapper extends EntityMapper<OrderContentDTO, OrderContent> {
    @Mapping(target = "products", source = "products", qualifiedByName = "productIdSet")
    @Mapping(target = "order", source = "order", qualifiedByName = "orderId")
    OrderContentDTO toDto(OrderContent s);

    @Mapping(target = "removeProduct", ignore = true)
    OrderContent toEntity(OrderContentDTO orderContentDTO);

    @Named("productId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductDTO toDtoProductId(Product product);

    @Named("productIdSet")
    default Set<ProductDTO> toDtoProductIdSet(Set<Product> product) {
        return product.stream().map(this::toDtoProductId).collect(Collectors.toSet());
    }

    @Named("orderId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrderDTO toDtoOrderId(Order order);
}
