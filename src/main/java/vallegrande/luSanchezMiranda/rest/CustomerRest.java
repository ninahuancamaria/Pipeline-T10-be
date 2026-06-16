package vallegrande.luSanchezMiranda.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vallegrande.luSanchezMiranda.model.Customer;
import vallegrande.luSanchezMiranda.service.CustomerService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/customer")
@Tag(name = "Customer", description = "CRUD de Clientes")
public class CustomerRest {

    @Autowired
    private CustomerService service;

    // LISTAR (con filtros opcionales)
    @Operation(summary = "Listar clientes", description = "Obtiene la lista completa de clientes con filtros opcionales por estado y tipo.")
    @GetMapping
    public List<Customer> listar(
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) String type) {
        if (status != null && type != null) {
            return service.listarPorEstadoYTipo(status, type);
        } else if (status != null) {
            return service.listarPorEstado(status);
        } else if (type != null) {
            return service.listarPorTipo(type);
        }
        return service.listar();
    }

    // LISTAR POR ID
    @Operation(summary = "Buscar cliente por ID", description = "Obtiene los datos de un cliente específico según su ID.")
    @GetMapping("/{id}")
    public Customer listarPorId(@PathVariable Integer id) {
        return service.listarPorId(id);
    }

    // LISTAR POR ESTADO (Mantenido por retrocompatibilidad)
    @GetMapping("/estado/{status}")
    public List<Customer> listarPorEstado(@PathVariable Boolean status) {
        return service.listarPorEstado(status);
    }

    // LISTAR POR TIPO
    @GetMapping("/tipo/{type}")
    public List<Customer> listarPorTipo(@PathVariable String type) {
        return service.listarPorTipo(type);
    }

    // CREAR
    @Operation(summary = "Crear nuevo cliente", description = "Registra un nuevo cliente en el sistema.")
    @PostMapping
    public Customer guardar(@Valid @RequestBody Customer customer) {
        return service.guardar(customer);
    }

    // EDITAR
    @Operation(summary = "Actualizar cliente", description = "Modifica los datos de un cliente existente.")
    @PutMapping("/{id}")
    public Customer actualizar(@PathVariable Integer id, @Valid @RequestBody Customer customer) {
        return service.actualizar(id, customer);
    }

    // ELIMINAR LOGICO
    @Operation(summary = "Eliminar cliente (Lógico)", description = "Cambia el estado del cliente a 'inactivo' y registra la fecha de eliminación.")
    @PatchMapping("/eliminar/{id}")
    public Customer eliminar(@PathVariable Integer id) {
        return service.eliminarLogico(id);
    }

    // RESTAURAR
    @Operation(summary = "Restaurar cliente", description = "Cambia el estado del cliente a 'activo' y registra la fecha de restauración.")
    @PatchMapping("/restaurar/{id}")
    public Customer restaurar(@PathVariable Integer id) {
        return service.restaurar(id);
    }
}