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

import ModuloProyectosInnovatech.Proyecto.dto.InformeDTO;
import ModuloProyectosInnovatech.Proyecto.model.Informe;
import ModuloProyectosInnovatech.Proyecto.service.InformeService;

@RestController
@RequestMapping("/api/v1/informes")
public class InformeController {

    @Autowired
    private InformeService informeService;

    @GetMapping
    public ResponseEntity<List<Informe>> listar() {
        return ResponseEntity.ok(informeService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Informe> getById(@PathVariable Integer id) {
        try {
            Informe informe = informeService.getById(id);
            return ResponseEntity.ok(informe);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody InformeDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(informeService.crear(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Informe> modificar(@PathVariable Integer id, @RequestBody InformeDTO dto) {
        try {
            return ResponseEntity.ok(informeService.modificar(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            informeService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
}
