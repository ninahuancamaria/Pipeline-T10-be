package vallegrande.luSanchezMiranda.rest;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vallegrande.luSanchezMiranda.model.Category;
import vallegrande.luSanchezMiranda.model.ProductSupply;
import vallegrande.luSanchezMiranda.service.ProductSupplyService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/product-supply")
// Controlador para administrar productos de abastecimiento y su payload de entrada simplificado.
public class ProductSupplyRest {

    @Autowired
    private ProductSupplyService service;

    // LISTAR TODOS
    @GetMapping
    public List<ProductSupply> listar() {
        return service.listar();
    }

    // LISTAR ACTIVOS
    @GetMapping("/activos")
    public List<ProductSupply> listarActivos() {
        return service.listarActivos();
    }

    // LISTAR INACTIVOS (ELIMINADOS)
    @GetMapping("/inactivos")
    public List<ProductSupply> listarInactivos() {
        return service.listarInactivos();
    }

    // BUSCAR POR ID
    @GetMapping("/{id}")
    public ProductSupply listarPorId(@PathVariable Integer id) {
        return service.listarPorId(id);
    }

    // Registra un nuevo producto de abastecimiento con los campos editables del formulario.
    @PostMapping
    public ProductSupply guardar(@RequestBody ProductSupplyRequest request) {
        return service.guardar(toEntity(request));
    }

    // ACTUALIZAR
    @PutMapping("/{id}")
    public ProductSupply actualizar(@PathVariable Integer id, @RequestBody ProductSupplyRequest request) {
        return service.actualizar(id, toEntity(request));
    }

    // Mapea el DTO de entrada al modelo JPA que usa la capa de servicio.
    private ProductSupply toEntity(ProductSupplyRequest request) {
        ProductSupply product = new ProductSupply();
        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setAvailableStock(request.getAvailableStock());
        product.setExpiryDate(request.getExpiryDate());
        product.setUnitMeasurement(request.getUnitMeasurement());
        product.setProductImage(request.getProductImage());

        if (request.getCategoryId() != null) {
            Category category = new Category();
            category.setCategoryId(request.getCategoryId());
            product.setCategory(category);
        }

        return product;
    }

    @Data
    public static class ProductSupplyRequest {
        private String productName;
        private String description;
        private java.math.BigDecimal price;
        private java.math.BigDecimal availableStock;
        private java.time.LocalDateTime expiryDate;
        private String unitMeasurement;
        private byte[] productImage;
        private Integer categoryId;
    }

    // ELIMINAR LOGICO
    @PatchMapping("/eliminar/{id}")
    public ProductSupply eliminarLogico(@PathVariable Integer id) {
        return service.eliminarLogico(id);
    }

    // RESTAURAR
    @PatchMapping("/restaurar/{id}")
    public ProductSupply restaurar(@PathVariable Integer id) {
        return service.restaurar(id);
    }
}
