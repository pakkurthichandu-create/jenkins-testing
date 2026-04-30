package com.example.helloworld;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.StreamSupport;

@Controller
public class TaskController {

    private final TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String index(Model model) {
        Iterable<Task> allTasks = repository.findAll();
        List<Task> taskList = StreamSupport.stream(allTasks.spliterator(), false).toList();
        
        long totalTasks = taskList.size();
        long completedTasks = taskList.stream().filter(Task::isCompleted).count();
        int progress = totalTasks > 0 ? (int) ((completedTasks * 100) / totalTasks) : 0;

        model.addAttribute("tasks", taskList);
        model.addAttribute("total", totalTasks);
        model.addAttribute("completed", completedTasks);
        model.addAttribute("progress", progress);
        
        return "index";
    }

    @PostMapping("/addTask")
    public String addTask(@RequestParam String description) {
        if (description != null && !description.trim().isEmpty()) {
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
