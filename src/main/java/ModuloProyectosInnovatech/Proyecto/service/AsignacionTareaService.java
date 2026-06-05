package ModuloProyectosInnovatech.Proyecto.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ModuloProyectosInnovatech.Proyecto.dto.AsignacionTareaDTO;
import ModuloProyectosInnovatech.Proyecto.model.AsignacionTarea;
import ModuloProyectosInnovatech.Proyecto.model.Tarea;
import ModuloProyectosInnovatech.Proyecto.repository.AsignacionTareaRepository;
import ModuloProyectosInnovatech.Proyecto.repository.TareaRepository;

@Service
public class AsignacionTareaService {

    @Autowired
    private AsignacionTareaRepository asignacionTareaRepository;

    @Autowired
    private TareaRepository tareaRepository;

    public List<AsignacionTarea> listarTodos() {
        return asignacionTareaRepository.findAll();
    }

    public AsignacionTarea getById(Integer id) {
        return asignacionTareaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));
    }

    public AsignacionTarea crear(AsignacionTareaDTO dto) {
        Tarea tarea = tareaRepository.findById(dto.getTareaId())
            .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        AsignacionTarea asignacion = new AsignacionTarea();
        asignacion.setTarea(tarea);
        asignacion.setUsuarioId(dto.getUsuarioId());
        asignacion.setFechaAsignacion(dto.getFechaAsignacion());
        asignacion.setEstado(dto.getEstado() != null ? dto.getEstado() : true);
        
        return asignacionTareaRepository.save(asignacion);
    }

    public AsignacionTarea modificar(Integer id, AsignacionTareaDTO dto) {
        return asignacionTareaRepository.findById(id).map(a -> {
            if (dto.getTareaId() != null) {
                Tarea tarea = tareaRepository.findById(dto.getTareaId())
                    .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
                a.setTarea(tarea);
            }
            a.setUsuarioId(dto.getUsuarioId());
            a.setFechaAsignacion(dto.getFechaAsignacion());
            if (dto.getEstado() != null) {
                a.setEstado(dto.getEstado());
            }
            
            return asignacionTareaRepository.save(a);
        }).orElseThrow(() -> new RuntimeException("Asignación no encontrada"));
    }

    // Borrado lógico: cambia estado a false en lugar de eliminar físicamente
    public void eliminar(Integer id) {
        asignacionTareaRepository.findById(id).ifPresent(a -> {
            a.setEstado(false);
            asignacionTareaRepository.save(a);
        });
    }
    
}
