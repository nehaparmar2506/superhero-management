package com.datagaurd.superhero.management.service.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Builder
public class SuperheroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String alias;
    private String name;
    private String origin;

    @OneToMany(mappedBy = "superheroEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PowerEntity> powers;

    @OneToMany(mappedBy = "superheroEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WeaponEntity> weapons;

    @OneToMany(mappedBy = "superheroEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AssociationEntity> associations;
}
