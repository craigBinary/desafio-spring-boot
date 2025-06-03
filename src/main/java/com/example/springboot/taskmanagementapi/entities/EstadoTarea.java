package com.example.springboot.taskmanagementapi.entities;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Entidad que representa el estado de una tarea")
@Entity
@Table(name="estado_tarea")
public class EstadoTarea {

    @Schema(description = "ID único del estado de tarea", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_tarea")
    private Long id;

    @Schema(description = "Nombre del estado de la tarea", example = "En progreso", required = true)
    @NotBlank(message = "El nombre del estado no puede estar vacío")
    @Column(name = "nombre_estado")
    private String nombreEstado;

    @Schema(description = "Lista de tareas asociadas a este estado")
    @OneToMany(mappedBy = "estadoTarea", 
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY)
    private List<Tarea> tarea = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public EstadoTarea() {
    }

    public EstadoTarea(String nombreEstado) {
        this();
        this.nombreEstado = nombreEstado;
    }
}