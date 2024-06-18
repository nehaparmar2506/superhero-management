package com.datagaurd.superhero.management.service.entity.mapper;


import com.datagaurd.superhero.management.service.api.v1.model.Superhero;
import com.datagaurd.superhero.management.service.entity.AssociationEntity;
import com.datagaurd.superhero.management.service.entity.PowerEntity;
import com.datagaurd.superhero.management.service.entity.SuperheroEntity;
import com.datagaurd.superhero.management.service.entity.WeaponEntity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SuperheroMapper {

    public static SuperheroEntity toDBEntity(Superhero superhero) {
        SuperheroEntity superheroEntity = SuperheroEntity.builder()
                .alias(superhero.getAlias())
                .name(superhero.getName())
                .origin(superhero.getOrigin())
                .build();

        superheroEntity.setPowers(toPowersEntities(superhero.getPowers(), superheroEntity));
        superheroEntity.setWeapons(toWeaponEntities(superhero.getWeapons(), superheroEntity));
        superheroEntity.setAssociations(toAssociationsEntities(superhero.getAssociations(), superheroEntity));

        return superheroEntity;
    }

    private static List<PowerEntity> toPowersEntities(List<String> SuperPowers, SuperheroEntity superhero) {
        if (SuperPowers == null) {
            return null;
        }
        return SuperPowers.stream()
                .map(name -> PowerEntity.builder().name(name).superheroEntity(superhero).build())
                .collect(Collectors.toList());
    }

    private static List<WeaponEntity> toWeaponEntities(List<String> weapons, SuperheroEntity superhero) {
        if (weapons == null) {
            return null;
        }
        return weapons.stream()
                .map(name -> WeaponEntity.builder().name(name)
                        .superheroEntity(superhero).build())
                .collect(Collectors.toList());
    }

    private static List<AssociationEntity> toAssociationsEntities(List<String> associations, SuperheroEntity superhero) {
        if (associations == null) {
            return null;
        }
        return associations.stream()
                .map(name ->
                        AssociationEntity.builder()
                                .name(name).superheroEntity(superhero).build()
                )
                .collect(Collectors.toList());
    }

    public static Superhero toDTO(SuperheroEntity superhero) {
        return Superhero.builder()
                .id(superhero.getId())
                .alias(superhero.getAlias())
                .name(superhero.getName())
                .origin(superhero.getOrigin())
                .powers(mapPowers(superhero.getPowers()))
                .weapons(mapWeapons(superhero.getWeapons()))
                .associations(mapAssociations(superhero.getAssociations()))
                .build();
    }

    private static List<String> mapPowers(List<PowerEntity> powers) {
        if (powers == null) {
            return Collections.emptyList();
        }
        return powers.stream()
                .map(PowerEntity::getName)
                .collect(Collectors.toList());
    }

    private static List<String> mapWeapons(List<WeaponEntity> weapons) {
        if (weapons == null) {
            return Collections.emptyList();
        }
        return weapons.stream()
                .map(WeaponEntity::getName)
                .collect(Collectors.toList());
    }

    private static List<String> mapAssociations(List<AssociationEntity> associations) {
        if (associations == null) {
            return Collections.emptyList();
        }
        return associations.stream()
                .map(AssociationEntity ::getName)
                .collect(Collectors.toList());
    }
}
