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

import ModuloProyectosInnovatech.Proyecto.dto.RegistroHorasDTO;
import ModuloProyectosInnovatech.Proyecto.model.RegistroHoras;
import ModuloProyectosInnovatech.Proyecto.service.RegistroHorasService;

@RestController
@RequestMapping("/api/v1/registro-horas")
public class RegistroHorasController {

    @Autowired
    private RegistroHorasService registroHorasService;

    @GetMapping
    public ResponseEntity<List<RegistroHoras>> listar() {
        return ResponseEntity.ok(registroHorasService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroHoras> getById(@PathVariable Integer id) {
        try {
            RegistroHoras registro = registroHorasService.getById(id);
            return ResponseEntity.ok(registro);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody RegistroHorasDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(registroHorasService.crear(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroHoras> modificar(@PathVariable Integer id, @RequestBody RegistroHorasDTO dto) {
        try {
            return ResponseEntity.ok(registroHorasService.modificar(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            registroHorasService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
}
