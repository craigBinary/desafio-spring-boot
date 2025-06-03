package com.example.springboot.taskmanagementapi.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.springboot.taskmanagementapi.entities.EstadoTarea;
import com.example.springboot.taskmanagementapi.services.EstadoTareaService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/estados")
@Tag(name = "Estados de Tarea", description = "Endpoints para el mantenedor de los estados de las tareas")
public class EstadoTareaController {

@Autowired 
private EstadoTareaService service;

@GetMapping
public List<EstadoTarea> list(){
    return service.findAll();
}

@PostMapping
public ResponseEntity<?> create(@Valid @RequestBody EstadoTarea estadoTarea, BindingResult result){
    if(result.hasFieldErrors()){
        return validation(result);
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(service.save(estadoTarea));
}

@DeleteMapping("/{id}")
public ResponseEntity<?> delete(@PathVariable Long id){
    Optional<EstadoTarea> estadoTareaOptional = service.delete(id);
    if(estadoTareaOptional.isPresent()){
        return ResponseEntity.ok(estadoTareaOptional.orElseThrow());
    }
    return ResponseEntity.notFound().build();
}

private ResponseEntity<?> validation(BindingResult result) {
    Map<String, String> errors = new HashMap<>();
    result.getFieldErrors().forEach(err -> {
        errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
    });
    return ResponseEntity.badRequest().body(errors);
}

}
