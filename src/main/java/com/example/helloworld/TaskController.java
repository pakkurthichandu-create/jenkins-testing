package com.example.helloworld;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TaskController {

    private final TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("tasks", repository.findAll());
        return "index";
    }

    @PostMapping("/addTask")
    public String addTask(@RequestParam String description) {
        if (!description.isEmpty()) {
            repository.save(new Task(description));
        }
        return "redirect:/";
    }

    @GetMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/toggleTask/{id}")
    public String toggleTask(@PathVariable Long id) {
        Task task = repository.findById(id).orElseThrow();
        task.setCompleted(!task.isCompleted());
        repository.save(task);
        return "redirect:/";
    }
}
