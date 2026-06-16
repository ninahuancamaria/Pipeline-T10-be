package vallegrande.luSanchezMiranda.service;

import vallegrande.luSanchezMiranda.model.Order;
import java.util.List;

public interface OrderService {
    List<Order> listar();
    Order listarPorId(Integer id);
    Order guardar(Order order);
    Order actualizar(Integer id, Order order);
    Order eliminarLogico(Integer id);
    Order restaurar(Integer id);
}
