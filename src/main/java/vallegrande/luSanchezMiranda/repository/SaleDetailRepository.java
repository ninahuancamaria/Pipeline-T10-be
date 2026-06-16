package vallegrande.luSanchezMiranda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vallegrande.luSanchezMiranda.model.SaleDetail;

/**
 * Repositorio JPA para acceder a los datos de la tabla SALE_DETAILS.
 */
public interface SaleDetailRepository extends JpaRepository<SaleDetail, Integer> {
    
    /**
     * Elimina todos los detalles de venta asociados a un ID de venta específico.
     * Utilizado para limpiezas en cascada manuales si son necesarias.
     * @param saleId Identificador de la venta padre.
     */
    void deleteBySale_SaleId(Integer saleId);
}
