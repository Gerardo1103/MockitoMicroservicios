package com.minsait.mockito.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data

public class Examen {
    private Long id;
    private String nombre;
    private List<String> preguntas;
    public Examen(Long id,String nombre){
        this.id = id;
        this.nombre = nombre;
        preguntas = new ArrayList<>();
    }
}
