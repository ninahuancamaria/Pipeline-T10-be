package vallegrande.luSanchezMiranda.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vallegrande.luSanchezMiranda.model.Sale;
import vallegrande.luSanchezMiranda.model.SaleDetail;
import vallegrande.luSanchezMiranda.repository.SaleRepository;
import vallegrande.luSanchezMiranda.service.SaleService;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de los servicios de negocio de Ventas.
 * Contiene la lógica transaccional para el almacenamiento de cabecera y detalles de venta.
 */
@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleRepository repository;

    /**
     * Recupera todas las ventas de la base de datos de manera transaccional y optimizada.
     */
    @Transactional(readOnly = true)
    @Override
    public List<Sale> listar() {
        return repository.findAll();
    }

    /**
     * Recupera una venta específica por su ID.
     */
    @Transactional(readOnly = true)
    @Override
    public Sale listarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * Guarda una venta nueva vinculando cada detalle con el objeto de venta padre.
     * Al ser una relación bidireccional, es obligatorio asignar "detail.setSale(sale)"
     * para que Hibernate establezca el campo de clave foránea "sale_id" en la BD.
     */
    @Transactional
    @Override
    public Sale guardar(Sale sale) {
        sale.setSaleId(null);
        if (sale.getDetails() != null) {
            for (SaleDetail detail : sale.getDetails()) {
                detail.setIdSaleDetail(null); // Asegura que el detalle sea insertado como nuevo
                detail.setSale(sale);         // Vincula el detalle a esta venta
            }
        }
        return repository.save(sale);
    }

    /**
     * Actualiza los datos de la venta y reemplaza/actualiza sus detalles.
     * Se limpian los detalles anteriores gracias a "orphanRemoval = true",
     * y se insertan los nuevos asociados a la venta existente.
     */
    @Transactional
    @Override
    public Sale actualizar(Integer id, Sale sale) {
        Sale existing = repository.findById(id).orElse(null);
        if (existing != null) {
            // Actualizar campos de cabecera si vienen en la petición
            if (sale.getSaleDate() != null) existing.setSaleDate(sale.getSaleDate());
            if (sale.getReceiptType() != null) existing.setReceiptType(sale.getReceiptType());
            if (sale.getPaymentMethod() != null) existing.setPaymentMethod(sale.getPaymentMethod());
            if (sale.getStatus() != null) existing.setStatus(sale.getStatus());
            if (sale.getTotalCost() != null) existing.setTotalCost(sale.getTotalCost());
            if (sale.getEmployee() != null) existing.setEmployee(sale.getEmployee());
            if (sale.getCustomer() != null) existing.setCustomer(sale.getCustomer());
            
            // Reemplazar la colección de detalles asociados
            if (sale.getDetails() != null) {
                if (existing.getDetails() == null) {
                    existing.setDetails(new ArrayList<>());
                } else {
                    existing.getDetails().clear(); // Elmina los detalles viejos en BD (orphanRemoval)
                }
                for (SaleDetail detail : sale.getDetails()) {
                    detail.setIdSaleDetail(null);
                    detail.setSale(existing);      // Vincula al registro existente
                    existing.getDetails().add(detail);
                }
            }
            return repository.save(existing);
        }
        return null;
    }

    /**
     * Eliminación lógica (anulación) de la venta.
     * En lugar de borrar físicamente el registro (lo cual causaría fallos de clave foránea
     * en tablas como movimientos de stock), marcamos su estado como "anulada".
     */
    @Transactional
    @Override
    public Sale eliminar(Integer id) {
        Sale existing = repository.findById(id).orElse(null);
        if (existing != null) {
            existing.setStatus("anulada");
            return repository.save(existing);
        }
        return null;
    }
}
