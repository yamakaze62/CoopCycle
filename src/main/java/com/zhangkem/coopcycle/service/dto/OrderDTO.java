package com.zhangkem.coopcycle.service.dto;

import com.zhangkem.coopcycle.domain.enumeration.State;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.zhangkem.coopcycle.domain.Order} entity.
 */
public class OrderDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer idOrder;

    @NotNull
    private Integer idRestaurant;

    @NotNull
    private Integer idCustomer;

    private State state;

    @DecimalMin(value = "5")
    @DecimalMax(value = "200")
    private Float totalprice;

    private ZonedDateTime date;

    private DelivererDTO deliverer;

    private CustomerDTO customer;

    private RestaurantDTO restaurant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    public Integer getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(Integer idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public Integer getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Integer idCustomer) {
        this.idCustomer = idCustomer;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Float getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Float totalprice) {
        this.totalprice = totalprice;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public DelivererDTO getDeliverer() {
        return deliverer;
    }

    public void setDeliverer(DelivererDTO deliverer) {
        this.deliverer = deliverer;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public RestaurantDTO getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantDTO restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderDTO)) {
            return false;
        }

        OrderDTO orderDTO = (OrderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, orderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderDTO{" +
            "id=" + getId() +
            ", idOrder=" + getIdOrder() +
            ", idRestaurant=" + getIdRestaurant() +
            ", idCustomer=" + getIdCustomer() +
            ", state='" + getState() + "'" +
            ", totalprice=" + getTotalprice() +
            ", date='" + getDate() + "'" +
            ", deliverer=" + getDeliverer() +
            ", customer=" + getCustomer() +
            ", restaurant=" + getRestaurant() +
            "}";
    }
}
