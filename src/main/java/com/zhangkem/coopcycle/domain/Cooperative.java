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
 * A Cooperative.
 */
@Entity
@Table(name = "cooperative")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cooperative implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_cooperative", nullable = false)
    private Integer idCooperative;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "cooperative")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "orders", "cooperative" }, allowSetters = true)
    private Set<Deliverer> deliverers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cooperative id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdCooperative() {
        return this.idCooperative;
    }

    public Cooperative idCooperative(Integer idCooperative) {
        this.setIdCooperative(idCooperative);
        return this;
    }

    public void setIdCooperative(Integer idCooperative) {
        this.idCooperative = idCooperative;
    }

    public String getName() {
        return this.name;
    }

    public Cooperative name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Deliverer> getDeliverers() {
        return this.deliverers;
    }

    public void setDeliverers(Set<Deliverer> deliverers) {
        if (this.deliverers != null) {
            this.deliverers.forEach(i -> i.setCooperative(null));
        }
        if (deliverers != null) {
            deliverers.forEach(i -> i.setCooperative(this));
        }
        this.deliverers = deliverers;
    }

    public Cooperative deliverers(Set<Deliverer> deliverers) {
        this.setDeliverers(deliverers);
        return this;
    }

    public Cooperative addDeliverer(Deliverer deliverer) {
        this.deliverers.add(deliverer);
        deliverer.setCooperative(this);
        return this;
    }

    public Cooperative removeDeliverer(Deliverer deliverer) {
        this.deliverers.remove(deliverer);
        deliverer.setCooperative(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cooperative)) {
            return false;
        }
        return id != null && id.equals(((Cooperative) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cooperative{" +
            "id=" + getId() +
            ", idCooperative=" + getIdCooperative() +
            ", name='" + getName() + "'" +
            "}";
    }
}
