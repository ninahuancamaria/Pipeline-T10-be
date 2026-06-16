package vallegrande.luSanchezMiranda.service;

import vallegrande.luSanchezMiranda.model.ProductSale;

import java.util.List;

public interface ProductSaleService {

    List<ProductSale> listar();
    ProductSale listarPorId(Integer id);
    List<ProductSale> listarActivos();
    List<ProductSale> listarInactivos();

    ProductSale guardar(ProductSale product);
    ProductSale actualizar(Integer id, ProductSale product);

    ProductSale eliminarLogico(Integer id);
    ProductSale restaurar(Integer id);
}