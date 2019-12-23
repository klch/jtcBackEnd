package pet.backend.restserver.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import pet.backend.restserver.entity.FileEntity;
import pet.backend.restserver.repository.FileEntityRepository;

import java.io.IOException;
import java.util.UUID;

public class CustomFileEntityDeserializer extends StdDeserializer<FileEntity> {
    @Autowired
    FileEntityRepository fileEntityRepository;

    public CustomFileEntityDeserializer(){
        this(null);
    }
    public CustomFileEntityDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public FileEntity deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        UUID id = UUID.fromString(jsonParser.getText());
        FileEntity fileEntity = fileEntityRepository.findById(id).get();
        return fileEntity;
    }
}
