package ModuloProyectosInnovatech.Proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ModuloProyectosInnovatech.Proyecto.dto.TareaDTO;
import ModuloProyectosInnovatech.Proyecto.model.Proyecto;
import ModuloProyectosInnovatech.Proyecto.model.Tarea;
import ModuloProyectosInnovatech.Proyecto.repository.ProyectoRepository;
import ModuloProyectosInnovatech.Proyecto.repository.TareaRepository;

@Service
public class TareaService {

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private ProyectoRepository proyectoRepository;

    public List<Tarea> listarTodos() {
        return tareaRepository.findAll();
    }

    public Tarea getTareaById(Integer id) {
        return tareaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
    }

    // Crear
    public Tarea crear(TareaDTO dto) {
        Proyecto proyecto = proyectoRepository.findById(dto.getProyectoId())
            .orElseThrow(() -> new RuntimeException("Proyecto no encontrado para asignar la tarea"));

        Tarea tarea = new Tarea();
        tarea.setNombreTareas(dto.getNombreTareas());
        tarea.setDescripcionTareas(dto.getDescripcionTareas());
        tarea.setEstado(dto.getEstado() != null ? dto.getEstado() : true);
        tarea.setProgreso(dto.getProgreso() != null ? dto.getProgreso() : "Pendiente"); 
        tarea.setProyecto(proyecto); 
        
        return tareaRepository.save(tarea);
    }

    // Modificar
    public Tarea modificar(Integer id, TareaDTO dto) {
        return tareaRepository.findById(id).map(t -> {
            t.setNombreTareas(dto.getNombreTareas());
            t.setDescripcionTareas(dto.getDescripcionTareas());
            
            if (dto.getEstado() != null) {
                t.setEstado(dto.getEstado()); 
            }
            if (dto.getProgreso() != null) {
                t.setProgreso(dto.getProgreso());
            }
            
            if (dto.getProyectoId() != null) {
                Proyecto proyecto = proyectoRepository.findById(dto.getProyectoId())
                    .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
                t.setProyecto(proyecto);
            }
            
            return tareaRepository.save(t);
        }).orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
    }

    public void eliminar(Integer id) {
        tareaRepository.findById(id).ifPresent(t -> {
            t.setEstado(false); 
            tareaRepository.save(t); 
        });
    }
    
}
