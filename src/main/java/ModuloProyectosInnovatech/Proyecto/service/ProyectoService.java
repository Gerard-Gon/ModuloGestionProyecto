package ModuloProyectosInnovatech.Proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ModuloProyectosInnovatech.Proyecto.dto.ProyectoDTO;
import ModuloProyectosInnovatech.Proyecto.model.Proyecto;
import ModuloProyectosInnovatech.Proyecto.repository.AsignacionTareaRepository;
import ModuloProyectosInnovatech.Proyecto.repository.InformeRepository;
import ModuloProyectosInnovatech.Proyecto.repository.ProyectoRepository;
import ModuloProyectosInnovatech.Proyecto.repository.RegistroHorasRepository;
import ModuloProyectosInnovatech.Proyecto.repository.TareaRepository;

@Service
public class ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private InformeRepository informeRepository;

    @Autowired
    private AsignacionTareaRepository asignacionTareaRepository;

    @Autowired
    private RegistroHorasRepository registroHorasRepository;

    // Listar todos los proyectos
    public List<Proyecto> listarTodos() {
        return proyectoRepository.findAll();
    }

    //Llama solo 1 proyecto por Id
    public Proyecto getProyectoById(Integer id) {
        return proyectoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
    }

    // Crear un proyecto a partir de un DTO
    public Proyecto crear(ProyectoDTO dto) {
        Proyecto proyecto = new Proyecto();
        proyecto.setNombreProyecto(dto.getNombreProyecto());
        proyecto.setDescripcionProyecto(dto.getDescripcionProyecto());
        proyecto.setJefeId(dto.getJefeId());
        proyecto.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        return proyectoRepository.save(proyecto);
        
    }

    // Modificar un proyecto existente
    public Proyecto modificar(Integer id, ProyectoDTO dto) {
        return proyectoRepository.findById(id).map(p -> {
            p.setNombreProyecto(dto.getNombreProyecto());
            p.setDescripcionProyecto(dto.getDescripcionProyecto());
            p.setJefeId(dto.getJefeId());
            p.setActivo(dto.getActivo());
            return proyectoRepository.save(p);
        }).orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
    }

    // Borrado lógico en cascada (optimizado, evita el problema N+1):
    // 1. Desactiva en masa los datos hijos (Informe, AsignacionTarea, RegistroHoras)
    // 2. Desactiva todas las tareas del proyecto en masa
    // 3. Desactiva el proyecto
    // Todo dentro de una única transacción atómica (@Transactional)
    @Transactional
    public void eliminar(Integer id) {
        proyectoRepository.findById(id).ifPresent(p -> {
            // Paso 1: Desactivar hijos de las tareas en una sola query cada uno
            informeRepository.desactivarPorProyecto(id);
            asignacionTareaRepository.desactivarPorProyecto(id);
            registroHorasRepository.desactivarPorProyecto(id);

            // Paso 2: Desactivar todas las tareas del proyecto en una sola query
            tareaRepository.desactivarPorProyecto(id);

            // Paso 3: Desactivar el proyecto
            p.setActivo(false);
            proyectoRepository.save(p);
        });
    }
    
}

