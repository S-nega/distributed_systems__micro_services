package kz.bitlab.middle02.micro01.micro01.service;

import kz.bitlab.middle02.micro01.micro01.dto.TaskDto;
import kz.bitlab.middle02.micro01.micro01.dto.UserDto;
import kz.bitlab.middle02.micro01.micro01.entity.Task;
import kz.bitlab.middle02.micro01.micro01.exception.UserNotFoundException;
import kz.bitlab.middle02.micro01.micro01.feign.UserFeignClient;
import kz.bitlab.middle02.micro01.micro01.mapper.TaskMapper;
import kz.bitlab.middle02.micro01.micro01.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserFeignClient userFeignClient;

    public List<TaskDto> getTasks() {
        return taskMapper.toDtoList(taskRepository.findAll());
    }

    public TaskDto getTask(Long id) {
        return taskMapper.toDto(taskRepository.findById(id).orElse(null));
    }

    public TaskDto addTask(TaskDto task) {
        UserDto userDto = userFeignClient.getUser(task.getUserId());
        if (userDto != null) {
            task.setStatus(1);
            return taskMapper.toDto(taskRepository.save(taskMapper.toEntity(task)));
        }
        throw new UserNotFoundException(task.getUserId());
    }

    public TaskDto updateTask(TaskDto task) {
        UserDto userDto = userFeignClient.getUser(task.getUserId());
        if (userDto != null) {
            return taskMapper.toDto(taskRepository.save(taskMapper.toEntity(task)));
        }
        throw new UserNotFoundException(task.getUserId());
    }

    public void deleteTask(Long id) {
        Task deleteTask = taskRepository.findById(id).orElse(null);
        taskRepository.delete(deleteTask);
    }
}
