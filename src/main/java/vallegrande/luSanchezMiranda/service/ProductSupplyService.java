package vallegrande.luSanchezMiranda.service;

import vallegrande.luSanchezMiranda.model.ProductSupply;
import java.util.List;

public interface ProductSupplyService {

    List<ProductSupply> listar();

    List<ProductSupply> listarActivos();

    List<ProductSupply> listarInactivos();

    ProductSupply listarPorId(Integer id);

    ProductSupply guardar(ProductSupply product);

    ProductSupply actualizar(Integer id, ProductSupply product);

    ProductSupply eliminarLogico(Integer id);

    ProductSupply restaurar(Integer id);
}
