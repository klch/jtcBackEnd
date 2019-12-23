package pet.backend.restserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.multipart.MultipartFile;
import pet.backend.restserver.config.FileUploadConfig;
import pet.backend.restserver.entity.FileEntity;
import pet.backend.restserver.repository.FileEntityRepository;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.util.Map;

@RestController
public class FileController {
    private final int uploadTimeout = 5000;
    public static Logger logger = LoggerFactory.getLogger(FileController.class);
    @Autowired
    private FileEntityRepository fileEntityRepository;
    @Autowired
    private FileUploadConfig fileUploadConfig;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public WebAsyncTask<ResponseEntity<?>> handleFileUpload(@RequestParam("name") final String name, @RequestParam("file") final MultipartFile file) {
        return  new WebAsyncTask <ResponseEntity <?>>(uploadTimeout, () -> {
            if(!file.isEmpty()) {
                FileEntity fileEntity = new FileEntity();
                fileEntity.setFileName(name);
                fileEntity.setClassName(fileEntity.getClass().getName());
                fileEntityRepository.save(fileEntity);
                String newName = fileEntity.getId() + "-" + name;
                String filePath = fileUploadConfig.getFilePath();
                try (FileChannel fileChannel = FileChannel.open(new File(filePath + newName).toPath(), StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE)) {
                    byte[] bytes = file.getBytes();
                    ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
                    fileChannel.write(byteBuffer);
                    return ResponseEntity.created(URI.create(fileUploadConfig.getUrlPath() + "/" + fileEntity.getId().toString())).build();
                } catch (IOException e) {
                    e.printStackTrace();
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        });
    }
}
