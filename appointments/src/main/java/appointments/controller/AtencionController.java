package appointments.controller;

import appointments.dto.AtencionDTO;
import appointments.service.AtencionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/atenciones")
public class AtencionController {

    @Autowired
    private AtencionServiceImpl atencionService;

    @GetMapping
    public ResponseEntity<List<AtencionDTO>> traerAtenciones(){

        return ResponseEntity.ok(atencionService.traerAtencion());
    }

    @PostMapping
    public ResponseEntity<AtencionDTO> crearAtenciones(@RequestBody AtencionDTO dto){

        AtencionDTO creado = atencionService.crearAtencion(dto);

        return ResponseEntity.created(URI.create("/api/atenciones" + creado.getId())).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtencionDTO> actualizarAtenciones (@PathVariable Long id,
                                                             @RequestBody AtencionDTO dto) {
        return ResponseEntity.ok(atencionService.actualizarAtencion(id, dto));
    }

    @DeleteMapping
    public ResponseEntity<Void> borrarAtenciones (@PathVariable Long id){
        atencionService.eliminarAtencion(id);
        return ResponseEntity.noContent().build();
    }
}