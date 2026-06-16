package vallegrande.luSanchezMiranda.service;

import vallegrande.luSanchezMiranda.model.ProductionStage;
import java.util.List;

public interface ProductionStageService {
    List<ProductionStage> listar();
    ProductionStage listarPorId(Integer id);
    ProductionStage guardar(ProductionStage stage);
    ProductionStage actualizar(Integer id, ProductionStage stage);
    void eliminar(Integer id);
}
