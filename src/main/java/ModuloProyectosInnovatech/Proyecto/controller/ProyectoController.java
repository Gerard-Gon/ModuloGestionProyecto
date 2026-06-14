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

import ModuloProyectosInnovatech.Proyecto.dto.ProyectoConJefeDTO;
import ModuloProyectosInnovatech.Proyecto.dto.ProyectoDTO;
import ModuloProyectosInnovatech.Proyecto.dto.UsuarioExternoDTO;
import ModuloProyectosInnovatech.Proyecto.model.Proyecto;
import ModuloProyectosInnovatech.Proyecto.service.ProyectoService;
import ModuloProyectosInnovatech.Proyecto.service.UsuarioClientService;

@RestController
@RequestMapping("/api/v1/proyectos")
public class ProyectoController {
    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private UsuarioClientService usuarioClientService;

    @GetMapping
    public ResponseEntity<List<Proyecto>> listar() {
    return ResponseEntity.ok(proyectoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> getProyectoById(@PathVariable Integer id) {
        try {
            // Intentamos buscar el proyecto. Si existe, devolvemos 200 OK
            Proyecto proyecto = proyectoService.getProyectoById(id);
            return ResponseEntity.ok(proyecto); 
        } catch (RuntimeException e) {
            // Si el servicio lanza la excepción ydevolvemos 404 Not Found
            return ResponseEntity.notFound().build(); 
        }
    }
    
    @PostMapping
    public ResponseEntity<Proyecto> crear(@RequestBody ProyectoDTO dto) {
    return ResponseEntity.status(HttpStatus.CREATED)
    .body(proyectoService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> modificar(@PathVariable Integer id,
    @RequestBody ProyectoDTO dto) {
        try {
            return ResponseEntity.ok(proyectoService.modificar(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
    proyectoService.eliminar(id);
    return ResponseEntity.noContent().build();
    }

    // Endpoint enriquecido: devuelve el proyecto junto con los datos del jefe
    // obtenidos del microservicio externo de Usuarios
    @GetMapping("/{id}/detalle")
    public ResponseEntity<ProyectoConJefeDTO> getDetalle(@PathVariable Integer id) {
        try {
            Proyecto proyecto = proyectoService.getProyectoById(id);

            // Consulta el microservicio externo; puede devolver null si está caído
            UsuarioExternoDTO jefe = usuarioClientService.obtenerUsuarioPorId(proyecto.getJefeId());

            return ResponseEntity.ok(new ProyectoConJefeDTO(proyecto, jefe));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
