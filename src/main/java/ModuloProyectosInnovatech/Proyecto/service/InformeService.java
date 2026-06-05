package ModuloProyectosInnovatech.Proyecto.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ModuloProyectosInnovatech.Proyecto.dto.InformeDTO;
import ModuloProyectosInnovatech.Proyecto.model.Informe;
import ModuloProyectosInnovatech.Proyecto.model.Tarea;
import ModuloProyectosInnovatech.Proyecto.repository.InformeRepository;
import ModuloProyectosInnovatech.Proyecto.repository.TareaRepository;

@Service
public class InformeService {

    @Autowired
    private InformeRepository informeRepository;

    @Autowired
    private TareaRepository tareaRepository;

    public List<Informe> listarTodos() {
        return informeRepository.findAll();
    }

    public Informe getById(Integer id) {
        return informeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Informe no encontrado"));
    }

    public Informe crear(InformeDTO dto) {
        Tarea tarea = tareaRepository.findById(dto.getTareaId())
            .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        Informe informe = new Informe();
        informe.setTarea(tarea);
        informe.setUrlArchivo(dto.getUrlArchivo());
        informe.setFechaSubida(dto.getFechaSubida());
        informe.setEstado(dto.getEstado() != null ? dto.getEstado() : true);
        
        return informeRepository.save(informe);
    }

    public Informe modificar(Integer id, InformeDTO dto) {
        return informeRepository.findById(id).map(i -> {
            if (dto.getTareaId() != null) {
                Tarea tarea = tareaRepository.findById(dto.getTareaId())
                    .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
                i.setTarea(tarea);
            }
            i.setUrlArchivo(dto.getUrlArchivo());
            i.setFechaSubida(dto.getFechaSubida());
            if (dto.getEstado() != null) {
                i.setEstado(dto.getEstado());
            }
            
            return informeRepository.save(i);
        }).orElseThrow(() -> new RuntimeException("Informe no encontrado"));
    }

    // Borrado lógico: cambia estado a false en lugar de eliminar físicamente
    public void eliminar(Integer id) {
        informeRepository.findById(id).ifPresent(i -> {
            i.setEstado(false);
            informeRepository.save(i);
        });
    }
    
}
