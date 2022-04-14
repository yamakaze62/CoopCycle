package com.zhangkem.coopcycle.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.zhangkem.coopcycle.domain.Deliverer} entity.
 */
public class DelivererDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer idDelivrer;

    private Integer idCooperative;

    private String name;

    private String surname;

    @NotNull
    @Size(min = 10, max = 10)
    private String telephone;

    @NotNull
    private Float latitude;

    @NotNull
    private Float longitude;

    private CooperativeDTO cooperative;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdDelivrer() {
        return idDelivrer;
    }

    public void setIdDelivrer(Integer idDelivrer) {
        this.idDelivrer = idDelivrer;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public CooperativeDTO getCooperative() {
        return cooperative;
    }

    public void setCooperative(CooperativeDTO cooperative) {
        this.cooperative = cooperative;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DelivererDTO)) {
            return false;
        }

        DelivererDTO delivererDTO = (DelivererDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, delivererDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DelivererDTO{" +
            "id=" + getId() +
            ", idDelivrer=" + getIdDelivrer() +
            ", idCooperative=" + getIdCooperative() +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", cooperative=" + getCooperative() +
            "}";
    }
}
