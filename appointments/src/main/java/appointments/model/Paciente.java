//package appointments.model;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Getter @Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class Paciente {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String nombre;
//    private String direccion;
//
//    @OneToMany(mappedBy = "paciente")
//    private List<Atencion> atencion = new ArrayList<>();
//
//}
