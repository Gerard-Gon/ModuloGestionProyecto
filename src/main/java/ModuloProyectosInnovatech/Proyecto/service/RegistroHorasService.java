package ModuloProyectosInnovatech.Proyecto.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ModuloProyectosInnovatech.Proyecto.dto.RegistroHorasDTO;
import ModuloProyectosInnovatech.Proyecto.model.RegistroHoras;
import ModuloProyectosInnovatech.Proyecto.model.Tarea;
import ModuloProyectosInnovatech.Proyecto.repository.RegistroHorasRepository;
import ModuloProyectosInnovatech.Proyecto.repository.TareaRepository;

@Service
public class RegistroHorasService {

    @Autowired
    private RegistroHorasRepository registroHorasRepository;

    @Autowired
    private TareaRepository tareaRepository;

    public List<RegistroHoras> listarTodos() {
        return registroHorasRepository.findAll();
    }

    public RegistroHoras getById(Integer id) {
        return registroHorasRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Registro de horas no encontrado"));
    }

    public RegistroHoras crear(RegistroHorasDTO dto) {
        Tarea tarea = tareaRepository.findById(dto.getTareaId())
            .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        RegistroHoras registro = new RegistroHoras();
        registro.setTarea(tarea);
        registro.setUsuarioId(dto.getUsuarioId());
        registro.setHorasTrabajadas(dto.getHorasTrabajadas());
        registro.setFechaRegistro(dto.getFechaRegistro());
        registro.setEstado(dto.getEstado() != null ? dto.getEstado() : true);
        
        return registroHorasRepository.save(registro);
    }

    public RegistroHoras modificar(Integer id, RegistroHorasDTO dto) {
        return registroHorasRepository.findById(id).map(r -> {
            if (dto.getTareaId() != null) {
                Tarea tarea = tareaRepository.findById(dto.getTareaId())
                    .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
                r.setTarea(tarea);
            }
            r.setUsuarioId(dto.getUsuarioId());
            r.setHorasTrabajadas(dto.getHorasTrabajadas());
            r.setFechaRegistro(dto.getFechaRegistro());
            if (dto.getEstado() != null) {
                r.setEstado(dto.getEstado());
            }
            
            return registroHorasRepository.save(r);
        }).orElseThrow(() -> new RuntimeException("Registro de horas no encontrado"));
    }

    // Borrado lógico: cambia estado a false en lugar de eliminar físicamente
    public void eliminar(Integer id) {
        registroHorasRepository.findById(id).ifPresent(r -> {
            r.setEstado(false);
            registroHorasRepository.save(r);
        });
    }
    
}
