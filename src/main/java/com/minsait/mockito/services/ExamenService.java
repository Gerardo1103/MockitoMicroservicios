package com.minsait.mockito.services;

import com.minsait.mockito.models.Examen;

import java.util.Optional;

public interface ExamenService {
    Optional<Examen> findExamenPorNombre(String nombre);
    Examen findExamenPorNombreConPreguntas(String nombre);
    Examen saveExamen(Examen examen);
}
