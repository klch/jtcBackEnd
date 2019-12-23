package pet.backend.restserver.repository;

import org.springframework.data.repository.CrudRepository;
import pet.backend.restserver.entity.BaseEntity;

import java.util.UUID;

public interface BaseEntityRepository extends CrudRepository<BaseEntity, UUID> {
}
