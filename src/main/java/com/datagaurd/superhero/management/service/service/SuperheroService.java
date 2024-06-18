package com.datagaurd.superhero.management.service.service;


import com.datagaurd.superhero.management.service.api.v1.model.Superhero;

import java.util.List;

public interface SuperheroService {
    Long createSuperhero(Superhero superhero);

    List<Superhero> getSuperheroes();

    Superhero getSuperheroById(Long id);
}
