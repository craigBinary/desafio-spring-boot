package com.example.springboot.taskmanagementapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot.taskmanagementapi.entities.EstadoTarea;
import com.example.springboot.taskmanagementapi.repositories.EstadoTareaRepository;

@Service
public class EstadoTareaServiceImpl implements EstadoTareaService{

    @Autowired
    private EstadoTareaRepository repository;

     @Transactional(readOnly = true)
    @Override
    public List<EstadoTarea> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<EstadoTarea> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    @Override
    public Optional<EstadoTarea> delete(Long id) {
        Optional<EstadoTarea> EstadoTareaOptional = repository.findById(id);
        EstadoTareaOptional.ifPresent(tareaDb -> {
            repository.delete(tareaDb);
            
         });
         return EstadoTareaOptional;
        
    }


    @Transactional
    @Override
    public EstadoTarea save(EstadoTarea estadoTarea) {
        return repository.save(estadoTarea);
    }

}
