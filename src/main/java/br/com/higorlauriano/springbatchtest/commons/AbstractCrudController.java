package br.com.higorlauriano.springbatchtest.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public abstract class AbstractCrudController<T extends AbstractEntity> {

    @Autowired
    private AbstractService<T> abstractService;

    @GetMapping("/{id}")
    public ResponseEntity<T> findOne(@PathVariable("id") Long id) {

        return abstractService.findOne(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping
    public ResponseEntity<Page<T>> findPaged(@RequestParam(value = "page", defaultValue = "0") int page,
                                             @RequestParam(value = "size", defaultValue = "10") int size) {

        return ResponseEntity.ok(abstractService.findPaged(page, size));
    }

    @PostMapping
    public ResponseEntity<T> save(@RequestBody T object) {

        return ResponseEntity.status(HttpStatus.CREATED).body(abstractService.save(object));
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> edit(@PathVariable("id") Long id, @RequestBody T object) {

        return ResponseEntity.ok(abstractService.edit(id, object));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {

        abstractService.delete(id);

        return ResponseEntity.ok().build();
    }

}
