package com.example.springboot.taskmanagementapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioDTO {

    private Long id;

    @NotBlank(message = "es requerido")
    private String nombreUsuario;

    @Size(min = 8, max = 20, message = "debe tener entre 8 y 20 caracteres")
    @NotBlank(message = "es requerida")
    private String clave;

    @NotBlank(message = "es requerido")
    @Email(message = "electrónico es inválido",regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,5}")
    private String correo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }


}
