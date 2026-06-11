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

import ModuloProyectosInnovatech.Proyecto.model.Tarea;
import ModuloProyectosInnovatech.Proyecto.model.Proyecto;
import ModuloProyectosInnovatech.Proyecto.repository.TareaRepository;
import ModuloProyectosInnovatech.Proyecto.repository.ProyectoRepository;
import ModuloProyectosInnovatech.Proyecto.dto.TareaDTO;

@ExtendWith(MockitoExtension.class)
public class TareaServiceTest {

    @Mock
    private TareaRepository tareaRepository;

    @Mock
    private ProyectoRepository proyectoRepository;

    @InjectMocks
    private TareaService tareaService;

    private TareaDTO tareaDTOFalsa;
    private Proyecto proyectoFalso;
    private Tarea tareaFalsa;

    @BeforeEach
    void setUp() {
        proyectoFalso = new Proyecto();
        proyectoFalso.setId(1);
        proyectoFalso.setNombreProyecto("Proyecto Beta");

        tareaDTOFalsa = new TareaDTO();
        tareaDTOFalsa.setNombreTareas("Configurar Base de Datos");
        tareaDTOFalsa.setDescripcionTareas("Configurar PostgreSQL en Render");
        tareaDTOFalsa.setEstado(true);
        tareaDTOFalsa.setProgreso("Pendiente");
        tareaDTOFalsa.setProyectoId(1);

        tareaFalsa = new Tarea();
        tareaFalsa.setId(10);
        tareaFalsa.setNombreTareas("Configurar Base de Datos");
        tareaFalsa.setProyecto(proyectoFalso);
    }

    @Test
    void testCrearTarea_ConProyectoExistente_Exitoso() {
        // Simulamos que el proyecto SÍ existe
        when(proyectoRepository.findById(1)).thenReturn(Optional.of(proyectoFalso));
        when(tareaRepository.save(any(Tarea.class))).thenReturn(tareaFalsa);

        Tarea resultado = tareaService.crear(tareaDTOFalsa);

        assertNotNull(resultado);
        assertEquals("Configurar Base de Datos", resultado.getNombreTareas());
        assertEquals(1, resultado.getProyecto().getId());
    }

    @Test
    void testCrearTarea_ProyectoNoExiste_LanzaExcepcion() {
        // Simulamos que el proyecto NO existe
        when(proyectoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            tareaService.crear(tareaDTOFalsa);
        });

        verify(tareaRepository, never()).save(any(Tarea.class));
    }
}