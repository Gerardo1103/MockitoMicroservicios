package com.minsait.mockito.repositories;

import com.minsait.mockito.models.Examen;

import java.util.List;

public interface ExamenRepository {
    List<Examen> findAll();
    Examen save(Examen examen);


}
