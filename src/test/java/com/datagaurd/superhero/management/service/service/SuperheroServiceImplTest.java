package com.datagaurd.superhero.management.service.service;


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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class SuperheroServiceImplTest {

    @Mock
    private SuperheroRepository superheroRepository;

    @InjectMocks
    private SuperheroServiceImpl superheroService;
    private AutoCloseable closeable;

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

    private static Superhero mockSuperhero() {
        return Superhero.builder()
                .alias("Test Superhero")
                .name("Test Name")
                .powers(Arrays.asList("Power1", "Power2"))
                .build();
    }

}
