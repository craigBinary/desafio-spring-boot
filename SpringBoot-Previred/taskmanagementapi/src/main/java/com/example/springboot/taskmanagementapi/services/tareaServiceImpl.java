package com.example.springboot.taskmanagementapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot.taskmanagementapi.entities.Tarea;
import com.example.springboot.taskmanagementapi.repositories.TareaRepository;

@Service
public class tareaServiceImpl implements TareaService{

    @Autowired
    private TareaRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<Tarea> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Tarea> findById(Long id) {
        return repository.findById(id);
    }


    @Transactional
    @Override
    public Optional<Tarea> delete(Long id) {
        Optional<Tarea> tareaOptional = repository.findById(id);
        tareaOptional.ifPresent(tareaDb -> {
            repository.delete(tareaDb);
            
         });
         return tareaOptional;
        
    }


    @Transactional
    @Override
    public Tarea save(Tarea tarea) {
        return repository.save(tarea);
    }

    @Transactional
    @Override
    public Optional<Tarea> update(Long id, Tarea tarea) {
        Optional<Tarea> tareaOptional = repository.findById(id);
        if(tareaOptional.isPresent()){
            Tarea tareaDb = tareaOptional.orElseThrow();
            tareaDb.setTitulo(tarea.getTitulo());
            tareaDb.setDescripcion(tarea.getDescripcion());
            //taskDb.setTask_status(task.getTask_status());
            return Optional.of(repository.save(tareaDb));
            
         }
         return tareaOptional;
    }    

}
