package com.example.springboot.taskmanagementapi.mapper;

import org.springframework.stereotype.Component;

import com.example.springboot.taskmanagementapi.dto.TareaResponseDTO;
import com.example.springboot.taskmanagementapi.entities.Tarea;

@Component
public class TareaMapper {
    
    public TareaResponseDTO toDTO(Tarea tarea) {
        TareaResponseDTO dto = new TareaResponseDTO();
        dto.setId(tarea.getId());
        dto.setTitulo(tarea.getTitulo());
        dto.setDescripcion(tarea.getDescripcion());
        dto.setEstadoTarea(tarea.getEstadoTarea().getNombreEstado());
        dto.setUsuario(tarea.getUsuario().getNombreUsuario());
        return dto;
    }
} 