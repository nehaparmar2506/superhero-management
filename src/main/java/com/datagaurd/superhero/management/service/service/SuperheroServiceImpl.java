package com.datagaurd.superhero.management.service.service;

import com.datagaurd.superhero.management.service.api.v1.model.Superhero;
import com.datagaurd.superhero.management.service.entity.SuperheroEntity;
import com.datagaurd.superhero.management.service.entity.mapper.SuperheroMapper;
import com.datagaurd.superhero.management.service.repository.SuperheroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SuperheroServiceImpl implements SuperheroService {

    @Autowired
    private SuperheroRepository superheroRepository;

    @Override
    public Long createSuperhero(Superhero superhero) {
        SuperheroEntity superheroEntity = SuperheroMapper.toDBEntity(superhero);
        return superheroRepository.save(superheroEntity).getId();
    }
}
