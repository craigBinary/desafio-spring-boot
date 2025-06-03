package com.example.springboot.taskmanagementapi.entities;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column(name = "id_usuario")
    private Long id;

    @Column(unique = true)
    private String nombreUsuario;

    private String clave;

    @Column(unique = true)
    private String correo;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarea> tarea;
    

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

    
    public Usuario(String nombreUsuario, String clave) {
        this();
        this.nombreUsuario = nombreUsuario;
        this.clave = clave;
    }

    
    public Usuario() {
        //task = new ArrayList<>();
    }
    
    public List<Tarea> getTarea() {
        return tarea;
    }

    public void setTarea(List<Tarea> tarea) {
        this.tarea = tarea;
    }

    @Override
    public String toString() {
        return "{id=" + id + ", usuario=" + nombreUsuario + ", clave=" + clave + ", tarea=" + tarea + "}";
    }
  
}
