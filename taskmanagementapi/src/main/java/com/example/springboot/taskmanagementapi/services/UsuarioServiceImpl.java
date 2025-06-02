package com.example.springboot.taskmanagementapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot.taskmanagementapi.entities.Usuario;
import com.example.springboot.taskmanagementapi.repositories.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<Usuario> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Usuario> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Usuario> findByNombreUsuario(String nombreUsuario) {
        return repository.findByNombreUsuario(nombreUsuario);
    }

    @Transactional
    @Override
    public Usuario save(Usuario usuario) {
        return repository.save(usuario);
    }

    @Transactional
    @Override
    public Optional<Usuario> delete(Long id) {
        Optional<Usuario> usuarioOptional = repository.findById(id);
        usuarioOptional.ifPresent(usuarioDb -> {
            repository.delete(usuarioDb);
        });
        return usuarioOptional;
    }

    @Transactional
    @Override
    public Optional<Usuario> update(Long id,Usuario usuario) {
        Optional<Usuario> usuarioOptional = repository.findById(usuario.getId());
        if(usuarioOptional.isPresent()){
            Usuario usuarioDb = usuarioOptional.orElseThrow();
            usuarioDb.setNombreUsuario(usuario.getNombreUsuario());
            usuarioDb.setCorreo(usuario.getCorreo());
            usuarioDb.setClave(usuario.getClave());
            return Optional.of(repository.save(usuarioDb));
            
         }
         return usuarioOptional;
    } 

    
} 