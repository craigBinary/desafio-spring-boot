package com.example.springboot.taskmanagementapi.dto;

import java.util.List;

public class UsuarioResponseDTO {

    private Long id;
    private String nombreUsuario;
    private String correo;
    private List<TareaSimpleDTO> tarea;
    
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
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public List<TareaSimpleDTO> getTarea() {
        return tarea;
    }
    public void setTarea(List<TareaSimpleDTO> tarea) {
        this.tarea = tarea;
    }

    
}
