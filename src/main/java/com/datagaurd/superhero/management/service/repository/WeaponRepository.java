package com.datagaurd.superhero.management.service.repository;

import com.datagaurd.superhero.management.service.entity.WeaponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeaponRepository extends JpaRepository<WeaponEntity, Long> {
}
