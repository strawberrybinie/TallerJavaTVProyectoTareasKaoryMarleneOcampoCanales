package com.example.taskmanager.controller;

import org.springframework.web.bind.annotation.*;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import com.example.taskmanager.repository.UserRepository;
import com.example.taskmanager.model.User;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final UserRepository userRepo;

    public TaskController(TaskService taskService, UserRepository userRepo){
        this.taskService = taskService;
        this.userRepo = userRepo;
    }

    @GetMapping
    public List<Task> listAll(Authentication auth){
        // ADMIN sees all, USER sees only own tasks
        if (auth.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ROLE_ADMIN"))) {
            return taskService.findAll();
        } else {
            return taskService.findByOwner(auth.getName());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getById(@PathVariable Long id, Authentication auth){
        Optional<Task> t = taskService.findById(id);
        if (t.isEmpty()) return ResponseEntity.notFound().build();
        Task task = t.get();
        if (auth.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ROLE_ADMIN"))
            || task.getOwner().getUsername().equals(auth.getName())) {
            return ResponseEntity.ok(task);
        }
        return ResponseEntity.status(403).build();
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Task> create(@RequestBody Task task, Authentication auth){
        User owner = userRepo.findByUsername(auth.getName()).orElse(null);
        task.setOwner(owner);
        Task saved = taskService.save(task);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Task> update(@PathVariable Long id, @RequestBody Task incoming){
        Optional<Task> t = taskService.findById(id);
        if (t.isEmpty()) return ResponseEntity.notFound().build();
        Task task = t.get();
        task.setTitle(incoming.getTitle());
        task.setDescription(incoming.getDescription());
        task.setCompleted(incoming.isCompleted());
        taskService.save(task);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        taskService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
