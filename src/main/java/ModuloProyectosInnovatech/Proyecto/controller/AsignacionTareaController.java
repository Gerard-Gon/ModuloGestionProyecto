package ModuloProyectosInnovatech.Proyecto.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ModuloProyectosInnovatech.Proyecto.dto.AsignacionTareaDTO;
import ModuloProyectosInnovatech.Proyecto.model.AsignacionTarea;
import ModuloProyectosInnovatech.Proyecto.service.AsignacionTareaService;

@RestController
@RequestMapping("/api/v1/asignaciones-tareas")
public class AsignacionTareaController {

    @Autowired
    private AsignacionTareaService asignacionTareaService;

    @GetMapping
    public ResponseEntity<List<AsignacionTarea>> listar() {
        return ResponseEntity.ok(asignacionTareaService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AsignacionTarea> getById(@PathVariable Integer id) {
        try {
            AsignacionTarea asignacion = asignacionTareaService.getById(id);
            return ResponseEntity.ok(asignacion);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody AsignacionTareaDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(asignacionTareaService.crear(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AsignacionTarea> modificar(@PathVariable Integer id, @RequestBody AsignacionTareaDTO dto) {
        try {
            return ResponseEntity.ok(asignacionTareaService.modificar(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            asignacionTareaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
}
