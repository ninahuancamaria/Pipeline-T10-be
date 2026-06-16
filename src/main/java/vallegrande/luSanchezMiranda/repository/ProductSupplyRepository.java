package vallegrande.luSanchezMiranda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vallegrande.luSanchezMiranda.model.ProductSupply;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductSupplyRepository extends JpaRepository<ProductSupply, Integer> {

    @Query("SELECT p FROM ProductSupply p JOIN FETCH p.category")
    List<ProductSupply> findAllWithCategory();

    @Query("SELECT p FROM ProductSupply p JOIN FETCH p.category WHERE p.deletedAt IS NULL")
    List<ProductSupply> findByDeletedAtIsNullWithCategory();

    @Query("SELECT p FROM ProductSupply p JOIN FETCH p.category WHERE p.deletedAt IS NOT NULL")
    List<ProductSupply> findByDeletedAtIsNotNullWithCategory();

    @Query("SELECT p FROM ProductSupply p JOIN FETCH p.category WHERE p.productId = :id")
    Optional<ProductSupply> findByIdWithCategory(@Param("id") Integer id);

    List<ProductSupply> findByProductNameContainingIgnoreCase(String productName);
}
