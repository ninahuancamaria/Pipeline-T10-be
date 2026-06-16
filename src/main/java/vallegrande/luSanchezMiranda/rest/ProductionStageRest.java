package vallegrande.luSanchezMiranda.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vallegrande.luSanchezMiranda.model.ProductionStage;
import vallegrande.luSanchezMiranda.service.ProductionStageService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/production-stage")
public class ProductionStageRest {

    @Autowired
    private ProductionStageService service;

    @GetMapping
    public List<ProductionStage> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ProductionStage listarPorId(@PathVariable Integer id) {
        return service.listarPorId(id);
    }

    @PostMapping
    public ProductionStage guardar(@RequestBody ProductionStage stage) {
        return service.guardar(stage);
    }

    @PutMapping("/{id}")
    public ProductionStage actualizar(@PathVariable Integer id, @RequestBody ProductionStage stage) {
        return service.actualizar(id, stage);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}
