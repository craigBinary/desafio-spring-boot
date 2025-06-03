package com.example.springboot.taskmanagementapi.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot.taskmanagementapi.dto.TareaDTO;
import com.example.springboot.taskmanagementapi.entities.EstadoTarea;
import com.example.springboot.taskmanagementapi.entities.Tarea;
import com.example.springboot.taskmanagementapi.entities.Usuario;
import com.example.springboot.taskmanagementapi.repositories.EstadoTareaRepository;
import com.example.springboot.taskmanagementapi.repositories.TareaRepository;
import com.example.springboot.taskmanagementapi.repositories.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class TareaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EstadoTareaRepository estadoTareaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Tarea tarea;
    private TareaDTO tareaDTO;
    private Usuario usuario;
    private EstadoTarea estadoTarea;

    @BeforeEach
    void setUp() {
        // Limpiar datos anteriores
        tareaRepository.deleteAll();
        usuarioRepository.deleteAll();
        estadoTareaRepository.deleteAll();
        
        // Configurar datos de prueba
        usuario = new Usuario();
        usuario.setNombreUsuario("Usuario Test");
        usuario.setCorreo("usuario.test@test.com");
        usuario.setClave("password123");
        usuario = usuarioRepository.save(usuario);

        estadoTarea = new EstadoTarea();
        estadoTarea.setNombreEstado("Pendiente");
        estadoTarea = estadoTareaRepository.save(estadoTarea);

        tarea = new Tarea();
        tarea.setTitulo("Tarea Test");
        tarea.setDescripcion("Descripción Test");
        tarea.setUsuario(usuario);
        tarea.setEstadoTarea(estadoTarea);
        tarea = tareaRepository.save(tarea);

        tareaDTO = new TareaDTO();
        tareaDTO.setTitulo("Nueva Tarea");
        tareaDTO.setDescripcion("Nueva Descripción");
        tareaDTO.setUsuarioId(usuario.getId());
        tareaDTO.setEstadoTareaId(estadoTarea.getId());
    }

    @Test
    @WithMockUser(roles = "USER")
    void listShouldReturnTareas() throws Exception {
        mockMvc.perform(get("/api/v1/tareas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Tarea Test"))
                .andExpect(jsonPath("$[0].descripcion").value("Descripción Test"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void createShouldReturnNewTarea() throws Exception {
        mockMvc.perform(post("/api/v1/tareas")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tareaDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("Nueva Tarea"))
                .andExpect(jsonPath("$.descripcion").value("Nueva Descripción"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void updateShouldReturnUpdatedTarea() throws Exception {
        tareaDTO.setTitulo("Tarea Actualizada");
        mockMvc.perform(put("/api/v1/tareas/" + tarea.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tareaDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("Tarea Actualizada"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void deleteShouldReturnDeletedTarea() throws Exception {
        mockMvc.perform(delete("/api/v1/tareas/" + tarea.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Tarea Test"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void viewShouldReturnTarea() throws Exception {
        mockMvc.perform(get("/api/v1/tareas/" + tarea.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Tarea Test"))
                .andExpect(jsonPath("$.descripcion").value("Descripción Test"));
    }
} 