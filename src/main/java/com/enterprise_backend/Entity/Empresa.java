package com.enterprise_backend.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.*;


import java.util.List;
@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Empresa {

    @Id
    private String nit;

    private String nombre;
    private String direccion;
    private String telefono;


}