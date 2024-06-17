package com.datagaurd.superhero.management.service.api.v1;


import com.datagaurd.superhero.management.service.api.v1.model.Superhero;
import com.datagaurd.superhero.management.service.service.SuperheroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/superheroes")
public class SuperheroManagementController{
    @Autowired
    private SuperheroService superheroService;
    @PostMapping
    public ResponseEntity<URI> createSuperhero(@RequestBody Superhero superhero) {
        Long id =  superheroService.createSuperhero(superhero);
        return ResponseEntity.created(URI.create("/api/v1/superheroes/" + id))
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

}
