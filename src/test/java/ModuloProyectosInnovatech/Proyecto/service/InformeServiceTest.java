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

import ModuloProyectosInnovatech.Proyecto.dto.InformeDTO;
import ModuloProyectosInnovatech.Proyecto.model.Informe;
import ModuloProyectosInnovatech.Proyecto.model.Tarea;
import ModuloProyectosInnovatech.Proyecto.repository.InformeRepository;
import ModuloProyectosInnovatech.Proyecto.repository.TareaRepository;

@ExtendWith(MockitoExtension.class)
public class InformeServiceTest {

    @Mock
    private InformeRepository informeRepository;

    @Mock
    private TareaRepository tareaRepository;

    @InjectMocks
    private InformeService informeService;

    private InformeDTO informeDTOFalso;
    private Informe informeFalso;
    private Tarea tareaFalsa;

    @BeforeEach
    void setUp() {
        tareaFalsa = new Tarea();
        tareaFalsa.setId(2);
        tareaFalsa.setNombreTareas("Implementar Gateway");

        informeDTOFalso = new InformeDTO();
        informeDTOFalso.setTareaId(2);
        informeDTOFalso.setUrlArchivo("https://aws.s3.com/informe.pdf");
        informeDTOFalso.setFechaSubida(LocalDate.now());
        informeDTOFalso.setEstado(true);

        informeFalso = new Informe();
        informeFalso.setId(50);
        informeFalso.setTarea(tareaFalsa);
        informeFalso.setUrlArchivo("https://aws.s3.com/informe.pdf");
    }

    @Test
    void testCrearInforme_Exitoso() {
        when(tareaRepository.findById(2)).thenReturn(Optional.of(tareaFalsa));
        when(informeRepository.save(any(Informe.class))).thenReturn(informeFalso);

        Informe resultado = informeService.crear(informeDTOFalso);

        assertNotNull(resultado);
        assertEquals("https://aws.s3.com/informe.pdf", resultado.getUrlArchivo());
        assertEquals(2, resultado.getTarea().getId());
    }

    @Test
    void testCrearInforme_TareaNoExiste_LanzaExcepcion() {
        when(tareaRepository.findById(2)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            informeService.crear(informeDTOFalso);
        });

        verify(informeRepository, never()).save(any(Informe.class));
    }
}