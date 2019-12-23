package pet.backend.restserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pet.backend.restserver.entity.FileEntity;
import pet.backend.restserver.entity.User;
import pet.backend.restserver.repository.FileEntityRepository;
import pet.backend.restserver.repository.UserRepository;

import java.net.URI;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

@RestController
@RequestMapping(value="/user")
public class UserController {
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserRepository userRepository;
    @Autowired
    FileEntityRepository fileEntityRepository;

    @RequestMapping(value = "/{userId}", params = "!status") //TODO: вот это еще не доделал
    public Callable<ResponseEntity<User>> getUserById(@PathVariable String userId){
        return ()-> {
            try{
                User u = userRepository.findById(UUID.fromString(userId)).orElseThrow();
                return ResponseEntity.ok(u);
            } catch (Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        };
    }

    @RequestMapping(value = "/add")
    public Callable<ResponseEntity<?>> addUser(@RequestBody User user){
        return ()-> {
            try{
                User u = userRepository.save(user);
                return ResponseEntity.created(URI.create(u.getId().toString())).build();
            } catch(Exception e) {
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }



}
