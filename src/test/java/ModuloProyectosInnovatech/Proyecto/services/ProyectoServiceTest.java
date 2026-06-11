package ModuloProyectosInnovatech.Proyecto.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ModuloProyectosInnovatech.Proyecto.model.Proyecto;
import ModuloProyectosInnovatech.Proyecto.repository.ProyectoRepository;
import ModuloProyectosInnovatech.Proyecto.dto.ProyectoDTO;

@ExtendWith(MockitoExtension.class)
public class ProyectoServiceTest {

    @Mock
    private ProyectoRepository proyectoRepository;

    @InjectMocks
    private ProyectoService proyectoService;

    private Proyecto proyectoFalso;
    private ProyectoDTO proyectoDTOFalso;

    @BeforeEach
    void setUp() {
        proyectoFalso = new Proyecto();
        proyectoFalso.setId(1);
        proyectoFalso.setNombreProyecto("Migración Cloud Innovatech");
        proyectoFalso.setActivo(true);
        proyectoFalso.setJefeId("firebase_uid_123");

        proyectoDTOFalso = new ProyectoDTO();
        proyectoDTOFalso.setNombreProyecto("Migración Cloud Innovatech");
        proyectoDTOFalso.setJefeId("firebase_uid_123");
        proyectoDTOFalso.setActivo(true);
    }

    @Test
    void testObtenerProyectoPorId_Exitoso() {
        when(proyectoRepository.findById(1)).thenReturn(Optional.of(proyectoFalso));

        Proyecto resultado = proyectoService.getProyectoById(1);

        assertNotNull(resultado);
        assertEquals("Migración Cloud Innovatech", resultado.getNombreProyecto());
        verify(proyectoRepository, times(1)).findById(1);
    }

    @Test
    void testObtenerProyectoPorId_NoEncontrado_LanzaExcepcion() {
        when(proyectoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            proyectoService.getProyectoById(99);
        });
    }

    @Test
    void testCrearProyecto_Exitoso() {
        when(proyectoRepository.save(any(Proyecto.class))).thenReturn(proyectoFalso);

        Proyecto resultado = proyectoService.crear(proyectoDTOFalso);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("firebase_uid_123", resultado.getJefeId());
        verify(proyectoRepository, times(1)).save(any(Proyecto.class));
    }
}