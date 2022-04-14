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
 * A OrderContent.
 */
@Entity
@Table(name = "order_content")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrderContent implements Serializable {

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
    @Column(name = "id_order", nullable = false)
    private Integer idOrder;

    @Column(name = "quantity_asked")
    private Integer quantityAsked;

    @Column(name = "product_available")
    private Boolean productAvailable;

    @ManyToMany
    @NotNull
    @JoinTable(
        name = "rel_order_content__product",
        joinColumns = @JoinColumn(name = "order_content_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "restaurant", "ordercontents" }, allowSetters = true)
    private Set<Product> products = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "orderContents", "deliverer", "customer", "restaurant" }, allowSetters = true)
    private Order order;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OrderContent id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdProduct() {
        return this.idProduct;
    }

    public OrderContent idProduct(Integer idProduct) {
        this.setIdProduct(idProduct);
        return this;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public Integer getIdOrder() {
        return this.idOrder;
    }

    public OrderContent idOrder(Integer idOrder) {
        this.setIdOrder(idOrder);
        return this;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    public Integer getQuantityAsked() {
        return this.quantityAsked;
    }

    public OrderContent quantityAsked(Integer quantityAsked) {
        this.setQuantityAsked(quantityAsked);
        return this;
    }

    public void setQuantityAsked(Integer quantityAsked) {
        this.quantityAsked = quantityAsked;
    }

    public Boolean getProductAvailable() {
        return this.productAvailable;
    }

    public OrderContent productAvailable(Boolean productAvailable) {
        this.setProductAvailable(productAvailable);
        return this;
    }

    public void setProductAvailable(Boolean productAvailable) {
        this.productAvailable = productAvailable;
    }

    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public OrderContent products(Set<Product> products) {
        this.setProducts(products);
        return this;
    }

    public OrderContent addProduct(Product product) {
        this.products.add(product);
        product.getOrdercontents().add(this);
        return this;
    }

    public OrderContent removeProduct(Product product) {
        this.products.remove(product);
        product.getOrdercontents().remove(this);
        return this;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderContent order(Order order) {
        this.setOrder(order);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderContent)) {
            return false;
        }
        return id != null && id.equals(((OrderContent) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderContent{" +
            "id=" + getId() +
            ", idProduct=" + getIdProduct() +
            ", idOrder=" + getIdOrder() +
            ", quantityAsked=" + getQuantityAsked() +
            ", productAvailable='" + getProductAvailable() + "'" +
            "}";
    }
}
