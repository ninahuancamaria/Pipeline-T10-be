package vallegrande.luSanchezMiranda.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vallegrande.luSanchezMiranda.model.ProductionBatch;
import vallegrande.luSanchezMiranda.service.ProductionBatchService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/production-batch")
public class ProductionBatchRest {

    @Autowired
    private ProductionBatchService service;

    @GetMapping
    public List<ProductionBatch> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ProductionBatch listarPorId(@PathVariable Integer id) {
        return service.listarPorId(id);
    }

    @PostMapping
    public ProductionBatch guardar(@RequestBody ProductionBatch batch) {
        return service.guardar(batch);
    }

    @PutMapping("/{id}")
    public ProductionBatch actualizar(@PathVariable Integer id, @RequestBody ProductionBatch batch) {
        return service.actualizar(id, batch);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}
