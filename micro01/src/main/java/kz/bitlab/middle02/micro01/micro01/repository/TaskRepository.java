package kz.bitlab.middle02.micro01.micro01.repository;

import jakarta.transaction.Transactional;
import kz.bitlab.middle02.micro01.micro01.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface TaskRepository extends JpaRepository<Task, Long> {
}
