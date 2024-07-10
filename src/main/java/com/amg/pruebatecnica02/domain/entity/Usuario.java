package com.amg.pruebatecnica02.domain.entity;


import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Entity(name = "usuarios")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    private UUID usuarioId;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private LocalDate fechaCreacion;
    private LocalDate fechaModificacion;
    private Boolean isActive;
    private String lastLogin;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "usuario")
    private List<Phone> phones =new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinTable(name = "usuario_roles",joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> roles;

    @PrePersist
    private void setDatos(){
        if (usuarioId==null){
            usuarioId=UUID.randomUUID();
        }
        if (lastLogin==null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            lastLogin=LocalDateTime.now().format(formatter);
        }
        if (fechaCreacion==null){
            fechaCreacion=LocalDate.now();
        }
        if (fechaModificacion ==null){
            fechaModificacion =LocalDate.now();
        }
        if (isActive==null){
            isActive=true;
        }
    }
}
