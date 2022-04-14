package com.zhangkem.coopcycle.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.zhangkem.coopcycle.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrderContentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderContentDTO.class);
        OrderContentDTO orderContentDTO1 = new OrderContentDTO();
        orderContentDTO1.setId(1L);
        OrderContentDTO orderContentDTO2 = new OrderContentDTO();
        assertThat(orderContentDTO1).isNotEqualTo(orderContentDTO2);
        orderContentDTO2.setId(orderContentDTO1.getId());
        assertThat(orderContentDTO1).isEqualTo(orderContentDTO2);
        orderContentDTO2.setId(2L);
        assertThat(orderContentDTO1).isNotEqualTo(orderContentDTO2);
        orderContentDTO1.setId(null);
        assertThat(orderContentDTO1).isNotEqualTo(orderContentDTO2);
    }
}
