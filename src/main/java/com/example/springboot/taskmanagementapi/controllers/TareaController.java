package com.example.springboot.taskmanagementapi.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.springboot.taskmanagementapi.dto.TareaDTO;
import com.example.springboot.taskmanagementapi.dto.TareaResponseDTO;
import com.example.springboot.taskmanagementapi.entities.EstadoTarea;
import com.example.springboot.taskmanagementapi.entities.Tarea;
import com.example.springboot.taskmanagementapi.entities.Usuario;
import com.example.springboot.taskmanagementapi.mapper.TareaMapper;
import com.example.springboot.taskmanagementapi.services.EstadoTareaService;
import com.example.springboot.taskmanagementapi.services.TareaService;
import com.example.springboot.taskmanagementapi.services.UsuarioService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tareas")
@Tag(name = "Tareas", description = "Endpoints para el mantenedor de tareas")
public class TareaController {

    @Autowired
    private TareaService service;

    @Autowired
    private EstadoTareaService estadoTareaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TareaMapper mapper;

    @GetMapping
    public ResponseEntity<List<TareaResponseDTO>> list() {
        List<TareaResponseDTO> tareas = service.findAll()
            .stream()
            .map(tarea -> mapper.toDTO(tarea))
            .collect(Collectors.toList());
        return ResponseEntity.ok(tareas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id) {
        Optional<Tarea> tareaOptional = service.findById(id);
        Map<String, String> bodyResponse = new HashMap<>();

        if (!tareaOptional.isPresent()) {
            bodyResponse.put("Mensaje", "Tarea no encontrado");
            bodyResponse.put("Status", HttpStatus.NOT_FOUND.toString());
            return ResponseEntity.badRequest().body(bodyResponse);
        }else if(tareaOptional.isPresent()){
            return ResponseEntity.ok(mapper.toDTO(tareaOptional.get()));
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody TareaDTO tareaDTO, BindingResult result) {
        if(result.hasFieldErrors()) {
            return validation(result);
        }

        Map<String, String> bodyResponse = new HashMap<>();
        // Buscar el estado de tarea
        Optional<EstadoTarea> estadoTarea = estadoTareaService.findById(tareaDTO.getEstadoTareaId());
        if (!estadoTarea.isPresent()) {
            bodyResponse.put("Mensaje", "Estado de tarea no encontrado");
            bodyResponse.put("Status", HttpStatus.NOT_FOUND.toString());
            return ResponseEntity.badRequest().body(bodyResponse);
        }

        // Buscar el usuario
        Optional<Usuario> usuario = usuarioService.findById(tareaDTO.getUsuarioId());
        if (!usuario.isPresent()) {
            bodyResponse.put("Mensaje", "Usuario no encontrado");
            bodyResponse.put("Status", HttpStatus.NOT_FOUND.toString());
            return ResponseEntity.badRequest().body(bodyResponse);
        }

        // Crear la nueva tarea
        Tarea tarea = new Tarea();
        tarea.setTitulo(tareaDTO.getTitulo());
        tarea.setDescripcion(tareaDTO.getDescripcion());
        tarea.setEstadoTarea(estadoTarea.get());
        tarea.setUsuario(usuario.get());

        Tarea savedTarea = service.save(tarea);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(savedTarea));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody TareaDTO tareaDTO, BindingResult result, @PathVariable Long id) {
        if(result.hasFieldErrors()) {
            return validation(result);
        }

        Map<String, String> bodyResponse = new HashMap<>();
        Optional<Tarea> tareaOptional = service.findById(id);

        if(!tareaOptional.isPresent()) {
            bodyResponse.put("Mensaje", "Tarea no encontrado");
            bodyResponse.put("Status", HttpStatus.NOT_FOUND.toString());
            return ResponseEntity.badRequest().body(bodyResponse);
        }

        Optional<EstadoTarea> estadoTarea = estadoTareaService.findById(tareaDTO.getEstadoTareaId());
        if (!estadoTarea.isPresent()) {
            bodyResponse.put("Mensaje", "Estado de tarea no encontrado");
            bodyResponse.put("Status", HttpStatus.NOT_FOUND.toString());
            return ResponseEntity.badRequest().body(bodyResponse);
        }

        Optional<Usuario> usuario = usuarioService.findById(tareaDTO.getUsuarioId());
        if (!usuario.isPresent()) {
            bodyResponse.put("Mensaje", "Usuario no encontrado");
            bodyResponse.put("Status", HttpStatus.NOT_FOUND.toString());
            return ResponseEntity.badRequest().body(bodyResponse);
        }

        Tarea tareaDb = tareaOptional.get();
        tareaDb.setTitulo(tareaDTO.getTitulo());
        tareaDb.setDescripcion(tareaDTO.getDescripcion());
        tareaDb.setEstadoTarea(estadoTarea.get());
        tareaDb.setUsuario(usuario.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(service.save(tareaDb)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Tarea> tareaOptional = service.delete(id);
        if(tareaOptional.isPresent()) {
            return ResponseEntity.ok(mapper.toDTO(tareaOptional.get()));
        }
        Map<String, String> bodyResponse = new HashMap<>();
        bodyResponse.put("Mensaje", "Tarea no encontrado");
        bodyResponse.put("Status", HttpStatus.NOT_FOUND.toString());
        return ResponseEntity.badRequest().body(bodyResponse);
    }

    private ResponseEntity<?> validation(BindingResult result) {
        return ResponseEntity.badRequest().body(
            result.getFieldErrors().stream()
                .collect(Collectors.toMap(
                    err -> err.getField(),
                    err -> "El campo " + err.getField() + " " + err.getDefaultMessage()
                ))
        );
    }
}
