package com.datagaurd.superhero.management.service.api.v1;


import com.datagaurd.superhero.management.service.api.v1.model.Superhero;
import com.datagaurd.superhero.management.service.service.SuperheroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/superheroes")
@Validated
public class SuperheroManagementController{
    @Autowired
    private SuperheroService superheroService;
    @PostMapping
    public ResponseEntity<URI> createSuperhero(@Valid @RequestBody Superhero superhero) {
        Long id =  superheroService.createSuperhero(superhero);
        return ResponseEntity.created(URI.create("/api/v1/superheroes/" + id))
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }
    @GetMapping
    public List<Superhero> getSuperheroes() {
        return superheroService.getSuperheroes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Superhero> getSuperheroById(@PathVariable Long id) {
        Superhero superhero = superheroService.getSuperheroById(id);
        return ResponseEntity.ok(superhero);
    }
}
