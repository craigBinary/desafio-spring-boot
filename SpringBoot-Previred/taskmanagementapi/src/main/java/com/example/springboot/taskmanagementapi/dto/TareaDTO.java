package com.example.springboot.taskmanagementapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TareaDTO {
    
    @NotBlank(message = "de la tarea es requerido")
    private String titulo;
    
    @NotBlank(message = "de la tarea es requerida")
    private String descripcion;
    
    @NotNull(message = "del estado de tarea es requerido")
    private Long estadoTareaId;
    
    @NotNull(message = "del usuario es requerido")
    private Long usuarioId;

    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getEstadoTareaId() {
        return estadoTareaId;
    }

    public void setEstadoTareaId(Long estadoTareaId) {
        this.estadoTareaId = estadoTareaId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
} 