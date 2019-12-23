package pet.backend.restserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pet.backend.restserver.config.FileUploadConfig;
import pet.backend.restserver.entity.FileEntity;
import pet.backend.restserver.repository.FileEntityRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.UUID;

@Controller
public class PictureController {
    public static Logger logger = LoggerFactory.getLogger(PictureController.class);
    @Autowired
    private FileEntityRepository fileEntityRepository;
    @Autowired
    private FileUploadConfig fileUploadConfig;

    @ResponseBody
    @RequestMapping(value = "/picture/{pictureId}", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> getPicture(@PathVariable UUID pictureId){
        logger.warn("pictureId " + pictureId);

        FileEntity fileEntity = fileEntityRepository.findById(pictureId).get();
        File file = new File(fileUploadConfig.getFilePath() + "/" + pictureId + "-" + fileEntity.getFileName());
        logger.warn("canonical path " + file.getAbsolutePath());
        try {
            return ResponseEntity.ok().contentLength(file.length()).contentType(MediaType.IMAGE_PNG).body(new InputStreamResource(new FileInputStream(file)));
        } catch (FileNotFoundException e) {
            return null;
        }
    }
}
