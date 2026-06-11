package ModuloProyectosInnovatech.Proyecto.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ModuloProyectosInnovatech.Proyecto.dto.AsignacionTareaDTO;
import ModuloProyectosInnovatech.Proyecto.model.AsignacionTarea;
import ModuloProyectosInnovatech.Proyecto.model.Tarea;
import ModuloProyectosInnovatech.Proyecto.repository.AsignacionTareaRepository;
import ModuloProyectosInnovatech.Proyecto.repository.TareaRepository;

@ExtendWith(MockitoExtension.class)
public class AsignacionTareaServiceTest {

    @Mock
    private AsignacionTareaRepository asignacionTareaRepository;

    @Mock
    private TareaRepository tareaRepository;

    @InjectMocks
    private AsignacionTareaService asignacionTareaService;

    private AsignacionTareaDTO asignacionDTOFalsa;
    private AsignacionTarea asignacionFalsa;
    private Tarea tareaFalsa;

    @BeforeEach
    void setUp() {
        tareaFalsa = new Tarea();
        tareaFalsa.setId(1);
        tareaFalsa.setNombreTareas("Optimizar consultas SQL");

        asignacionDTOFalsa = new AsignacionTareaDTO();
        asignacionDTOFalsa.setTareaId(1);
        asignacionDTOFalsa.setUsuarioId("firebase_uid_789");
        asignacionDTOFalsa.setFechaAsignacion(LocalDate.now());
        asignacionDTOFalsa.setEstado(true);

        asignacionFalsa = new AsignacionTarea();
        asignacionFalsa.setId(100);
        asignacionFalsa.setTarea(tareaFalsa);
        asignacionFalsa.setUsuarioId("firebase_uid_789");
    }

    @Test
    void testCrearAsignacion_Exitoso() {
        when(tareaRepository.findById(1)).thenReturn(Optional.of(tareaFalsa));
        when(asignacionTareaRepository.save(any(AsignacionTarea.class))).thenReturn(asignacionFalsa);

        AsignacionTarea resultado = asignacionTareaService.crear(asignacionDTOFalsa);

        assertNotNull(resultado);
        assertEquals(100, resultado.getId());
        assertEquals("firebase_uid_789", resultado.getUsuarioId());
        verify(tareaRepository, times(1)).findById(1);
        verify(asignacionTareaRepository, times(1)).save(any(AsignacionTarea.class));
    }

    @Test
    void testCrearAsignacion_TareaNoExiste_LanzaExcepcion() {
        when(tareaRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            asignacionTareaService.crear(asignacionDTOFalsa);
        });

        verify(asignacionTareaRepository, never()).save(any(AsignacionTarea.class));
    }
}