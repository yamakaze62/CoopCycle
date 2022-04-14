package com.zhangkem.coopcycle.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zhangkem.coopcycle.domain.enumeration.State;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Order.
 */
@Entity
@Table(name = "jhi_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_order", nullable = false)
    private Integer idOrder;

    @NotNull
    @Column(name = "id_restaurant", nullable = false)
    private Integer idRestaurant;

    @NotNull
    @Column(name = "id_customer", nullable = false)
    private Integer idCustomer;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state;

    @DecimalMin(value = "5")
    @DecimalMax(value = "200")
    @Column(name = "totalprice")
    private Float totalprice;

    @Column(name = "date")
    private ZonedDateTime date;

    @OneToMany(mappedBy = "order")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "products", "order" }, allowSetters = true)
    private Set<OrderContent> orderContents = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "orders", "cooperative" }, allowSetters = true)
    private Deliverer deliverer;

    @ManyToOne
    @JsonIgnoreProperties(value = { "orders" }, allowSetters = true)
    private Customer customer;

    @ManyToOne
    @JsonIgnoreProperties(value = { "orders", "products" }, allowSetters = true)
    private Restaurant restaurant;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Order id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdOrder() {
        return this.idOrder;
    }

    public Order idOrder(Integer idOrder) {
        this.setIdOrder(idOrder);
        return this;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    public Integer getIdRestaurant() {
        return this.idRestaurant;
    }

    public Order idRestaurant(Integer idRestaurant) {
        this.setIdRestaurant(idRestaurant);
        return this;
    }

    public void setIdRestaurant(Integer idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public Integer getIdCustomer() {
        return this.idCustomer;
    }

    public Order idCustomer(Integer idCustomer) {
        this.setIdCustomer(idCustomer);
        return this;
    }

    public void setIdCustomer(Integer idCustomer) {
        this.idCustomer = idCustomer;
    }

    public State getState() {
        return this.state;
    }

    public Order state(State state) {
        this.setState(state);
        return this;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Float getTotalprice() {
        return this.totalprice;
    }

    public Order totalprice(Float totalprice) {
        this.setTotalprice(totalprice);
        return this;
    }

    public void setTotalprice(Float totalprice) {
        this.totalprice = totalprice;
    }

    public ZonedDateTime getDate() {
        return this.date;
    }

    public Order date(ZonedDateTime date) {
        this.setDate(date);
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Set<OrderContent> getOrderContents() {
        return this.orderContents;
    }

    public void setOrderContents(Set<OrderContent> orderContents) {
        if (this.orderContents != null) {
            this.orderContents.forEach(i -> i.setOrder(null));
        }
        if (orderContents != null) {
            orderContents.forEach(i -> i.setOrder(this));
        }
        this.orderContents = orderContents;
    }

    public Order orderContents(Set<OrderContent> orderContents) {
        this.setOrderContents(orderContents);
        return this;
    }

    public Order addOrderContent(OrderContent orderContent) {
        this.orderContents.add(orderContent);
        orderContent.setOrder(this);
        return this;
    }

    public Order removeOrderContent(OrderContent orderContent) {
        this.orderContents.remove(orderContent);
        orderContent.setOrder(null);
        return this;
    }

    public Deliverer getDeliverer() {
        return this.deliverer;
    }

    public void setDeliverer(Deliverer deliverer) {
        this.deliverer = deliverer;
    }

    public Order deliverer(Deliverer deliverer) {
        this.setDeliverer(deliverer);
        return this;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Order customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    public Restaurant getRestaurant() {
        return this.restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Order restaurant(Restaurant restaurant) {
        this.setRestaurant(restaurant);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return id != null && id.equals(((Order) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Order{" +
            "id=" + getId() +
            ", idOrder=" + getIdOrder() +
            ", idRestaurant=" + getIdRestaurant() +
            ", idCustomer=" + getIdCustomer() +
            ", state='" + getState() + "'" +
            ", totalprice=" + getTotalprice() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
