package com.zhangkem.coopcycle.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Restaurant.
 */
@Entity
@Table(name = "restaurant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Restaurant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_restaurant", nullable = false)
    private Integer idRestaurant;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull
    @Size(min = 10, max = 10)
    @Column(name = "telephone", length = 10, nullable = false)
    private String telephone;

    @OneToMany(mappedBy = "restaurant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "orderContents", "deliverer", "customer", "restaurant" }, allowSetters = true)
    private Set<Order> orders = new HashSet<>();

    @OneToMany(mappedBy = "restaurant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "restaurant", "ordercontents" }, allowSetters = true)
    private Set<Product> products = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Restaurant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdRestaurant() {
        return this.idRestaurant;
    }

    public Restaurant idRestaurant(Integer idRestaurant) {
        this.setIdRestaurant(idRestaurant);
        return this;
    }

    public void setIdRestaurant(Integer idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public String getName() {
        return this.name;
    }

    public Restaurant name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public Restaurant address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Restaurant telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Set<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(Set<Order> orders) {
        if (this.orders != null) {
            this.orders.forEach(i -> i.setRestaurant(null));
        }
        if (orders != null) {
            orders.forEach(i -> i.setRestaurant(this));
        }
        this.orders = orders;
    }

    public Restaurant orders(Set<Order> orders) {
        this.setOrders(orders);
        return this;
    }

    public Restaurant addOrder(Order order) {
        this.orders.add(order);
        order.setRestaurant(this);
        return this;
    }

    public Restaurant removeOrder(Order order) {
        this.orders.remove(order);
        order.setRestaurant(null);
        return this;
    }

    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        if (this.products != null) {
            this.products.forEach(i -> i.setRestaurant(null));
        }
        if (products != null) {
            products.forEach(i -> i.setRestaurant(this));
        }
        this.products = products;
    }

    public Restaurant products(Set<Product> products) {
        this.setProducts(products);
        return this;
    }

    public Restaurant addProduct(Product product) {
        this.products.add(product);
        product.setRestaurant(this);
        return this;
    }

    public Restaurant removeProduct(Product product) {
        this.products.remove(product);
        product.setRestaurant(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Restaurant)) {
            return false;
        }
        return id != null && id.equals(((Restaurant) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Restaurant{" +
            "id=" + getId() +
            ", idRestaurant=" + getIdRestaurant() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", telephone='" + getTelephone() + "'" +
            "}";
    }
}
