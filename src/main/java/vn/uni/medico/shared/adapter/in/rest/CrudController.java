package vn.uni.medico.shared.adapter.in.rest;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

public interface CrudController<O, F, P> {

    @PostMapping
    ResponseEntity<O> create(O dto);

    @PutMapping("/{id}")
    ResponseEntity<O> update(P id, O dto);

    @GetMapping
    ResponseEntity<Page<O>> list(Integer page, Integer size, String query, String asc, String desc);

    @PostMapping("/search")
    ResponseEntity<Page<O>> search(F filter, Integer page, Integer size, String query, String asc, String desc);

    @GetMapping("/{id}")
    ResponseEntity<O> get(P id);

    @DeleteMapping("/{id}")
    void delete(P id);
}
