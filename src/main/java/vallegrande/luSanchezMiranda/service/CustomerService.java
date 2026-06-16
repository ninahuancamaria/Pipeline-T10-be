package vallegrande.luSanchezMiranda.service;

import vallegrande.luSanchezMiranda.model.Customer;

import java.util.List;

public interface CustomerService {

    // Operaciones de lectura
    List<Customer> listar();
    Customer listarPorId(Integer id);
    List<Customer> listarPorEstado(Boolean status);
    List<Customer> listarPorTipo(String type);
    List<Customer> listarPorEstadoYTipo(Boolean status, String type);

    // Operaciones de persistencia
    Customer guardar(Customer customer); // Crear nuevo
    Customer actualizar(Integer id, Customer customer); // Actualizar existente

    // Operaciones de estado (Baja/Alta)
    Customer eliminarLogico(Integer id); // Inactivar
    Customer restaurar(Integer id); // Activar
}