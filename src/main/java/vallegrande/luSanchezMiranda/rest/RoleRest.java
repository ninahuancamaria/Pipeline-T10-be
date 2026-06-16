package vallegrande.luSanchezMiranda.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vallegrande.luSanchezMiranda.model.Role;
import vallegrande.luSanchezMiranda.service.RoleService;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/role")
@Tag(name = "Role", description = "CRUD de Roles")
public class RoleRest {

    @Autowired
    private RoleService service;

    @Operation(summary = "Listar roles", description = "Obtiene la lista de roles, opcionalmente filtrada por estado.")
    @GetMapping
    public List<Role> listar(@RequestParam(required = false) String status) {
        if (status != null) {
            return service.listarPorEstado(status);
        }
        return service.listar();
    }

    @Operation(summary = "Buscar rol por ID")
    @GetMapping("/{id}")
    public Role listarPorId(@PathVariable Integer id) {
        return service.listarPorId(id);
    }

    @Operation(summary = "Crear rol")
    @PostMapping
    public Role guardar(@RequestBody Role role) {
        return service.guardar(role);
    }

    @Operation(summary = "Actualizar rol")
    @PutMapping("/{id}")
    public Role actualizar(@PathVariable Integer id, @RequestBody Role role) {
        return service.actualizar(id, role);
    }

    @Operation(summary = "Eliminación lógica de rol")
    @DeleteMapping("/{id}")
    public Role eliminar(@PathVariable Integer id) {
        return service.eliminarLogico(id);
    }

    @Operation(summary = "Restaurar rol inactivo")
    @PatchMapping("/restaurar/{id}")
    public Role restaurar(@PathVariable Integer id) {
        return service.restaurar(id);
    }
}
