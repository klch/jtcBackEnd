package pet.backend.restserver.repository;

import org.springframework.data.repository.CrudRepository;
import pet.backend.restserver.entity.User;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
}
