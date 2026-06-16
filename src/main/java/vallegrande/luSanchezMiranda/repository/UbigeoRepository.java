package vallegrande.luSanchezMiranda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vallegrande.luSanchezMiranda.model.Ubigeo;

@Repository
public interface UbigeoRepository extends JpaRepository<Ubigeo, Integer> {
}