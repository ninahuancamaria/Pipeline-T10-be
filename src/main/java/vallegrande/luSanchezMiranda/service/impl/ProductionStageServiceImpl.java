package vallegrande.luSanchezMiranda.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vallegrande.luSanchezMiranda.model.ProductionStage;
import vallegrande.luSanchezMiranda.repository.ProductionStageRepository;
import vallegrande.luSanchezMiranda.service.ProductionStageService;

import java.util.List;
import java.util.Optional;

@Service
public class ProductionStageServiceImpl implements ProductionStageService {

    @Autowired
    private ProductionStageRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<ProductionStage> listar() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public ProductionStage listarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public ProductionStage guardar(ProductionStage stage) {
        stage.setStageId(null);
        return repository.save(stage);
    }

    @Transactional
    @Override
    public ProductionStage actualizar(Integer id, ProductionStage stage) {
        Optional<ProductionStage> existenteOpt = repository.findById(id);

        if (existenteOpt.isPresent()) {
            ProductionStage existing = existenteOpt.get();
            if (stage.getStageName() != null) existing.setStageName(stage.getStageName());
            if (stage.getQuantityIn() != null) existing.setQuantityIn(stage.getQuantityIn());
            if (stage.getQuantityLost() != null) existing.setQuantityLost(stage.getQuantityLost());
            if (stage.getStartDate() != null) existing.setStartDate(stage.getStartDate());
            if (stage.getEndDate() != null) existing.setEndDate(stage.getEndDate());
            if (stage.getObservations() != null) existing.setObservations(stage.getObservations());
            if (stage.getEmployee() != null) existing.setEmployee(stage.getEmployee());
            if (stage.getProductionBatch() != null) existing.setProductionBatch(stage.getProductionBatch());
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
