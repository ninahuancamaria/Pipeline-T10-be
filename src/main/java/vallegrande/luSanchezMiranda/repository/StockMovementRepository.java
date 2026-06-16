package vallegrande.luSanchezMiranda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vallegrande.luSanchezMiranda.model.StockMovement;

import java.time.LocalDateTime;
import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Integer> {

    @Query("SELECT s FROM StockMovement s WHERE " +
           "(:productsSaleId IS NULL OR s.productSale.productsSaleId = :productsSaleId) AND " +
           "(:startDate IS NULL OR s.movementDate >= :startDate) AND " +
           "(:endDate IS NULL OR s.movementDate <= :endDate) AND " +
           "(:status IS NULL OR LOWER(s.status) = LOWER(:status)) " +
           "ORDER BY s.movementDate DESC")
    List<StockMovement> findByFilters(
            @Param("productsSaleId") Integer productsSaleId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("status") String status
    );
}
