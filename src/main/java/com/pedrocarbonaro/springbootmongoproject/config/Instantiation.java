package com.pedrocarbonaro.springbootmongoproject.config;

import com.pedrocarbonaro.springbootmongoproject.domain.Post;
import com.pedrocarbonaro.springbootmongoproject.dto.AuthorDTO;
import com.pedrocarbonaro.springbootmongoproject.dto.CommentDTO;
import com.pedrocarbonaro.springbootmongoproject.repository.PostRepository;
import com.pedrocarbonaro.springbootmongoproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.pedrocarbonaro.springbootmongoproject.domain.User;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        userRepository.deleteAll();
        postRepository.deleteAll();

        User maria = new User(null, "Maria Brown", "maria@gmail.com");
        User alex = new User(null, "Alex Green", "alex@gmail.com");
        User bob = new User(null, "Bob Grey", "bob@gmail.com");

        userRepository.saveAll(Arrays.asList(maria, alex, bob));

        Post post1 = new Post(null, sdf.parse("21/03/2018"), "Let's trip!", "I'm coming to São Paulo, hugs!!", new AuthorDTO(maria));
        Post post2 = new Post(null, sdf.parse("23/03/2018"), "Good morning!", "i woke up happy today.", new AuthorDTO(maria));

        CommentDTO c1 = new CommentDTO("Good trip bro!", sdf.parse("21/03/2018"), new AuthorDTO(alex));
        CommentDTO c2 = new CommentDTO("Enjoy the trip!", sdf.parse("22/03/2018"), new AuthorDTO(bob));
        CommentDTO c3 = new CommentDTO("Have a nice day!", sdf.parse("23/03/2018"), new AuthorDTO(alex));

        post1.getComments().addAll(Arrays.asList(c1, c2));
        post2.getComments().add(c3);

        postRepository.saveAll(Arrays.asList(post1, post2));

        maria.getPosts().addAll(Arrays.asList(post1, post2));
        userRepository.save(maria);
    }
}