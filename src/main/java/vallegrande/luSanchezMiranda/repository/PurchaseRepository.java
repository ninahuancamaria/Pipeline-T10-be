package vallegrande.luSanchezMiranda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vallegrande.luSanchezMiranda.model.Purchase;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

    @Query("SELECT DISTINCT p FROM Purchase p " +
           "LEFT JOIN FETCH p.supplier s " +
           "LEFT JOIN FETCH s.ubigeo " +
           "LEFT JOIN FETCH s.category " +
           "LEFT JOIN FETCH p.employee e " +
           "LEFT JOIN FETCH e.ubigeo " +
           "LEFT JOIN FETCH e.role " +
           "LEFT JOIN FETCH p.details d " +
           "LEFT JOIN FETCH d.product pr " +
           "LEFT JOIN FETCH pr.category")
    List<Purchase> findAllWithDetails();

    @Query("SELECT p FROM Purchase p " +
           "LEFT JOIN FETCH p.supplier s " +
           "LEFT JOIN FETCH s.ubigeo " +
           "LEFT JOIN FETCH s.category " +
           "LEFT JOIN FETCH p.employee e " +
           "LEFT JOIN FETCH e.ubigeo " +
           "LEFT JOIN FETCH e.role " +
           "LEFT JOIN FETCH p.details d " +
           "LEFT JOIN FETCH d.product pr " +
           "LEFT JOIN FETCH pr.category " +
           "WHERE p.idPurchase = :id")
    Optional<Purchase> findByIdWithDetails(@Param("id") Integer id);

    @Query("SELECT DISTINCT p FROM Purchase p " +
           "LEFT JOIN FETCH p.supplier s " +
           "LEFT JOIN FETCH s.ubigeo " +
           "LEFT JOIN FETCH s.category " +
           "LEFT JOIN FETCH p.employee e " +
           "LEFT JOIN FETCH e.ubigeo " +
           "LEFT JOIN FETCH e.role " +
           "LEFT JOIN FETCH p.details d " +
           "LEFT JOIN FETCH d.product pr " +
           "LEFT JOIN FETCH pr.category " +
           "WHERE p.status = :status")
    List<Purchase> findByStatusWithDetails(@Param("status") String status);
}
