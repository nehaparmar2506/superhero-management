package com.datagaurd.superhero.management.service.service;


import com.datagaurd.superhero.management.service.api.exception.ResourceNotFoundException;
import com.datagaurd.superhero.management.service.api.v1.model.Superhero;
import com.datagaurd.superhero.management.service.entity.SuperheroEntity;
import com.datagaurd.superhero.management.service.entity.mapper.SuperheroMapper;
import com.datagaurd.superhero.management.service.repository.SuperheroRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class SuperheroServiceImplTest {

    @Mock
    private SuperheroRepository superheroRepository;

    @InjectMocks
    private SuperheroServiceImpl superheroService;
    private AutoCloseable closeable;

    private static Superhero mockSuperhero() {
        return Superhero.builder()
                .alias("Test Superhero")
                .name("Test Name")
                .powers(Arrays.asList("Power1", "Power2"))
                .build();
    }

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void testCreateSuperhero() {

        Superhero superhero = mockSuperhero();
        SuperheroEntity superheroEntity = SuperheroMapper.toDBEntity(superhero);
        superheroEntity.setId(1L);

        when(superheroRepository.save(any(SuperheroEntity.class))).thenReturn(superheroEntity);

        var id = superheroService.createSuperhero(superhero);

        assertNotNull(id);
        assertEquals(id, 1);

    }

    @Test
    void testGetSuperheroes() {
        var superheroEntity1 = SuperheroEntity.builder()
                .id(1L)
                .alias("Superman")
                .name("Clark Kent")
                .build();
        var superheroEntity2 = SuperheroEntity.builder()
                .id(2L)
                .alias("Batman")
                .name("Bruce Wayne")
                .build();

        when(superheroRepository.findAll()).thenReturn(Arrays.asList(superheroEntity1, superheroEntity2));

        List<Superhero> result = superheroService.getSuperheroes();

        assertEquals(2, result.size());
        assertTrue(result.contains(SuperheroMapper.toDTO(superheroEntity1)));
        assertTrue(result.contains(SuperheroMapper.toDTO(superheroEntity2)));
    }

    @Test
    void testGetSuperheroById() {
        var superheroEntity1 = SuperheroEntity.builder()
                .id(1L)
                .alias("Superman")
                .name("Clark Kent")
                .build();
        when(superheroRepository.findById(1L)).thenReturn(Optional.of(superheroEntity1));

        Superhero result = superheroService.getSuperheroById(1L);

        assertNotNull(result);
        assertEquals(SuperheroMapper.toDTO(superheroEntity1), result);
    }

    @Test
    void testGetSuperheroByIdNotFound() {
        when(superheroRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            superheroService.getSuperheroById(1L);
        });

        assertEquals(ResourceNotFoundException.class, exception.getClass());
    }

}
