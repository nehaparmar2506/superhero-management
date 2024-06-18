package com.datagaurd.superhero.management.service.service;

import com.datagaurd.superhero.management.service.api.exception.ResourceNotFoundException;
import com.datagaurd.superhero.management.service.api.v1.model.Superhero;
import com.datagaurd.superhero.management.service.entity.SuperheroEntity;
import com.datagaurd.superhero.management.service.entity.mapper.SuperheroMapper;
import com.datagaurd.superhero.management.service.repository.SuperheroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SuperheroServiceImpl implements SuperheroService {

    @Autowired
    private SuperheroRepository superheroRepository;

    @Override
    public Long createSuperhero(Superhero superhero) {
        SuperheroEntity superheroEntity = SuperheroMapper.toDBEntity(superhero);
        return superheroRepository.save(superheroEntity).getId();
    }

    @Override
    public List<Superhero> getSuperheroes() {
        return superheroRepository.findAll().stream()
                .map(SuperheroMapper::toDTO)
                .collect(Collectors.toList());    }

    @Override
    public Superhero getSuperheroById(Long id) {
        SuperheroEntity superheroEntity = superheroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Superhero not found with id: " + id));
        return SuperheroMapper.toDTO(superheroEntity);
    }
}
