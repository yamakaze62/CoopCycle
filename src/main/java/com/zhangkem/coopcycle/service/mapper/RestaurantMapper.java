package com.zhangkem.coopcycle.service.mapper;

import com.zhangkem.coopcycle.domain.Restaurant;
import com.zhangkem.coopcycle.service.dto.RestaurantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Restaurant} and its DTO {@link RestaurantDTO}.
 */
@Mapper(componentModel = "spring")
public interface RestaurantMapper extends EntityMapper<RestaurantDTO, Restaurant> {}
