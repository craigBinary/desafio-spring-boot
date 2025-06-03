package com.example.springboot.taskmanagementapi.services;

import java.util.List;
import java.util.Optional;
import com.example.springboot.taskmanagementapi.entities.Usuario;

public interface UsuarioService {
    List<Usuario> findAll();
    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    Usuario save(Usuario usuario);
    Optional<Usuario> update(Long id,Usuario usuario);
    Optional<Usuario> delete(Long id);
} 