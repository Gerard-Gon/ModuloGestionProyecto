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

import ModuloProyectosInnovatech.Proyecto.dto.RegistroHorasDTO;
import ModuloProyectosInnovatech.Proyecto.model.RegistroHoras;
import ModuloProyectosInnovatech.Proyecto.model.Tarea;
import ModuloProyectosInnovatech.Proyecto.repository.RegistroHorasRepository;
import ModuloProyectosInnovatech.Proyecto.repository.TareaRepository;

@ExtendWith(MockitoExtension.class)
public class RegistroHorasServiceTest {

    @Mock
    private RegistroHorasRepository registroHorasRepository;

    @Mock
    private TareaRepository tareaRepository;

    @InjectMocks
    private RegistroHorasService registroHorasService;

    private RegistroHorasDTO registroDTOFalso;
    private RegistroHoras registroFalso;
    private Tarea tareaFalsa;

    @BeforeEach
    void setUp() {
        tareaFalsa = new Tarea();
        tareaFalsa.setId(3);
        tareaFalsa.setNombreTareas("Desarrollar Microservicio");

        registroDTOFalso = new RegistroHorasDTO();
        registroDTOFalso.setTareaId(3);
        registroDTOFalso.setUsuarioId("firebase_uid_456");
        registroDTOFalso.setHorasTrabajadas(5.5f);
        registroDTOFalso.setFechaRegistro(LocalDate.now());
        registroDTOFalso.setEstado(true);

        registroFalso = new RegistroHoras();
        registroFalso.setId(200);
        registroFalso.setTarea(tareaFalsa);
        registroFalso.setUsuarioId("firebase_uid_456");
        registroFalso.setHorasTrabajadas(5.5f);
    }

    @Test
    void testCrearRegistroHoras_Exitoso() {
        when(tareaRepository.findById(3)).thenReturn(Optional.of(tareaFalsa));
        when(registroHorasRepository.save(any(RegistroHoras.class))).thenReturn(registroFalso);

        RegistroHoras resultado = registroHorasService.crear(registroDTOFalso);

        assertNotNull(resultado);
        assertEquals(5.5f, resultado.getHorasTrabajadas());
        assertEquals("firebase_uid_456", resultado.getUsuarioId());
    }

    @Test
    void testCrearRegistroHoras_TareaNoExiste_LanzaExcepcion() {
        when(tareaRepository.findById(3)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            registroHorasService.crear(registroDTOFalso);
        });

        verify(registroHorasRepository, never()).save(any(RegistroHoras.class));
    }
}