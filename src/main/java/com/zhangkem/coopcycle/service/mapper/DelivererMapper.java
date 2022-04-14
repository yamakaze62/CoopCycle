package com.zhangkem.coopcycle.service.mapper;

import com.zhangkem.coopcycle.domain.Cooperative;
import com.zhangkem.coopcycle.domain.Deliverer;
import com.zhangkem.coopcycle.service.dto.CooperativeDTO;
import com.zhangkem.coopcycle.service.dto.DelivererDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Deliverer} and its DTO {@link DelivererDTO}.
 */
@Mapper(componentModel = "spring")
public interface DelivererMapper extends EntityMapper<DelivererDTO, Deliverer> {
    @Mapping(target = "cooperative", source = "cooperative", qualifiedByName = "cooperativeId")
    DelivererDTO toDto(Deliverer s);

    @Named("cooperativeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CooperativeDTO toDtoCooperativeId(Cooperative cooperative);
}
