package com.example.taskmanager.service;

import org.springframework.stereotype.Service;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.UserRepository;
import com.example.taskmanager.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepo;
    private final UserRepository userRepo;

    public TaskService(TaskRepository taskRepo, UserRepository userRepo){
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
    }

    public List<Task> findAll(){ return taskRepo.findAll(); }

    public Optional<Task> findById(Long id){ return taskRepo.findById(id); }

    public Task save(Task t){ return taskRepo.save(t); }

    public void deleteById(Long id){ taskRepo.deleteById(id); }

    public List<Task> findByOwner(String username){ return taskRepo.findByOwnerUsername(username); }
}
