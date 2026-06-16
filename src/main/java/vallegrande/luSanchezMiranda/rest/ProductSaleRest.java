package vallegrande.luSanchezMiranda.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vallegrande.luSanchezMiranda.model.ProductSale;
import vallegrande.luSanchezMiranda.service.ProductSaleService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/product-sale")
@Tag(name = "ProductSale", description = "CRUD de Productos de Venta")
public class ProductSaleRest {

    @Autowired
    private ProductSaleService service;

    @Operation(summary = "Listar productos de venta", description = "Obtiene la lista completa de productos de venta.")
    @GetMapping
    public List<ProductSale> listar() {
        return service.listar();
    }

    @Operation(summary = "Listar productos activos", description = "Obtiene la lista de productos de venta con estado activo.")
    @GetMapping("/activos")
    public List<ProductSale> listarActivos() {
        return service.listarActivos();
    }

    @Operation(summary = "Listar productos inactivos", description = "Obtiene la lista de productos de venta eliminados lógicamente.")
    @GetMapping("/inactivos")
    public List<ProductSale> listarInactivos() {
        return service.listarInactivos();
    }

    @Operation(summary = "Buscar producto por ID", description = "Obtiene los datos de un producto de venta específico según su ID.")
    @GetMapping("/{id}")
    public ProductSale listarPorId(@PathVariable Integer id) {
        return service.listarPorId(id);
    }

    @Operation(summary = "Crear producto de venta", description = "Registra un nuevo producto de venta en el sistema.")
    @PostMapping
    public ProductSale guardar(@Valid @RequestBody ProductSale product) {
        return service.guardar(product);
    }

    @Operation(summary = "Actualizar producto de venta", description = "Modifica los datos de un producto de venta existente.")
    @PutMapping("/{id}")
    public ProductSale actualizar(@PathVariable Integer id, @Valid @RequestBody ProductSale product) {
        return service.actualizar(id, product);
    }

    @Operation(summary = "Eliminación lógica de producto", description = "Marca un producto de venta como inactivo.")
    @PatchMapping("/eliminar/{id}")
    public ProductSale eliminarLogico(@PathVariable Integer id) {
        return service.eliminarLogico(id);
    }

    @Operation(summary = "Restaurar producto inactivo", description = "Restaura un producto de venta eliminado lógicamente.")
    @PatchMapping("/restaurar/{id}")
    public ProductSale restaurar(@PathVariable Integer id) {
        return service.restaurar(id);
    }
}