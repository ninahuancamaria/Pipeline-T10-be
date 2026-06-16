package vallegrande.luSanchezMiranda.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vallegrande.luSanchezMiranda.model.Category;
import vallegrande.luSanchezMiranda.model.ProductSupply;
import vallegrande.luSanchezMiranda.repository.CategoryRepository;
import vallegrande.luSanchezMiranda.repository.ProductSupplyRepository;
import vallegrande.luSanchezMiranda.service.ProductSupplyService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductSupplyServiceImpl implements ProductSupplyService {

    @Autowired
    private ProductSupplyRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<ProductSupply> listar() {
        return repository.findAllWithCategory();
    }

    @Override
    public List<ProductSupply> listarActivos() {
        return repository.findByDeletedAtIsNullWithCategory();
    }

    @Override
    public List<ProductSupply> listarInactivos() {
        return repository.findByDeletedAtIsNotNullWithCategory();
    }

    @Override
    public ProductSupply listarPorId(Integer id) {
        return repository.findByIdWithCategory(id).orElse(null);
    }

    @Override
    public ProductSupply guardar(ProductSupply product) {
        if (product.getCategory() != null) {
            Category category = categoryRepository.findById(product.getCategory().getCategoryId()).orElse(null);
            product.setCategory(category);
        }
        product.setDeletedAt(null);
        product.setRestoredAt(null);
        ProductSupply saved = repository.save(product);
        return repository.findByIdWithCategory(saved.getProductId()).orElse(saved);
    }

    @Override
    public ProductSupply actualizar(Integer id, ProductSupply product) {
        Optional<ProductSupply> existente = repository.findById(id);
        if (existente.isPresent()) {
            ProductSupply p = existente.get();

            if (product.getCategory() != null) {
                Category category = categoryRepository.findById(product.getCategory().getCategoryId()).orElse(null);
                p.setCategory(category);
            }
            p.setProductName(product.getProductName());
            p.setDescription(product.getDescription());
            p.setPrice(product.getPrice());
            p.setAvailableStock(product.getAvailableStock());
            p.setExpiryDate(product.getExpiryDate());
            p.setUnitMeasurement(product.getUnitMeasurement());
            p.setProductImage(product.getProductImage());

            ProductSupply saved = repository.save(p);
            return repository.findByIdWithCategory(saved.getProductId()).orElse(saved);
        }
        return null;
    }

    @Override
    public ProductSupply eliminarLogico(Integer id) {
        ProductSupply p = repository.findById(id).orElse(null);
        if (p != null) {
            p.setDeletedAt(LocalDateTime.now());
            ProductSupply saved = repository.save(p);
            return repository.findByIdWithCategory(saved.getProductId()).orElse(saved);
        }
        return null;
    }

    @Override
    public ProductSupply restaurar(Integer id) {
        ProductSupply p = repository.findById(id).orElse(null);
        if (p != null) {
            p.setDeletedAt(null);
            p.setRestoredAt(LocalDateTime.now());
            ProductSupply saved = repository.save(p);
            return repository.findByIdWithCategory(saved.getProductId()).orElse(saved);
        }
        return null;
    }
}
