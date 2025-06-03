package com.example.springboot.taskmanagementapi.services;

import java.util.List;
import java.util.Optional;

import com.example.springboot.taskmanagementapi.entities.Tarea;

public interface TareaService {

    List<Tarea> findAll();
    Optional<Tarea> findById(Long id);
    Tarea save(Tarea tarea);
    Optional<Tarea> update(Long id,Tarea tarea);
    Optional<Tarea> delete(Long id);
}
