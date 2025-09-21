package com.example.demo.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clientes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "tipoDocumento es obligatorio")
    private String tipoDocumento;

    @NotBlank(message = "numeroDocumento es obligatorio")
    @Column(unique = true)
    private String numeroDocumento;

    @NotBlank(message = "primerNombre es obligatorio")
    private String primerNombre;

    private String segundoNombre;

    @NotBlank(message = "primerApellido es obligatorio")
    private String primerApellido;

    private String segundoApellido;

    @NotNull(message = "telefono es obligatorio")
    private Integer telefono;

    @NotBlank(message = "correoElectronico es obligatorio")
    @Email(message = "correoElectronico debe tener un formato válido")
    private String correoElectronico;
}
