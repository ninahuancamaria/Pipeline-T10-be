package vallegrande.luSanchezMiranda.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vallegrande.luSanchezMiranda.model.Ubigeo;
import vallegrande.luSanchezMiranda.service.UbigeoService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/ubigeo")
public class UbigeoRest {

    @Autowired
    private UbigeoService service;

    @GetMapping
    public List<Ubigeo> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Ubigeo listarPorId(@PathVariable Integer id) {
        return service.listarPorId(id);
    }

    @PostMapping
    public Ubigeo guardar(@RequestBody Ubigeo ubigeo) {
        return service.guardar(ubigeo);
    }

    @PutMapping("/{id}")
    public Ubigeo actualizar(@PathVariable Integer id, @RequestBody Ubigeo ubigeo) {
        return service.actualizar(id, ubigeo);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}