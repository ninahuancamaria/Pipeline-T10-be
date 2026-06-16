package vallegrande.luSanchezMiranda.service;

import vallegrande.luSanchezMiranda.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> listar();
    Category listarPorId(Integer id);
    Category guardar(Category category);
    Category actualizar(Integer id, Category category);
    void eliminar(Integer id);
}
