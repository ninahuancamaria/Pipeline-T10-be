package vallegrande.luSanchezMiranda.service;

import vallegrande.luSanchezMiranda.model.Employee;
import java.util.List;

public interface EmployeeService {
    List<Employee> listar();
    List<Employee> listarPorEstado(String status);
    Employee listarPorId(Integer id);
    Employee guardar(Employee employee);
    Employee actualizar(Integer id, Employee employee);
    Employee eliminarLogico(Integer id);
    Employee restaurar(Integer id);
}
