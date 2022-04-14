package com.zhangkem.coopcycle.service.mapper;

import com.zhangkem.coopcycle.domain.Customer;
import com.zhangkem.coopcycle.domain.Deliverer;
import com.zhangkem.coopcycle.domain.Order;
import com.zhangkem.coopcycle.domain.Restaurant;
import com.zhangkem.coopcycle.service.dto.CustomerDTO;
import com.zhangkem.coopcycle.service.dto.DelivererDTO;
import com.zhangkem.coopcycle.service.dto.OrderDTO;
import com.zhangkem.coopcycle.service.dto.RestaurantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Order} and its DTO {@link OrderDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {
    @Mapping(target = "deliverer", source = "deliverer", qualifiedByName = "delivererId")
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerId")
    @Mapping(target = "restaurant", source = "restaurant", qualifiedByName = "restaurantId")
    OrderDTO toDto(Order s);

    @Named("delivererId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DelivererDTO toDtoDelivererId(Deliverer deliverer);

    @Named("customerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoCustomerId(Customer customer);

    @Named("restaurantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RestaurantDTO toDtoRestaurantId(Restaurant restaurant);
}
