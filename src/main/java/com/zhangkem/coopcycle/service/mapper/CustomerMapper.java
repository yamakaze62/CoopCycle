package com.zhangkem.coopcycle.service.mapper;

import com.zhangkem.coopcycle.domain.Customer;
import com.zhangkem.coopcycle.service.dto.CustomerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Customer} and its DTO {@link CustomerDTO}.
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {}
