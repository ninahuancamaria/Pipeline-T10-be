package vallegrande.luSanchezMiranda.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vallegrande.luSanchezMiranda.model.SaleDetail;
import vallegrande.luSanchezMiranda.repository.SaleDetailRepository;
import vallegrande.luSanchezMiranda.service.SaleDetailService;

import java.util.List;

/**
 * Implementación de la capa de servicios para la gestión de Detalles de Venta.
 */
@Service
public class SaleDetailServiceImpl implements SaleDetailService {

    @Autowired
    private SaleDetailRepository repository;

    /**
     * Obtiene todos los detalles de venta de la base de datos.
     */
    @Transactional(readOnly = true)
    @Override
    public List<SaleDetail> listar() {
        return repository.findAll();
    }

    /**
     * Obtiene un detalle de venta específico por su identificador primario.
     */
    @Transactional(readOnly = true)
    @Override
    public SaleDetail listarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * Guarda individualmente un nuevo detalle de venta.
     */
    @Transactional
    @Override
    public SaleDetail guardar(SaleDetail saleDetail) {
        saleDetail.setIdSaleDetail(null); // Asegura inserción
        return repository.save(saleDetail);
    }

    /**
     * Modifica los campos de un detalle de venta si existe en la base de datos.
     */
    @Transactional
    @Override
    public SaleDetail actualizar(Integer id, SaleDetail saleDetail) {
        SaleDetail existing = repository.findById(id).orElse(null);
        if (existing != null) {
            if (saleDetail.getUnitPrice() != null) existing.setUnitPrice(saleDetail.getUnitPrice());
            if (saleDetail.getProductAmount() != null) existing.setProductAmount(saleDetail.getProductAmount());
            if (saleDetail.getSubtotalCost() != null) existing.setSubtotalCost(saleDetail.getSubtotalCost());
            if (saleDetail.getProductSale() != null) existing.setProductSale(saleDetail.getProductSale());
            if (saleDetail.getSale() != null) existing.setSale(saleDetail.getSale());
            return repository.save(existing);
        }
        return null;
    }

    /**
     * Elimina físicamente el detalle de venta por su ID.
     */
    @Transactional
    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}
