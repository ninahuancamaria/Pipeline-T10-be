package vallegrande.luSanchezMiranda.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vallegrande.luSanchezMiranda.dto.OrderRequest;
import vallegrande.luSanchezMiranda.dto.OrderResponse;
import vallegrande.luSanchezMiranda.model.Order;
import vallegrande.luSanchezMiranda.service.OrderService;
import vallegrande.luSanchezMiranda.service.impl.OrderServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/order")
@Tag(name = "Order", description = "CRUD de Pedidos con Detalles")
public class OrderRest {

    @Autowired
    private OrderService service;

    @Autowired
    private OrderServiceImpl serviceImpl;

    @Operation(summary = "Listar todos los pedidos")
    @GetMapping
    public ResponseEntity<List<OrderResponse>> listar() {
        List<Order> orders = service.listar();
        List<OrderResponse> responses = orders.stream()
                .map(serviceImpl::convertEntityToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Buscar pedido por ID")
    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Pedido encontrado",
            content = @Content(schema = @Schema(implementation = OrderResponse.class)))
    public ResponseEntity<OrderResponse> listarPorId(@PathVariable Integer id) {
        Order order = service.listarPorId(id);
        if (order != null) {
            return ResponseEntity.ok(serviceImpl.convertEntityToResponse(order));
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Crear nuevo pedido con detalles",
            description = "Crea un pedido incluyendo sus líneas de detalle")
    @PostMapping
    @ApiResponse(responseCode = "201", description = "Pedido creado exitosamente",
            content = @Content(schema = @Schema(implementation = OrderResponse.class)))
    public ResponseEntity<OrderResponse> guardar(@RequestBody OrderRequest orderRequest) {
        Order order = serviceImpl.convertRequestToEntity(orderRequest);
        Order savedOrder = service.guardar(order);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(serviceImpl.convertEntityToResponse(savedOrder));
    }

    @Operation(summary = "Actualizar pedido")
    @PutMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Pedido actualizado",
            content = @Content(schema = @Schema(implementation = OrderResponse.class)))
    public ResponseEntity<OrderResponse> actualizar(@PathVariable Integer id, 
                                                     @RequestBody OrderRequest orderRequest) {
        Order orderToUpdate = serviceImpl.convertRequestToEntity(orderRequest);
        Order updatedOrder = service.actualizar(id, orderToUpdate);
        if (updatedOrder != null) {
            return ResponseEntity.ok(serviceImpl.convertEntityToResponse(updatedOrder));
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Eliminación lógica de pedido")
    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Pedido eliminado")
    public ResponseEntity<OrderResponse> eliminar(@PathVariable Integer id) {
        Order deletedOrder = service.eliminarLogico(id);
        if (deletedOrder != null) {
            return ResponseEntity.ok(serviceImpl.convertEntityToResponse(deletedOrder));
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Restaurar pedido inactivo")
    @PatchMapping("/restaurar/{id}")
    @ApiResponse(responseCode = "200", description = "Pedido restaurado")
    public ResponseEntity<OrderResponse> restaurar(@PathVariable Integer id) {
        Order restoredOrder = service.restaurar(id);
        if (restoredOrder != null) {
            return ResponseEntity.ok(serviceImpl.convertEntityToResponse(restoredOrder));
        }
        return ResponseEntity.notFound().build();
    }
}
