package vallegrande.luSanchezMiranda.rest;

import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vallegrande.luSanchezMiranda.model.Category;
import vallegrande.luSanchezMiranda.model.Supplier;
import vallegrande.luSanchezMiranda.model.Ubigeo;
import vallegrande.luSanchezMiranda.service.SupplierService;

import java.util.List;

@RestController
@RequestMapping("/api/supplier")
// Controlador REST para gestionar proveedores y su contrato de entrada simplificado.
public class SupplierRest {

    @Autowired
    private SupplierService service;

    // LISTAR
    @GetMapping
    public List<Supplier> listar() {
        return service.listar();
    }

    // LISTAR POR ID
    @GetMapping("/{id}")
    public Supplier listarPorId(@PathVariable Integer id) {
        return service.listarPorId(id);
    }

    // LISTAR POR ESTADO
    @GetMapping("/estado/{status}")
    public List<Supplier> listarPorEstado(@PathVariable Boolean status) {
        return service.listarPorEstado(status);
    }

    // Crea un proveedor usando solo los campos que el cliente puede editar manualmente.
    @PostMapping
    public Supplier guardar(@Valid @RequestBody SupplierRequest request) {
        return service.guardar(toEntity(request));
    }

    // EDITAR
    @PutMapping("/{id}")
    public Supplier actualizar(@PathVariable Integer id, @Valid @RequestBody SupplierRequest request) {
        return service.actualizar(id, toEntity(request));
    }

    // Convierte el DTO de entrada al modelo de persistencia antes de enviarlo al servicio.
    private Supplier toEntity(SupplierRequest request) {
        Supplier supplier = new Supplier();
        supplier.setCompanyName(request.getCompanyName());
        supplier.setRuc(request.getRuc());
        supplier.setPhone(request.getPhone());
        supplier.setEmail(request.getEmail());
        supplier.setAddress(request.getAddress());
        supplier.setStatus(request.getStatus());

        if (request.getUbigeoCode() != null) {
            Ubigeo ubigeo = new Ubigeo();
            ubigeo.setUbigeoCode(request.getUbigeoCode());
            supplier.setUbigeo(ubigeo);
        }

        if (request.getCategoryId() != null) {
            Category category = new Category();
            category.setCategoryId(request.getCategoryId());
            supplier.setCategory(category);
        }

        return supplier;
    }

    @Data
    public static class SupplierRequest {
        private String companyName;
        private String ruc;
        private Long phone;
        private String email;
        private String address;
        private Boolean status;
        private Integer ubigeoCode;
        private Integer categoryId;
    }

    // ELIMINAR LOGICO
    @PatchMapping("/eliminar/{id}")
    public Supplier eliminar(@PathVariable Integer id) {
        return service.eliminarLogico(id);
    }

    // RESTAURAR
    @PatchMapping("/restaurar/{id}")
    public Supplier restaurar(@PathVariable Integer id) {
        return service.restaurar(id);
    }
}
