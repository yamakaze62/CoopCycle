package com.zhangkem.coopcycle.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.zhangkem.coopcycle.domain.Cooperative} entity.
 */
public class CooperativeDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer idCooperative;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdCooperative() {
        return idCooperative;
    }

    public void setIdCooperative(Integer idCooperative) {
        this.idCooperative = idCooperative;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CooperativeDTO)) {
            return false;
        }

        CooperativeDTO cooperativeDTO = (CooperativeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cooperativeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CooperativeDTO{" +
            "id=" + getId() +
            ", idCooperative=" + getIdCooperative() +
            ", name='" + getName() + "'" +
            "}";
    }
}
