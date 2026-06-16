package vallegrande.luSanchezMiranda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vallegrande.luSanchezMiranda.model.ProductionStage;

public interface ProductionStageRepository extends JpaRepository<ProductionStage, Integer> {
}
