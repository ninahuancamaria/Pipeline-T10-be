package vallegrande.luSanchezMiranda.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vallegrande.luSanchezMiranda.model.OrderDetail;
import vallegrande.luSanchezMiranda.repository.OrderDetailRepository;
import vallegrande.luSanchezMiranda.service.OrderDetailService;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<OrderDetail> listar() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public OrderDetail listarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public OrderDetail guardar(OrderDetail orderDetail) {
        orderDetail.setIdOrderDetail(null);
        return repository.save(orderDetail);
    }

    @Transactional
    @Override
    public OrderDetail actualizar(Integer id, OrderDetail orderDetail) {
        OrderDetail existing = repository.findById(id).orElse(null);
        if (existing != null) {
            if (orderDetail.getQuantity() != null) existing.setQuantity(orderDetail.getQuantity());
            if (orderDetail.getUnitPrice() != null) existing.setUnitPrice(orderDetail.getUnitPrice());
            if (orderDetail.getSubtotal() != null) existing.setSubtotal(orderDetail.getSubtotal());
            if (orderDetail.getOrder() != null) existing.setOrder(orderDetail.getOrder());
            if (orderDetail.getProductSale() != null) existing.setProductSale(orderDetail.getProductSale());
            return repository.save(existing);
        }
        return null;
    }

    @Transactional
    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}
