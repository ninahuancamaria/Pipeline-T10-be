package vallegrande.luSanchezMiranda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vallegrande.luSanchezMiranda.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
