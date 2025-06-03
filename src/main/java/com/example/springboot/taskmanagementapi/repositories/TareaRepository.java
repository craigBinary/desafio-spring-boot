package com.example.springboot.taskmanagementapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springboot.taskmanagementapi.entities.Tarea;

public interface TareaRepository extends JpaRepository<Tarea,Long>{

}

