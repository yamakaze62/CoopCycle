package com.zhangkem.coopcycle.service.mapper;

import com.zhangkem.coopcycle.domain.Product;
import com.zhangkem.coopcycle.domain.Restaurant;
import com.zhangkem.coopcycle.service.dto.ProductDTO;
import com.zhangkem.coopcycle.service.dto.RestaurantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {
    @Mapping(target = "restaurant", source = "restaurant", qualifiedByName = "restaurantId")
    ProductDTO toDto(Product s);

    @Named("restaurantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RestaurantDTO toDtoRestaurantId(Restaurant restaurant);
}
