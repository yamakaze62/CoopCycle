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
 * A Deliverer.
 */
@Entity
@Table(name = "deliverer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Deliverer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_delivrer", nullable = false)
    private Integer idDelivrer;

    @Column(name = "id_cooperative")
    private Integer idCooperative;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @NotNull
    @Size(min = 10, max = 10)
    @Column(name = "telephone", length = 10, nullable = false)
    private String telephone;

    @NotNull
    @Column(name = "latitude", nullable = false)
    private Float latitude;

    @NotNull
    @Column(name = "longitude", nullable = false)
    private Float longitude;

    @OneToMany(mappedBy = "deliverer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "orderContents", "deliverer", "customer", "restaurant" }, allowSetters = true)
    private Set<Order> orders = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "deliverers" }, allowSetters = true)
    private Cooperative cooperative;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Deliverer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdDelivrer() {
        return this.idDelivrer;
    }

    public Deliverer idDelivrer(Integer idDelivrer) {
        this.setIdDelivrer(idDelivrer);
        return this;
    }

    public void setIdDelivrer(Integer idDelivrer) {
        this.idDelivrer = idDelivrer;
    }

    public Integer getIdCooperative() {
        return this.idCooperative;
    }

    public Deliverer idCooperative(Integer idCooperative) {
        this.setIdCooperative(idCooperative);
        return this;
    }

    public void setIdCooperative(Integer idCooperative) {
        this.idCooperative = idCooperative;
    }

    public String getName() {
        return this.name;
    }

    public Deliverer name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public Deliverer surname(String surname) {
        this.setSurname(surname);
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Deliverer telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Float getLatitude() {
        return this.latitude;
    }

    public Deliverer latitude(Float latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return this.longitude;
    }

    public Deliverer longitude(Float longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Set<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(Set<Order> orders) {
        if (this.orders != null) {
            this.orders.forEach(i -> i.setDeliverer(null));
        }
        if (orders != null) {
            orders.forEach(i -> i.setDeliverer(this));
        }
        this.orders = orders;
    }

    public Deliverer orders(Set<Order> orders) {
        this.setOrders(orders);
        return this;
    }

    public Deliverer addOrder(Order order) {
        this.orders.add(order);
        order.setDeliverer(this);
        return this;
    }

    public Deliverer removeOrder(Order order) {
        this.orders.remove(order);
        order.setDeliverer(null);
        return this;
    }

    public Cooperative getCooperative() {
        return this.cooperative;
    }

    public void setCooperative(Cooperative cooperative) {
        this.cooperative = cooperative;
    }

    public Deliverer cooperative(Cooperative cooperative) {
        this.setCooperative(cooperative);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Deliverer)) {
            return false;
        }
        return id != null && id.equals(((Deliverer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Deliverer{" +
            "id=" + getId() +
            ", idDelivrer=" + getIdDelivrer() +
            ", idCooperative=" + getIdCooperative() +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            "}";
    }
}
