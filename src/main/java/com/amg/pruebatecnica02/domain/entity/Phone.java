package com.amg.pruebatecnica02.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity(name = "phones")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Phone {
    @Id
    private UUID phoneId;
    @Column(unique = true)
    private String number;
    private String cityCode;
    private String contryCode;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuario_id",nullable = false)
    private Usuario usuario;

    @PrePersist
    void getData(){
        if (phoneId==null){
            phoneId=UUID.randomUUID();
        }
    }
}
