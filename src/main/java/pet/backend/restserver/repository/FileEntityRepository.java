package pet.backend.restserver.repository;


import org.springframework.data.repository.CrudRepository;
import pet.backend.restserver.entity.FileEntity;

import java.util.UUID;

public interface FileEntityRepository extends CrudRepository<FileEntity, UUID> {
}
