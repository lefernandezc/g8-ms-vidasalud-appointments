package appointments.service;

import appointments.dto.AtencionDTO;

import java.util.List;

public interface AtencionServiceImpl {

    List<AtencionDTO> traerAtencion();
    AtencionDTO crearAtencion(AtencionDTO atencionDto);
    AtencionDTO actualizarAtencion(Long id, AtencionDTO atencionDto);
    void eliminarAtencion (Long id);
}
