package com.datagaurd.superhero.management.service.repository;

import com.datagaurd.superhero.management.service.entity.SuperheroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuperheroRepository extends JpaRepository<SuperheroEntity, Long> {
}
