package com.example.springboot.taskmanagementapi.entities;

import jakarta.persistence.*;


@Entity
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column(name = "id_tarea")
    private Long id;

    private String titulo;

    private String descripcion;
    
    @ManyToOne
    @JoinColumn (name= "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_estado_tarea", nullable = false) 
    private EstadoTarea estadoTarea;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
    
    public EstadoTarea getEstadoTarea() {
        return estadoTarea;
    }
    
    public void setEstadoTarea(EstadoTarea estadoTarea) {
        this.estadoTarea = estadoTarea;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public Tarea() {
    }
    
    public Tarea(String titulo, String descripcion, Usuario usuario, EstadoTarea estadoTarea) {
        this();
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.usuario = usuario;
        this.estadoTarea = estadoTarea;
    }
    
}
