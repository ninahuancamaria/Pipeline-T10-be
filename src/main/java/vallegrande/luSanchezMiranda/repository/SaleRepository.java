package vallegrande.luSanchezMiranda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vallegrande.luSanchezMiranda.model.Sale;

/**
 * Repositorio JPA para acceder a los datos de la tabla SALE (Ventas).
 */
public interface SaleRepository extends JpaRepository<Sale, Integer> {
}
