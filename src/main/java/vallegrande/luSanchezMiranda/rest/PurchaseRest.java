package vallegrande.luSanchezMiranda.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vallegrande.luSanchezMiranda.dto.PurchaseRequest;
import vallegrande.luSanchezMiranda.dto.PurchaseResponse;
import vallegrande.luSanchezMiranda.model.*;
import vallegrande.luSanchezMiranda.service.PurchaseService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/purchase")
@Tag(name = "Purchase", description = "CRUD de Órdenes de Compra (Purchases)")
// Endpoint REST para crear, consultar y eliminar compras con sus detalles relacionados.
public class PurchaseRest {

    @Autowired
    private PurchaseService service;

    // LISTAR
    @Operation(summary = "Listar compras", description = "Obtiene la lista completa de compras registradas con todos sus detalles.")
    @GetMapping
    public List<PurchaseResponse> listar() {
        return service.listar().stream().map(this::toResponse).toList();
    }

    // LISTAR POR ID
    @Operation(summary = "Buscar compra por ID", description = "Obtiene una compra específica con todos sus detalles según su ID.")
    @GetMapping("/{id}")
    public PurchaseResponse listarPorId(@PathVariable Integer id) {
        return toResponse(service.listarPorId(id));
    }

    // LISTAR POR ESTADO
    @Operation(summary = "Listar compras por estado", description = "Obtiene la lista de compras filtrada por estado (ej. COMPLETADO, PENDIENTE, CANCELADO).")
    @GetMapping("/estado/{status}")
    public List<PurchaseResponse> listarPorEstado(@PathVariable String status) {
        return service.listarPorEstado(status).stream().map(this::toResponse).toList();
    }

    // CREAR
    @Operation(summary = "Crear nueva compra", description = "Registra una nueva orden de compra con sus detalles. Si el estado es COMPLETADO, incrementa el stock de los productos correspondientes.")
    @PostMapping
    public PurchaseResponse guardar(@Valid @RequestBody PurchaseRequest request) {
        return toResponse(service.guardar(toEntity(request)));
    }

    // EDITAR
    @Operation(summary = "Editar compra", description = "Modifica los datos y detalles de una compra existente, ajustando el stock de productos de forma consistente.")
    @PutMapping("/{id}")
    public PurchaseResponse actualizar(@PathVariable Integer id, @Valid @RequestBody PurchaseRequest request) {
        return toResponse(service.actualizar(id, toEntity(request)));
    }

    // Transforma la entidad de compra al DTO de respuesta usado por Swagger y el cliente.
    private PurchaseResponse toResponse(Purchase purchase) {
        if (purchase == null) {
            return null;
        }

        PurchaseResponse response = new PurchaseResponse();
        response.setIdPurchase(purchase.getIdPurchase());
        response.setSupplierId(purchase.getSupplier() != null ? purchase.getSupplier().getSupplierId() : null);
        response.setSupplierCompanyName(purchase.getSupplier() != null ? purchase.getSupplier().getCompanyName() : null);
        response.setEmployeeId(purchase.getEmployee() != null ? purchase.getEmployee().getEmployeeId() : null);
        response.setEmployeeName(purchase.getEmployee() != null ? purchase.getEmployee().getName() : null);
        response.setDatePurchases(purchase.getDatePurchases());
        response.setTotalAmount(purchase.getTotalAmount());
        response.setStatus(purchase.getStatus());

        if (purchase.getDetails() != null) {
            response.setDetails(purchase.getDetails().stream().map(detail -> {
                PurchaseResponse.PurchaseDetailResponse detailResponse = new PurchaseResponse.PurchaseDetailResponse();
                detailResponse.setIdPurchasesDetail(detail.getIdPurchasesDetail());
                detailResponse.setProductId(detail.getProduct() != null ? detail.getProduct().getProductId() : null);
                detailResponse.setProductName(detail.getProduct() != null ? detail.getProduct().getProductName() : null);
                detailResponse.setAmountProduct(detail.getAmountProduct());
                detailResponse.setUnitPrice(detail.getUnitPrice());
                detailResponse.setTotalCost(detail.getTotalCost());
                return detailResponse;
            }).toList());
        }

        return response;
    }

    // Convierte la solicitud recibida en la entidad de compra con sus detalles asociados.
    private Purchase toEntity(PurchaseRequest request) {
        Purchase purchase = new Purchase();

        Supplier supplier = new Supplier();
        supplier.setSupplierId(request.getSupplierId());
        purchase.setSupplier(supplier);

        Employee employee = new Employee();
        employee.setEmployeeId(request.getEmployeeId());
        purchase.setEmployee(employee);

        purchase.setStatus(request.getStatus());

        if (request.getDetails() != null) {
            for (PurchaseRequest.PurchaseDetailRequest detailRequest : request.getDetails()) {
                PurchaseDetail detail = new PurchaseDetail();
                detail.setAmountProduct(detailRequest.getAmountProduct());
                detail.setUnitPrice(detailRequest.getUnitPrice());

                ProductSupply product = new ProductSupply();
                product.setProductId(detailRequest.getProductId());
                detail.setProduct(product);
                detail.setPurchase(purchase);

                purchase.getDetails().add(detail);
            }
        }

        return purchase;
    }

    // ELIMINAR LOGICO (REGISTRO ELIMINADO)
    @Operation(summary = "Eliminar compra", description = "Marca la compra como registro eliminado. Si la compra estaba completada, se revierte el stock asociado al eliminarla.")
    @PatchMapping("/eliminar/{id}")
    public Purchase eliminar(@PathVariable Integer id) {
        return service.eliminarLogico(id);
    }
}
