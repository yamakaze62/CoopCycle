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
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_product", nullable = false)
    private Integer idProduct;

    @NotNull
    @Column(name = "id_restaurant", nullable = false)
    private Integer idRestaurant;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Float price;

    @Min(value = 0)
    @Column(name = "disponibility")
    private Integer disponibility;

    @ManyToOne
    @JsonIgnoreProperties(value = { "orders", "products" }, allowSetters = true)
    private Restaurant restaurant;

    @ManyToMany(mappedBy = "products")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "products", "order" }, allowSetters = true)
    private Set<OrderContent> ordercontents = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Product id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdProduct() {
        return this.idProduct;
    }

    public Product idProduct(Integer idProduct) {
        this.setIdProduct(idProduct);
        return this;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public Integer getIdRestaurant() {
        return this.idRestaurant;
    }

    public Product idRestaurant(Integer idRestaurant) {
        this.setIdRestaurant(idRestaurant);
        return this;
    }

    public void setIdRestaurant(Integer idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public String getName() {
        return this.name;
    }

    public Product name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return this.price;
    }

    public Product price(Float price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getDisponibility() {
        return this.disponibility;
    }

    public Product disponibility(Integer disponibility) {
        this.setDisponibility(disponibility);
        return this;
    }

    public void setDisponibility(Integer disponibility) {
        this.disponibility = disponibility;
    }

    public Restaurant getRestaurant() {
        return this.restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Product restaurant(Restaurant restaurant) {
        this.setRestaurant(restaurant);
        return this;
    }

    public Set<OrderContent> getOrdercontents() {
        return this.ordercontents;
    }

    public void setOrdercontents(Set<OrderContent> orderContents) {
        if (this.ordercontents != null) {
            this.ordercontents.forEach(i -> i.removeProduct(this));
        }
        if (orderContents != null) {
            orderContents.forEach(i -> i.addProduct(this));
        }
        this.ordercontents = orderContents;
    }

    public Product ordercontents(Set<OrderContent> orderContents) {
        this.setOrdercontents(orderContents);
        return this;
    }

    public Product addOrdercontent(OrderContent orderContent) {
        this.ordercontents.add(orderContent);
        orderContent.getProducts().add(this);
        return this;
    }

    public Product removeOrdercontent(OrderContent orderContent) {
        this.ordercontents.remove(orderContent);
        orderContent.getProducts().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", idProduct=" + getIdProduct() +
            ", idRestaurant=" + getIdRestaurant() +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            ", disponibility=" + getDisponibility() +
            "}";
    }
}
