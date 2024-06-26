package com.pedrocarbonaro.springbootmongoproject.controllers;

import com.pedrocarbonaro.springbootmongoproject.domain.Post;
import com.pedrocarbonaro.springbootmongoproject.domain.User;

import com.pedrocarbonaro.springbootmongoproject.dto.UserDTO;
import com.pedrocarbonaro.springbootmongoproject.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        List<User> list = userService.findAll();
        List<UserDTO> listDto = list.stream().map(UserDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok().body(new UserDTO(userService.findById(id)));
    }

    @GetMapping(value = "/{id}/posts")
    public ResponseEntity<List<Post>> findPosts(@PathVariable String id) {
        return ResponseEntity.ok().body(userService.findById(id).getPosts());
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody UserDTO objDTO) {
        User obj = userService.insert(userService.fromDTO(objDTO));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> update(@RequestBody UserDTO objDTO, @PathVariable String id) {
        User obj = userService.fromDTO(objDTO);
        obj.setId(id);
        obj = userService.update(obj);
        return ResponseEntity.noContent().build();
    }
}