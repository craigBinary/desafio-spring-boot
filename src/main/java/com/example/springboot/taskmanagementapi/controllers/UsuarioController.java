package com.example.springboot.taskmanagementapi.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.springboot.taskmanagementapi.dto.UsuarioDTO;
import com.example.springboot.taskmanagementapi.dto.UsuarioResponseDTO;
import com.example.springboot.taskmanagementapi.entities.Usuario;
import com.example.springboot.taskmanagementapi.mapper.UsuarioMapper;
import com.example.springboot.taskmanagementapi.services.UsuarioService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "Endpoints para el mantenedor de usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Autowired
    private UsuarioMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> list() {
        List<UsuarioResponseDTO> usuarios = service.findAll()
            .stream()
            .map(usuario -> mapper.toDTO(usuario))
            .collect(Collectors.toList());
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = service.findById(id);
        Map<String, String> bodyResponse = new HashMap<>();
        if (!usuarioOptional.isPresent()) {
            bodyResponse.put("Mensaje", "Usuario no encontrado");
            bodyResponse.put("Status", HttpStatus.NOT_FOUND.toString());
            return ResponseEntity.badRequest().body(bodyResponse);
        }
        
        return ResponseEntity.ok(mapper.toDTO(usuarioOptional.get()));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UsuarioDTO usuarioDTO, BindingResult result) {
        if(result.hasFieldErrors()) {
            return validation(result);
        }

        Map<String, String> bodyResponse = new HashMap<>();

        Optional<Usuario> usuarioOptional = service.findByNombreUsuario(usuarioDTO.getNombreUsuario());
        if (usuarioOptional.isPresent()) {
            bodyResponse.put("Mensaje", "Usuario ya existe");
            bodyResponse.put("Status", HttpStatus.CONFLICT.toString());
            return ResponseEntity.badRequest().body(bodyResponse);
        }

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setClave(passwordEncoder.encode(usuarioDTO.getClave()));

        Usuario savedUsuario = service.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(savedUsuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody UsuarioDTO usuarioDTO, BindingResult result, @PathVariable Long id) {
        if(result.hasFieldErrors()) {
            return validation(result);
        }

        Map<String, String> bodyResponse = new HashMap<>();
        Optional<Usuario> usuarioOptional = service.findById(id);
        if (!usuarioOptional.isPresent()) {
            bodyResponse.put("Mensaje", "Usuario no encontrado");
            bodyResponse.put("Status", HttpStatus.NOT_FOUND.toString());
            return ResponseEntity.badRequest().body(bodyResponse);
        }

        Usuario usuarioDb = usuarioOptional.get();
        usuarioDb.setNombreUsuario(usuarioDTO.getNombreUsuario());
        usuarioDb.setCorreo(usuarioDTO.getCorreo());
        usuarioDb.setClave(passwordEncoder.encode(usuarioDTO.getClave()));

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(service.save(usuarioDb)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Usuario> usuarioOptional = service.delete(id);
        Map<String, String> bodyResponse = new HashMap<>();

        if(usuarioOptional.isPresent()){
            return ResponseEntity.ok(mapper.toDTO(usuarioOptional.get()));
        }
        bodyResponse.put("Mensaje", "Usuario no encontrado");
        bodyResponse.put("Status", HttpStatus.NOT_FOUND.toString());
        return ResponseEntity.badRequest().body(bodyResponse);
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
