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

import ModuloProyectosInnovatech.Proyecto.dto.ProyectoDTO;
import ModuloProyectosInnovatech.Proyecto.model.Proyecto;
import ModuloProyectosInnovatech.Proyecto.service.ProyectoService;

@RestController
@RequestMapping("/api/v1/proyectos")
public class ProyectoController {
    @Autowired
    private ProyectoService proyectoService;

    @GetMapping
    public ResponseEntity<List<Proyecto>> listar() {
    return ResponseEntity.ok(proyectoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> getProyectoById(@PathVariable Integer id) {
        Proyecto proyecto = proyectoService.getProyectoById(id);
        if (proyecto == null) {
            return ResponseEntity.notFound().build(); 
        }
        return ResponseEntity.ok(proyecto); 
    }
    
    @PostMapping
    public ResponseEntity<Proyecto> crear(@RequestBody ProyectoDTO dto) {
    return ResponseEntity.status(HttpStatus.CREATED)
    .body(proyectoService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> modificar(@PathVariable Integer id,
    @RequestBody ProyectoDTO dto) {
    return ResponseEntity.ok(proyectoService.modificar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
    proyectoService.eliminar(id);
    return ResponseEntity.noContent().build();
    }   
    
}
