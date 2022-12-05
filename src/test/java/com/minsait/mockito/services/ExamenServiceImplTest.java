package com.minsait.mockito.services;

import com.minsait.mockito.models.Examen;
import com.minsait.mockito.repositories.ExamenRepository;
import com.minsait.mockito.repositories.PreguntaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExamenServiceImplTest {
    @Mock
    ExamenRepository examenRepository;
    @Mock
    PreguntaRepository preguntaRepository;

    @InjectMocks
    ExamenServiceImpl service;
    @Captor
    ArgumentCaptor <Long> captor;

    @Test
    void testArgumentCaptor(){
        when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasByExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        service.findExamenPorNombreConPreguntas("Español");
        verify(preguntaRepository).findPreguntasByExamenId(captor.capture());
        assertEquals(2L,captor.getValue());
    }

    @Test
    void testFindExamenPorNombre(){
        //datos necesarios simulados
        when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);

        //llamada al metodo
        Optional<Examen> examen = service.findExamenPorNombre("Matematicas");

        //prueba unitaria o afirmacion de que el examen sea obtenido correctamente
        assertEquals("Matematicas",examen.get().getNombre());
        assertTrue(examen.isPresent());
    }
    @Test
    void testPreguntasExamen(){
        when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasByExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);

        Examen examen= service.findExamenPorNombreConPreguntas("Historia");
        assertNotNull(examen);
        assertTrue(examen.getPreguntas().contains("Aritmetica"));
        //mockito
        //por defecto tiene times 1 aunque sea verify(examenRepository).findAll();
        verify(examenRepository,times(1)).findAll();//times, atMost, atMostOnce,atLeastOnce,atLeast
        verify(preguntaRepository).findPreguntasByExamenId(anyLong());
    }
    @Test
    void testExamenNombrePreguntasNuLl(){
        when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);
        Examen examen = service.findExamenPorNombreConPreguntas("Algebra");
        assertNull(examen);
    }
    @Test
    void testSaveExamenReturn(){
     service.saveExamen(Datos.EXAMEN);
     verify(examenRepository,times(1)).save(Datos.EXAMEN);

    }
    @Test
    void testExceptions(){
        when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasByExamenId(anyLong())).thenThrow(IllegalArgumentException.class);

        Exception exception =assertThrows(IllegalArgumentException.class,()->{
            service.findExamenPorNombreConPreguntas("Matematicas");
        });
        assertTrue(IllegalArgumentException.class.equals(exception.getClass()));
    }
    @Test
    void testDoThrow(){
        //mockito
        doThrow(IllegalArgumentException.class).when(preguntaRepository).SavePreguntas(anyList());
        Examen examen =Datos.EXAMEN;
        examen.setPreguntas(Datos.PREGUNTAS);
        assertThrows(IllegalArgumentException.class,()->service.saveExamen(examen));
    }
    @Test
    void testDoAnswer(){
        when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);
        //when(preguntaRepository.findPreguntasByExamenId(1L)).thenReturn(Datos.PREGUNTAS);
        //when(preguntaRepository.findPreguntasByExamenId(2L)).thenReturn(Collections.EMPTY_LIST);
        doAnswer(invocationOnMock -> {
            Long id = invocationOnMock.getArgument(0);
            return id==1?Datos.EXAMENES:Collections.EMPTY_LIST;
        }).when(preguntaRepository).findPreguntasByExamenId(anyLong());
        Examen examen = service.findExamenPorNombreConPreguntas("Matematicas");

        assertAll(
                () -> assertNotNull(examen),
                () -> assertFalse(examen.getPreguntas().isEmpty()),
                () -> assertEquals(3,examen.getPreguntas().size())
        );
    }
}