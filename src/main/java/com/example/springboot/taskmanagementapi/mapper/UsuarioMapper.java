package com.example.springboot.taskmanagementapi.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.springboot.taskmanagementapi.dto.TareaSimpleDTO;
import com.example.springboot.taskmanagementapi.dto.UsuarioResponseDTO;
import com.example.springboot.taskmanagementapi.entities.Tarea;
import com.example.springboot.taskmanagementapi.entities.Usuario;

@Component
public class UsuarioMapper {
    
    public UsuarioResponseDTO toDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNombreUsuario(usuario.getNombreUsuario());
        dto.setCorreo(usuario.getCorreo());
        
        if (usuario.getTarea() != null) {
            dto.setTarea(usuario.getTarea().stream()
                .map(this::convertTareaToDTO)
                .collect(Collectors.toList())); 
        }
        
        return dto;
    }
    
    private TareaSimpleDTO convertTareaToDTO(Tarea tarea) {
        TareaSimpleDTO dto = new TareaSimpleDTO();
        dto.setId(tarea.getId());
        dto.setTitulo(tarea.getTitulo());
        dto.setDescripcion(tarea.getDescripcion());
        dto.setEstadoTarea(tarea.getEstadoTarea().getNombreEstado());
        return dto;
    }
}
