package vallegrande.luSanchezMiranda.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vallegrande.luSanchezMiranda.model.Employee;
import vallegrande.luSanchezMiranda.repository.EmployeeRepository;
import vallegrande.luSanchezMiranda.service.EmployeeService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<Employee> listar() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Employee> listarPorEstado(String status) {
        return repository.findByStatusIgnoreCase(status);
    }

    @Transactional(readOnly = true)
    @Override
    public Employee listarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Employee guardar(Employee employee) {
        employee.setEmployeeId(null); // Asegurar creación
        return repository.save(employee);
    }

    @Transactional
    @Override
    public Employee actualizar(Integer id, Employee employee) {
        Employee existing = repository.findById(id).orElse(null);
        if (existing != null) {
            if (employee.getName() != null) existing.setName(employee.getName());
            if (employee.getLastName() != null) existing.setLastName(employee.getLastName());
            if (employee.getDocumentType() != null) existing.setDocumentType(employee.getDocumentType());
            if (employee.getDocumentNumber() != null) existing.setDocumentNumber(employee.getDocumentNumber());
            if (employee.getEmail() != null) existing.setEmail(employee.getEmail());
            if (employee.getPhone() != null) existing.setPhone(employee.getPhone());
            if (employee.getAddress() != null) existing.setAddress(employee.getAddress());
            if (employee.getEntryDate() != null) existing.setEntryDate(employee.getEntryDate());
            if (employee.getPassword() != null) existing.setPassword(employee.getPassword());
            if (employee.getUbigeo() != null) existing.setUbigeo(employee.getUbigeo());
            if (employee.getRole() != null) existing.setRole(employee.getRole());
            if (employee.getStatus() != null) existing.setStatus(employee.getStatus());
            return repository.save(existing);
        }
        return null;
    }

    @Transactional
    @Override
    public Employee eliminarLogico(Integer id) {
        Employee existing = repository.findById(id).orElse(null);
        if (existing != null) {
            existing.setStatus("inactivo");
            existing.setDeletedAt(LocalDateTime.now());
            return repository.save(existing);
        }
        return null;
    }

    @Transactional
    @Override
    public Employee restaurar(Integer id) {
        Employee existing = repository.findById(id).orElse(null);
        if (existing != null) {
            existing.setStatus("activo");
            existing.setRestoredAt(LocalDateTime.now());
            return repository.save(existing);
        }
        return null;
    }
}
