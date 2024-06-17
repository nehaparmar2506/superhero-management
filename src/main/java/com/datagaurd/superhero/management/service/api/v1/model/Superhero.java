package com.datagaurd.superhero.management.service.api.v1.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Superhero {
    private Long id;
    private String alias;
    private String name;
    private String origin;
    private List<String> powers;
    private List<String> weapons;
    private List<String> associations;
}
