package vallegrande.luSanchezMiranda.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vallegrande.luSanchezMiranda.model.Sale;
import vallegrande.luSanchezMiranda.dto.SaleRequest;
import vallegrande.luSanchezMiranda.service.SaleService;

import java.util.List;

/**
 * Controlador REST que expone los endpoints HTTP para la gestión de Ventas.
 * Permite listar, buscar, registrar, actualizar y anular ventas de forma unificada.
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/api/sale")
@Tag(name = "Sale", description = "CRUD de Ventas")
public class SaleRest {

    @Autowired
    private SaleService service;

    /**
     * Endpoint GET para obtener todas las ventas.
     * Retorna la cabecera de la venta junto con sus respectivos detalles asociados.
     */
    @Operation(summary = "Listar ventas")
    @GetMapping
    public List<Sale> listar() {
        return service.listar();
    }

    /**
     * Endpoint GET para buscar una venta por su ID.
     */
    @Operation(summary = "Buscar venta por ID")
    @GetMapping("/{id}")
    public Sale listarPorId(@PathVariable Integer id) {
        return service.listarPorId(id);
    }

    /**
     * Endpoint POST para registrar una nueva venta con sus detalles en una sola petición.
     * Recibe un SaleRequest (JSON simplificado) y lo convierte en entidad antes de guardar.
     */
    @Operation(summary = "Crear venta")
    @PostMapping
    public Sale guardar(@RequestBody SaleRequest request) {
        return service.guardar(request.toEntity());
    }

    /**
     * Endpoint PUT para actualizar una venta existente.
     * Recibe los nuevos datos y la lista actualizada de detalles de la venta.
     */
    @Operation(summary = "Actualizar venta")
    @PutMapping("/{id}")
    public Sale actualizar(@PathVariable Integer id, @RequestBody SaleRequest request) {
        return service.actualizar(id, request.toEntity());
    }

    /**
     * Endpoint DELETE para realizar una eliminación lógica (anulación) de una venta.
     * Cambia el estado del registro a "anulada" sin borrar físicamente los datos.
     */
    @Operation(summary = "Eliminación lógica de venta (anular)")
    @DeleteMapping("/{id}")
    public Sale eliminar(@PathVariable Integer id) {
        return service.eliminar(id);
    }
}
