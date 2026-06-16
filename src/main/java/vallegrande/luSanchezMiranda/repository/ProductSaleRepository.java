package vallegrande.luSanchezMiranda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vallegrande.luSanchezMiranda.model.ProductSale;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductSaleRepository extends JpaRepository<ProductSale, Integer> {
    
    List<ProductSale> findByProductNameContainingIgnoreCase(String productName);
    
    List<ProductSale> findByCategory_CategoryId(Integer categoryId);
    
    List<ProductSale> findByDeletedAtIsNull();
    
    List<ProductSale> findByDeletedAtIsNotNull();
    
    Optional<ProductSale> findByProductName(String productName);
}
