package vallegrande.luSanchezMiranda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vallegrande.luSanchezMiranda.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
