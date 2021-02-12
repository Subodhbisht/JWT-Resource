package sb.resource.jwtresource.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sb.resource.jwtresource.entity.User;
import sb.resource.jwtresource.exception.UserNotFoundException;
import sb.resource.jwtresource.model.UserSearchCriteria;
import sb.resource.jwtresource.service.UserService;

@RestController
public class UserController {

    @Autowired
    WebClient webClientBuilder;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/saveUser")
    public ResponseEntity<Mono<User>> saveUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Mono<User> userMonouser = webClientBuilder.post()
                .uri("/saveUser")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(user), User.class)
                .retrieve()
                .bodyToMono(User.class);
        return new ResponseEntity(userMonouser, HttpStatus.OK);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<Mono<User>> updateUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Mono<User> userMonouser = webClientBuilder.post()
                .uri("/updateUser")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(user), User.class)
                .retrieve()
                .bodyToMono(User.class);
        return new ResponseEntity(userMonouser, HttpStatus.OK);
    }

    @PostMapping("/getUsers")
    public Flux<User> getUsers(@RequestBody UserSearchCriteria criteria) {
        return webClientBuilder.post()
                .uri("/getUsers")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(criteria), UserSearchCriteria.class)
                .retrieve()
                .bodyToFlux(User.class)
                .map(user -> {
                    user.setPassword("VinodGeeta@2");
                    return user;
                });
    }

    @GetMapping("/user/{userName}")
    public ResponseEntity<Mono<?>> getUser(@PathVariable String userName) {
        Mono<User> user = webClientBuilder.get().uri("/user/userName")
                .retrieve().bodyToMono(User.class);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}