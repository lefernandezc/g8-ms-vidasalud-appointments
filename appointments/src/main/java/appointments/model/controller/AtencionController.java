package appointments.model.controller;

import appointments.model.Atencion;
import appointments.model.EstadoAtencion;
import appointments.model.service.AtencionService;
import appointments.model.view.AtencionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/atenciones")
@RequiredArgsConstructor
public class AtencionController {
    private final AtencionService service;

    @PostMapping
    public ResponseEntity<AtencionResponse> crearAtencion(@RequestBody Atencion request){
        Atencion creada = service.crear(request);
        AtencionResponse vista = AtencionResponse.desdeModelo(creada, "Atención agendada correctamente");
        return new ResponseEntity<>(vista, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<AtencionResponse> actualizarEstado(
            @PathVariable Long id,
            @RequestParam EstadoAtencion nuevoEstado){
        Atencion actualizada = service.cambiarEstado(id, nuevoEstado);
        AtencionResponse vista = AtencionResponse.desdeModelo(actualizada, "Estado actualizado");
        return ResponseEntity.ok(vista);
    }

    @GetMapping
    public ResponseEntity<List<AtencionResponse>> listarTodas() {
        List<AtencionResponse> listaVistas = service.listar().stream()
                .map(atencion -> AtencionResponse.desdeModelo(atencion, "Listado ok"))
                        .toList();
        return ResponseEntity.ok(listaVistas);
    }


}
