package vallegrande.luSanchezMiranda.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vallegrande.luSanchezMiranda.model.ProductionBatch;
import vallegrande.luSanchezMiranda.repository.ProductionBatchRepository;
import vallegrande.luSanchezMiranda.service.ProductionBatchService;

import java.util.List;
import java.util.Optional;

@Service
public class ProductionBatchServiceImpl implements ProductionBatchService {

    @Autowired
    private ProductionBatchRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<ProductionBatch> listar() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public ProductionBatch listarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public ProductionBatch guardar(ProductionBatch batch) {
        batch.setBatchId(null);
        return repository.save(batch);
    }

    @Transactional
    @Override
    public ProductionBatch actualizar(Integer id, ProductionBatch batch) {
        Optional<ProductionBatch> existenteOpt = repository.findById(id);

        if (existenteOpt.isPresent()) {
            ProductionBatch existing = existenteOpt.get();
            if (batch.getOrderDetail() != null) existing.setOrderDetail(batch.getOrderDetail());
            if (batch.getProductSale() != null) existing.setProductSale(batch.getProductSale());
            if (batch.getEmployee() != null) existing.setEmployee(batch.getEmployee());
            if (batch.getQuantityInitial() != null) existing.setQuantityInitial(batch.getQuantityInitial());
            if (batch.getStartDate() != null) existing.setStartDate(batch.getStartDate());
            if (batch.getEstimatedReadyDate() != null) existing.setEstimatedReadyDate(batch.getEstimatedReadyDate());
            if (batch.getStatus() != null) existing.setStatus(batch.getStatus());
            if (batch.getObservations() != null) existing.setObservations(batch.getObservations());
            return repository.save(existing);
        }

        return null;
    }

    @Transactional
    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}
