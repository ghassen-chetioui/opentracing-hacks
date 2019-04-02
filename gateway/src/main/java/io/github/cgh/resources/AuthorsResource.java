package io.github.cgh.resources;

import io.github.cgh.services.AuthorsService;
import io.github.cgh.services.AuthorsService.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/authors")
public class AuthorsResource {

    private final AuthorsService service;

    @Autowired
    public AuthorsResource(AuthorsService service) {
        this.service = service;
    }

    @GetMapping
    public List<Author> authors() {
        return service.all();
    }

    @GetMapping("/{id}")
    public Author author(@PathVariable String id) {
        return service.byId(id);
    }
}
