package com.example.springboot.taskmanagementapi.services;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot.taskmanagementapi.entities.Usuario;
import com.example.springboot.taskmanagementapi.repositories.UsuarioRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService{

    @Autowired
    private UsuarioRepository repository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<Usuario> usuarioOptional = repository.findByNombreUsuario(username);
       if(usuarioOptional.isEmpty()){
        throw new UsernameNotFoundException(String.format("Nombre %s no existe en base de datos", username));
       }
       Usuario usuario = usuarioOptional.orElseThrow();

       return new org.springframework.security.core.userdetails.User(
        usuario.getNombreUsuario(), 
        usuario.getClave(),
        true,
        true,
        true,
        true,
        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")) 
        );
        }
    }