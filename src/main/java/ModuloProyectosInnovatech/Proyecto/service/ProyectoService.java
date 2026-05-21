package ModuloProyectosInnovatech.Proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ModuloProyectosInnovatech.Proyecto.dto.ProyectoDTO;
import ModuloProyectosInnovatech.Proyecto.model.Proyecto;
import ModuloProyectosInnovatech.Proyecto.repository.ProyectoRepository;

@Service
public class ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

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

    // Borrado lógico: Cambia el estado a false en lugar de eliminar
    public void eliminar(Integer id) {
        proyectoRepository.findById(id).ifPresent(p -> {
        p.setActivo(false); // Cambiamos el estado a inactivo
        proyectoRepository.save(p); // Guardamos el cambio
        });
    }
    
}
