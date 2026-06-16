package vallegrande.luSanchezMiranda.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vallegrande.luSanchezMiranda.model.Employee;
import vallegrande.luSanchezMiranda.service.EmployeeService;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/employee")
@Tag(name = "Employee", description = "CRUD de Empleados")
public class EmployeeRest {

    @Autowired
    private EmployeeService service;

    @Operation(summary = "Listar empleados", description = "Obtiene la lista de empleados, opcionalmente filtrada por estado.")
    @GetMapping
    public List<Employee> listar(@RequestParam(required = false) String status) {
        if (status != null) {
            return service.listarPorEstado(status);
        }
        return service.listar();
    }

    @Operation(summary = "Buscar empleado por ID")
    @GetMapping("/{id}")
    public Employee listarPorId(@PathVariable Integer id) {
        return service.listarPorId(id);
    }

    @Operation(summary = "Crear empleado")
    @PostMapping
    public Employee guardar(@RequestBody Employee employee) {
        return service.guardar(employee);
    }

    @Operation(summary = "Actualizar empleado")
    @PutMapping("/{id}")
    public Employee actualizar(@PathVariable Integer id, @RequestBody Employee employee) {
        return service.actualizar(id, employee);
    }

    @Operation(summary = "Eliminación lógica de empleado")
    @DeleteMapping("/{id}")
    public Employee eliminar(@PathVariable Integer id) {
        return service.eliminarLogico(id);
    }

    @Operation(summary = "Restaurar empleado inactivo")
    @PatchMapping("/restaurar/{id}")
    public Employee restaurar(@PathVariable Integer id) {
        return service.restaurar(id);
    }
}
