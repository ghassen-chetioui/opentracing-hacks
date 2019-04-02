package io.github.cgh.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class AuthorsService {

    public static class Author {
        public String name;
        public String nationality;
    }

    private final RestTemplate restTemplate;

    @Autowired
    public AuthorsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Author> all() {
        ResponseEntity<Author[]> response = restTemplate.getForEntity("http://localhost:8081/authors", Author[].class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return Arrays.asList(Objects.requireNonNull(response.getBody()));
        } else {
            throw new IllegalStateException("Could not retrieve authors");
        }
    }

    public Author byId(String id) {
        ResponseEntity<Author> response = restTemplate.getForEntity("http://localhost:8081/authors/" + id, Author.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else if (response.getStatusCode().is4xxClientError()) {
            throw new IllegalArgumentException("Author with id " + id + "was not found");
        } else {
            throw new IllegalStateException("Could not retrieve author");
        }
    }


}
