package vallegrande.luSanchezMiranda.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vallegrande.luSanchezMiranda.model.*;
import vallegrande.luSanchezMiranda.repository.*;
import vallegrande.luSanchezMiranda.service.PurchaseService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
// Lógica de negocio de compras: validación, cálculo de totales y control de stock.
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseRepository repository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProductSupplyRepository productSupplyRepository;

    @Override
    public List<Purchase> listar() {
        return repository.findAllWithDetails();
    }

    @Override
    public Purchase listarPorId(Integer id) {
        return repository.findByIdWithDetails(id).orElse(null);
    }

    @Override
    public List<Purchase> listarPorEstado(String status) {
        return repository.findByStatusWithDetails(status);
    }

    // Crea una compra, valida proveedores, empleados y detalles, y actualiza stock si el estado es COMPLETADO.
    @Override
    @Transactional
    public Purchase guardar(Purchase purchase) {
        Objects.requireNonNull(purchase, "La compra no puede ser nula");

        if (purchase.getSupplier() == null || purchase.getSupplier().getSupplierId() == null) {
            throw new IllegalArgumentException("Debe indicar un proveedor válido");
        }
        if (purchase.getEmployee() == null || purchase.getEmployee().getEmployeeId() == null) {
            throw new IllegalArgumentException("Debe indicar un empleado válido");
        }
        if (purchase.getDetails() == null || purchase.getDetails().isEmpty()) {
            throw new IllegalArgumentException("La compra debe tener al menos un detalle");
        }

        Supplier supplier = supplierRepository.findById(purchase.getSupplier().getSupplierId())
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado"));
        Employee employee = employeeRepository.findById(purchase.getEmployee().getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));

        purchase.setSupplier(supplier);
        purchase.setEmployee(employee);

        BigDecimal totalAmount = BigDecimal.ZERO;

        if (purchase.getDetails() != null) {
            for (PurchaseDetail detail : purchase.getDetails()) {
                if (detail == null || detail.getProduct() == null || detail.getProduct().getProductId() == null) {
                    throw new IllegalArgumentException("Cada detalle debe incluir un producto válido");
                }
                if (detail.getAmountProduct() == null || detail.getAmountProduct() <= 0) {
                    throw new IllegalArgumentException("La cantidad del detalle debe ser mayor a cero");
                }
                if (detail.getUnitPrice() == null || detail.getUnitPrice().compareTo(BigDecimal.ZERO) < 0) {
                    throw new IllegalArgumentException("El precio unitario del detalle no es válido");
                }

                detail.setPurchase(purchase);
                ProductSupply product = productSupplyRepository.findById(detail.getProduct().getProductId())
                        .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
                detail.setProduct(product);

                BigDecimal cost = detail.getUnitPrice().multiply(BigDecimal.valueOf(detail.getAmountProduct()));
                detail.setTotalCost(cost);
                totalAmount = totalAmount.add(cost);

                if ("COMPLETADO".equalsIgnoreCase(purchase.getStatus())) {
                    BigDecimal newStock = product.getAvailableStock().add(BigDecimal.valueOf(detail.getAmountProduct()));
                    product.setAvailableStock(newStock);
                    productSupplyRepository.save(product);
                }
            }
        }
        purchase.setTotalAmount(totalAmount);

        Purchase saved = repository.save(purchase);
        return repository.findByIdWithDetails(saved.getIdPurchase()).orElse(saved);
    }

    // Actualiza una compra existente y ajusta el stock según el estado anterior y el nuevo estado.
    @Override
    @Transactional
    public Purchase actualizar(Integer id, Purchase purchase) {
        Optional<Purchase> existenteOpt = repository.findByIdWithDetails(id);
        if (existenteOpt.isPresent()) {
            Purchase existente = existenteOpt.get();

            // Si el estado anterior era COMPLETADO, revertimos temporalmente el stock del detalle viejo
            if ("COMPLETADO".equalsIgnoreCase(existente.getStatus())) {
                for (PurchaseDetail detail : existente.getDetails()) {
                    ProductSupply product = detail.getProduct();
                    if (product != null) {
                        BigDecimal revertedStock = product.getAvailableStock().subtract(BigDecimal.valueOf(detail.getAmountProduct()));
                        product.setAvailableStock(revertedStock);
                        productSupplyRepository.save(product);
                    }
                }
            }

            // Actualizar datos básicos
            if (purchase.getSupplier() != null && purchase.getSupplier().getSupplierId() != null) {
                Supplier supplier = supplierRepository.findById(purchase.getSupplier().getSupplierId())
                        .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado"));
                existente.setSupplier(supplier);
            }
            if (purchase.getEmployee() != null && purchase.getEmployee().getEmployeeId() != null) {
                Employee employee = employeeRepository.findById(purchase.getEmployee().getEmployeeId())
                        .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));
                existente.setEmployee(employee);
            }
            if (purchase.getDatePurchases() != null) {
                existente.setDatePurchases(purchase.getDatePurchases());
            }
            if (purchase.getStatus() != null) {
                existente.setStatus(purchase.getStatus());
            }

            // Actualizar detalles si se envían en el cuerpo
            if (purchase.getDetails() != null) {
                // Limpiar detalles viejos
                existente.getDetails().clear();

                BigDecimal totalAmount = BigDecimal.ZERO;
                for (PurchaseDetail detail : purchase.getDetails()) {
                    if (detail == null || detail.getProduct() == null || detail.getProduct().getProductId() == null) {
                        throw new IllegalArgumentException("Cada detalle debe incluir un producto válido");
                    }
                    if (detail.getAmountProduct() == null || detail.getAmountProduct() <= 0) {
                        throw new IllegalArgumentException("La cantidad del detalle debe ser mayor a cero");
                    }
                    if (detail.getUnitPrice() == null || detail.getUnitPrice().compareTo(BigDecimal.ZERO) < 0) {
                        throw new IllegalArgumentException("El precio unitario del detalle no es válido");
                    }

                    detail.setPurchase(existente);
                    ProductSupply product = productSupplyRepository.findById(detail.getProduct().getProductId())
                            .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
                    detail.setProduct(product);

                    BigDecimal cost = detail.getUnitPrice().multiply(BigDecimal.valueOf(detail.getAmountProduct()));
                    detail.setTotalCost(cost);
                    totalAmount = totalAmount.add(cost);

                    existente.getDetails().add(detail);
                }
                existente.setTotalAmount(totalAmount);
            } else {
                // Si no se envían nuevos detalles, recalculamos sobre los existentes
                BigDecimal totalAmount = BigDecimal.ZERO;
                for (PurchaseDetail detail : existente.getDetails()) {
                    BigDecimal cost = detail.getUnitPrice().multiply(BigDecimal.valueOf(detail.getAmountProduct()));
                    detail.setTotalCost(cost);
                    totalAmount = totalAmount.add(cost);
                }
                existente.setTotalAmount(totalAmount);
            }

            // Si el NUEVO estado es COMPLETADO, aplicamos el incremento de stock
            if ("COMPLETADO".equalsIgnoreCase(existente.getStatus())) {
                for (PurchaseDetail detail : existente.getDetails()) {
                    ProductSupply product = detail.getProduct();
                    if (product != null) {
                        BigDecimal newStock = product.getAvailableStock().add(BigDecimal.valueOf(detail.getAmountProduct()));
                        product.setAvailableStock(newStock);
                        productSupplyRepository.save(product);
                    }
                }
            }

            Purchase saved = repository.save(existente);
            return repository.findByIdWithDetails(saved.getIdPurchase()).orElse(saved);
        }
        return null;
    }

    // Elimina la compra de forma lógica y revierte el stock si la compra estaba completada.
    @Override
    @Transactional
    public Purchase eliminarLogico(Integer id) {
        Purchase p = repository.findByIdWithDetails(id).orElse(null);
        if (p != null) {
            // Si el estado era COMPLETADO, debemos revertir el incremento del stock
            if ("COMPLETADO".equalsIgnoreCase(p.getStatus())) {
                for (PurchaseDetail detail : p.getDetails()) {
                    ProductSupply product = detail.getProduct();
                    if (product != null) {
                        BigDecimal newStock = product.getAvailableStock().subtract(BigDecimal.valueOf(detail.getAmountProduct()));
                        product.setAvailableStock(newStock);
                        productSupplyRepository.save(product);
                    }
                }
            }
            // Cambiar el estado a REGISTRO ELIMINADO como eliminación lógica
            p.setStatus("REGISTRO ELIMINADO");
            Purchase saved = repository.save(p);
            return repository.findByIdWithDetails(saved.getIdPurchase()).orElse(saved);
        }
        return null;
    }
}
