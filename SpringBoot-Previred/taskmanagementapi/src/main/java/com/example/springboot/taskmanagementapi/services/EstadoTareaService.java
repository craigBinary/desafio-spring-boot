package com.example.springboot.taskmanagementapi.services;

import java.util.List;
import java.util.Optional;
import com.example.springboot.taskmanagementapi.entities.EstadoTarea;

public interface EstadoTareaService {

    List<EstadoTarea> findAll();
    Optional<EstadoTarea> findById(Long id);
    EstadoTarea save(EstadoTarea estadoTarea);
    Optional<EstadoTarea> delete(Long id);
}
