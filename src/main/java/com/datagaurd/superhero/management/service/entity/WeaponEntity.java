package com.datagaurd.superhero.management.service.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;


@Entity
@Data
@Builder
public class WeaponEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "superheroEntity_id")
    private SuperheroEntity superheroEntity;
}
