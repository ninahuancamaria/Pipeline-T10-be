package vallegrande.luSanchezMiranda.service;

import vallegrande.luSanchezMiranda.model.ProductionBatch;
import java.util.List;

public interface ProductionBatchService {
    List<ProductionBatch> listar();
    ProductionBatch listarPorId(Integer id);
    ProductionBatch guardar(ProductionBatch batch);
    ProductionBatch actualizar(Integer id, ProductionBatch batch);
    void eliminar(Integer id);
}
