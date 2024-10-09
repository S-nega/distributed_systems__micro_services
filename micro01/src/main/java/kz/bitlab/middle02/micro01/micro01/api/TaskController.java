package kz.bitlab.middle02.micro01.micro01.api;

import kz.bitlab.middle02.micro01.micro01.dto.TaskDto;
import kz.bitlab.middle02.micro01.micro01.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/task")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public List<TaskDto> getTasks(){
        return taskService.getTasks();
    }

    @GetMapping(value = "{id}")
    public TaskDto getTask(@PathVariable(name = "id") Long id){
        return taskService.getTask(id);
    }

    @PostMapping
    public TaskDto addTask(@RequestBody TaskDto task){
        return taskService.addTask(task);
    }

    @PutMapping
    public TaskDto updateTask(@RequestBody TaskDto task){
        return taskService.updateTask(task);
    }

    @DeleteMapping(value = "{id}") // (value = "/{id}")
    public void deleteTask(@PathVariable(name = "id") Long id){
        taskService.deleteTask(id);
    }
}
