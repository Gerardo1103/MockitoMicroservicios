package com.minsait.mockito.repositories;

import java.util.List;

public interface PreguntaRepository {
    List<String> findPreguntasByExamenId(Long id);
    void SavePreguntas(List<String> preguntas);

}
