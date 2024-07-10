package com.amg.pruebatecnica02.domain.entity;

import com.amg.pruebatecnica02.domain.entity.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "Roles")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rolesId;
    @Enumerated(EnumType.STRING)
    private RoleName roleName;
}
