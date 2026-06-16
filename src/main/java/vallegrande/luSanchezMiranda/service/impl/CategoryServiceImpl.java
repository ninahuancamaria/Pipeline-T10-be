package vallegrande.luSanchezMiranda.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vallegrande.luSanchezMiranda.model.Category;
import vallegrande.luSanchezMiranda.repository.CategoryRepository;
import vallegrande.luSanchezMiranda.service.CategoryService;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Override
    public List<Category> listar() {
        return repository.findAll();
    }

    @Override
    public Category listarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Category guardar(Category category) {
        return repository.save(category);
    }

    @Override
    public Category actualizar(Integer id, Category category) {
        Optional<Category> existente = repository.findById(id);

        if (existente.isPresent()) {
            Category c = existente.get();
            c.setCategoryName(category.getCategoryName());
            c.setCategoryType(category.getCategoryType());
            c.setStatus(category.getStatus());
            return repository.save(c);
        }

        return null;
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}
