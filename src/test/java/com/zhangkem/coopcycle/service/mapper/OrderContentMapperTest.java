package com.zhangkem.coopcycle.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderContentMapperTest {

    private OrderContentMapper orderContentMapper;

    @BeforeEach
    public void setUp() {
        orderContentMapper = new OrderContentMapperImpl();
    }
}
