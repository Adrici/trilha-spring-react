package com.devsuperior.dscatalog.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_category")
public class Category implements Serializable {
    private static final long serialVersionUIDO = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE") //PADRAO UTC
    private Instant createdAt;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant updateddAt;

   @ManyToMany(mappedBy = "categories")
    private Set<Product> products = new HashSet<>();


    //necessário para o hibernate identificar a classe
    @Deprecated
    public Category() {

    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Instant getCreatedAt() {
        return createdAt;
    }


    public Instant getUpdateddAt() {
        return updateddAt;
    }


    @PrePersist
    public void prePersist(){
        createdAt = Instant.now();

    }

    @PreUpdate
    public void preUpdate(){
        updateddAt = Instant.now();

    }

    public Set<Product> getProducts() {
        return products;
    }

    @Override
    //para o java entender o que vai diferenciar um objeto de outro. Ex.: nome e sobrenome
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return Objects.equals(getId(), category.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
