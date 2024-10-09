package kz.bitlab.middle02.micro02.micro02.repository;

import jakarta.transaction.Transactional;
import kz.bitlab.middle02.micro02.micro02.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
}
