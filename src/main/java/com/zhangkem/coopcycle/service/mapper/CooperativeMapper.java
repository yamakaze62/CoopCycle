package com.zhangkem.coopcycle.service.mapper;

import com.zhangkem.coopcycle.domain.Cooperative;
import com.zhangkem.coopcycle.service.dto.CooperativeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cooperative} and its DTO {@link CooperativeDTO}.
 */
@Mapper(componentModel = "spring")
public interface CooperativeMapper extends EntityMapper<CooperativeDTO, Cooperative> {}
