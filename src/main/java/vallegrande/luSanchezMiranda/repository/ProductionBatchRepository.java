package vallegrande.luSanchezMiranda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vallegrande.luSanchezMiranda.model.ProductionBatch;

public interface ProductionBatchRepository extends JpaRepository<ProductionBatch, Integer> {
}
