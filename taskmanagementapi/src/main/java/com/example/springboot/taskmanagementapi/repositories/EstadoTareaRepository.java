package com.example.springboot.taskmanagementapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springboot.taskmanagementapi.entities.EstadoTarea;

public interface EstadoTareaRepository extends JpaRepository<EstadoTarea, Long>{

}
