package com.datagaurd.superhero.management.service.repository;


import com.datagaurd.superhero.management.service.entity.PowerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuperPowerRepository extends JpaRepository<PowerEntity, Long> {
}
