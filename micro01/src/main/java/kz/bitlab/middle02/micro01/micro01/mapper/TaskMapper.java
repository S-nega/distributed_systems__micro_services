package kz.bitlab.middle02.micro01.micro01.mapper;

import kz.bitlab.middle02.micro01.micro01.dto.TaskDto;
import kz.bitlab.middle02.micro01.micro01.entity.Task;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskDto toDto(Task task);

    Task toEntity(TaskDto taskDto);

    List<TaskDto> toDtoList(List<Task> tasks);

}
