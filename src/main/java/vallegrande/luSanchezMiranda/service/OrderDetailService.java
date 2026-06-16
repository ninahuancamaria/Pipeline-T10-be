package vallegrande.luSanchezMiranda.service;

import vallegrande.luSanchezMiranda.model.OrderDetail;
import java.util.List;

public interface OrderDetailService {
    List<OrderDetail> listar();
    OrderDetail listarPorId(Integer id);
    OrderDetail guardar(OrderDetail orderDetail);
    OrderDetail actualizar(Integer id, OrderDetail orderDetail);
    void eliminar(Integer id);
}
