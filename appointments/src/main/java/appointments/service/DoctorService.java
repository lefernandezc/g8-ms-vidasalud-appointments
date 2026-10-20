//package appointments.service;
//
//import appointments.dto.DoctorDTO;
//import appointments.exception.NotFoundException;
//import appointments.mapper.Mapper;
//import appointments.model.Doctor;
//import appointments.repository.DoctorRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class DoctorService implements DoctorServiceImpl {
//
//    @Autowired
//    private DoctorRepository repo;
//
//    @Override
//    public List<DoctorDTO> traerDoctor() {
//        return repo.findAll().stream().map(Mapper::toDTO).toList();
//    }
//
//    @Override
//    public DoctorDTO crearDoctor(DoctorDTO doctorDto) {
//        var doc = Doctor.builder()
//                .nombre(doctorDto.getNombre())
//                .especialidad(doctorDto.getEspecialidad())
//                .build();
//
//        return Mapper.toDTO(repo.save(doc));
//    }
//
//    @Override
//    public DoctorDTO actualizarDoctor(Long id, DoctorDTO doctorDto) {
//
//        Doctor doc = repo.findById(id)
//        .orElseThrow(() -> new NotFoundException("Doctor no encontrado"));
//
//        doc.setNombre(doctorDto.getNombre());
//        doc.setEspecialidad(doctorDto.getEspecialidad());
//
//        return Mapper.toDTO(repo.save(doc));
//    }
//
//    @Override
//    public void eliminarDoctor(Long id) {
//
//        if (!repo.existsById(id)){
//            throw new NotFoundException("Doctor no encontrado para eliminar");
//        }
//
//        repo.deleteById(id);
//    }
//}
