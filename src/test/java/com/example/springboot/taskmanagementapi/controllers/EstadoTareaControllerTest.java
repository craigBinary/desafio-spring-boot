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

import com.example.springboot.taskmanagementapi.entities.EstadoTarea;
import com.example.springboot.taskmanagementapi.repositories.EstadoTareaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class EstadoTareaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EstadoTareaRepository estadoTareaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private EstadoTarea estadoTarea;

    @BeforeEach
    void setUp() {
        // Limpiar datos anteriores
        estadoTareaRepository.deleteAll();
        
        // Configurar datos de prueba
        estadoTarea = new EstadoTarea();
        estadoTarea.setNombreEstado("Pendiente");
        estadoTarea = estadoTareaRepository.save(estadoTarea);
    }

    @Test
    @WithMockUser(roles = "USER")
    void listShouldReturnEstadosTarea() throws Exception {
        mockMvc.perform(get("/api/v1/estados")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreEstado").value("Pendiente"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void createShouldReturnNewEstadoTarea() throws Exception {
        EstadoTarea nuevoEstado = new EstadoTarea();
        nuevoEstado.setNombreEstado("En Progreso");

        mockMvc.perform(post("/api/v1/estados")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoEstado)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombreEstado").value("En Progreso"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void createShouldReturnErrorWhenInvalidData() throws Exception {
        EstadoTarea invalidEstado = new EstadoTarea();
        // No establecemos el nombre que es requerido

        mockMvc.perform(post("/api/v1/estados")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidEstado)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "USER")
    void deleteShouldReturnDeletedEstadoTarea() throws Exception {
        mockMvc.perform(delete("/api/v1/estados/" + estadoTarea.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreEstado").value("Pendiente"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void deleteShouldReturnNotFoundWhenEstadoTareaDoesNotExist() throws Exception {
        mockMvc.perform(delete("/api/v1/estados/999")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
} 