package appointments.mapper;

import appointments.dto.AtencionDTO;
import appointments.model.Atencion;
//import appointments.dto.DoctorDTO;
//import appointments.dto.PacienteDTO;
//import appointments.model.Doctor;
//import appointments.model.Paciente;


public class Mapper {

    public static AtencionDTO toDTO(Atencion a){
        if (a == null) return null;

        return AtencionDTO.builder()
                .id(a.getId())
                .paciente(a.getPaciente())
                .doctor(a.getDoctor())
                .horaFecha(a.getHoraFecha())
                .build();
    }

//    public static DoctorDTO toDTO(Doctor d){
//        if (d == null) return null;
//
//        return DoctorDTO.builder()
//                .id(d.getId())
//                .nombre(d.getNombre())
//                .especialidad(d.getEspecialidad())
//                .build();
//    }
//
//    public static PacienteDTO toDTO(Paciente p){
//        if (p == null) return null;
//
//        return PacienteDTO.builder()
//                .id(p.getId())
//                .nombre(p.getNombre())
//                .direccion(p.getDireccion())
////                .atencion(p.getAtencion() == null ? List.of() : p.getAtencion().stream()
////                        .map(p -> toDTO(p))
////                        .toList())
//                .build();
//
//    }

}
