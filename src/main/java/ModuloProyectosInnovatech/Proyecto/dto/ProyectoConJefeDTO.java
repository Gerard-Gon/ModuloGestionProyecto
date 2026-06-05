package ModuloProyectosInnovatech.Proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import ModuloProyectosInnovatech.Proyecto.model.Proyecto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProyectoConJefeDTO {

    private Proyecto proyecto;
    private UsuarioExternoDTO jefe;

}
