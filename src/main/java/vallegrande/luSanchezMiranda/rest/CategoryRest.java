package vallegrande.luSanchezMiranda.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vallegrande.luSanchezMiranda.model.Category;
import vallegrande.luSanchezMiranda.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryRest {

    @Autowired
    private CategoryService service;

    @GetMapping
    public List<Category> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Category listarPorId(@PathVariable Integer id) {
        return service.listarPorId(id);
    }

    @PostMapping
    public Category guardar(@RequestBody Category category) {
        return service.guardar(category);
    }

    @PutMapping("/{id}")
    public Category actualizar(@PathVariable Integer id, @RequestBody Category category) {
        return service.actualizar(id, category);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}
