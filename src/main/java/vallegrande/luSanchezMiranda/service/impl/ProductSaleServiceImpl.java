package vallegrande.luSanchezMiranda.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vallegrande.luSanchezMiranda.model.Category;
import vallegrande.luSanchezMiranda.model.ProductSale;
import vallegrande.luSanchezMiranda.repository.CategoryRepository;
import vallegrande.luSanchezMiranda.repository.ProductSaleRepository;
import vallegrande.luSanchezMiranda.service.ProductSaleService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductSaleServiceImpl implements ProductSaleService {

    @Autowired
    private ProductSaleRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<ProductSale> listar() {
        return repository.findAll();
    }

    @Override
    public ProductSale listarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<ProductSale> listarActivos() {
        return repository.findByDeletedAtIsNull();
    }

    @Override
    public List<ProductSale> listarInactivos() {
        return repository.findByDeletedAtIsNotNull();
    }

    @Override
    @Transactional
    public ProductSale guardar(ProductSale product) {
        // Resolver la categoría desde la base de datos usando el categoryId recibido
        if (product.getCategory() != null && product.getCategory().getCategoryId() != null) {
            Category category = categoryRepository.findById(product.getCategory().getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Categoría no encontrada con ID: " + product.getCategory().getCategoryId()));
            product.setCategory(category);
        }
        return repository.save(product);
    }

    @Override
    @Transactional
    public ProductSale actualizar(Integer id, ProductSale product) {
        Optional<ProductSale> existente = repository.findById(id);

        if (existente.isPresent()) {
            ProductSale p = existente.get();

            p.setProductName(product.getProductName());
            p.setPrice(product.getPrice());
            p.setAvailableStock(product.getAvailableStock());
            p.setUnitMeasurement(product.getUnitMeasurement());
            p.setDescription(product.getDescription());

            // Resolver la categoría desde la base de datos
            if (product.getCategory() != null && product.getCategory().getCategoryId() != null) {
                Category category = categoryRepository.findById(product.getCategory().getCategoryId())
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Categoría no encontrada con ID: " + product.getCategory().getCategoryId()));
                p.setCategory(category);
            }

            // No copiar fechas de auditoría del request
            return repository.save(p);
        }

        return null;
    }

    @Override
    @Transactional
    public ProductSale eliminarLogico(Integer id) {
        ProductSale p = listarPorId(id);
        if (p != null) {
            p.setDeletedAt(LocalDateTime.now());
            return repository.save(p);
        }
        return null;
    }

    @Override
    @Transactional
    public ProductSale restaurar(Integer id) {
        ProductSale p = listarPorId(id);
        if (p != null) {
            p.setDeletedAt(null);
            p.setRestoredAt(LocalDateTime.now());
            return repository.save(p);
        }
        return null;
    }
}