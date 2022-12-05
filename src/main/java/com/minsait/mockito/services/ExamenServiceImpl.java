package com.minsait.mockito.services;

import com.minsait.mockito.models.Examen;
import com.minsait.mockito.repositories.ExamenRepository;
import com.minsait.mockito.repositories.PreguntaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExamenServiceImpl implements ExamenService{
    ExamenRepository examenRepository;
    PreguntaRepository preguntaRepository;
    @Override
    public Optional<Examen> findExamenPorNombre(String nombre) {
        //examenRepository.findAll(); //para marcar error en la prueba unitaria
        return examenRepository.findAll().stream()
               //.filter(name -> name.equals(nombre))
               // .filter(examen -> examen.getNombre().equals(nombre))
                .findFirst();

    }

    @Override
    public Examen findExamenPorNombreConPreguntas(String nombre) {
        Optional<Examen> examen = findExamenPorNombre(nombre);
        if(examen.isPresent()){
            examen.get().setPreguntas(preguntaRepository
                    .findPreguntasByExamenId(examen.get().getId()));
            return examen.get();
        }
        return null;
    }

    @Override
    public Examen saveExamen(Examen examen) {
        //Validar si tiene preguntas. si tiene preguntas, guardar el examen con ellas
        //si no tiene solo guardar el examen
        if(!examen.getPreguntas().isEmpty()){
                preguntaRepository.SavePreguntas(examen.getPreguntas());
        }
        return examenRepository.save(examen);
    }
}
