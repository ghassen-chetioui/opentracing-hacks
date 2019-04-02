package io.github.cgh.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/books")
public class BooksResource {

    @GetMapping
    public String books() {
        return "H2G2, Origin";
    }

    @GetMapping("/{id}")
    public String book(@PathVariable String id) {
        return "H2G2-" + id;
    }
}
