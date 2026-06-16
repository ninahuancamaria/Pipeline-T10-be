package vallegrande.luSanchezMiranda.dto;

import lombok.Data;
import vallegrande.luSanchezMiranda.model.Sale;
import vallegrande.luSanchezMiranda.model.SaleDetail;
import vallegrande.luSanchezMiranda.model.Employee;
import vallegrande.luSanchezMiranda.model.Customer;
import vallegrande.luSanchezMiranda.model.ProductSale;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Objeto de Transferencia de Datos (DTO) que define la estructura simplificada del JSON
 * requerida para registrar o actualizar una venta con sus detalles en Swagger.
 */
@Data
public class SaleRequest {
    private String receiptType;      // Tipo de comprobante (BOLETA, FACTURA, etc.)
    private String paymentMethod;    // Método de pago (YAPE, EFECTIVO, tarjeta, etc.)
    private String status;           // Estado de la venta (completada, pendiente, anulada)
    private BigDecimal totalCost;    // Costo total acumulado de la venta
    private EmployeeIdRequest employee; // Información mínima del empleado
    private CustomerIdRequest customer; // Información mínima del cliente
    private List<SaleDetailRequest> details; // Listado de detalles de la venta

    /**
     * DTO interno para recibir solo el ID del Empleado.
     */
    @Data
    public static class EmployeeIdRequest {
        private Integer employeeId;
    }

    /**
     * DTO interno para recibir solo el ID del Cliente.
     */
    @Data
    public static class CustomerIdRequest {
        private Integer idCustomer;
    }

    /**
     * DTO interno que representa cada detalle individual de la venta.
     */
    @Data
    public static class SaleDetailRequest {
        private BigDecimal unitPrice;    // Precio unitario cobrado por el producto
        private Integer productAmount;   // Cantidad vendida del producto
        private BigDecimal subtotalCost; // Subtotal calculado para esta línea
        private ProductSaleIdRequest productSale; // Información del producto vendido
    }

    /**
     * DTO interno para recibir solo el ID del Producto.
     */
    @Data
    public static class ProductSaleIdRequest {
        private Integer productsSaleId;
    }

    /**
     * Convierte el DTO simplificado SaleRequest a la estructura de entidad JPA Sale
     * con sus respectivas relaciones instanciadas y enlazadas bidireccionalmente.
     */
    public Sale toEntity() {
        Sale sale = new Sale();
        sale.setReceiptType(this.receiptType);
        sale.setPaymentMethod(this.paymentMethod);
        sale.setTotalCost(this.totalCost);
        if (this.status != null) {
            sale.setStatus(this.status);
        }

        // Mapea el empleado creando una instancia dummy con solo su clave primaria
        if (this.employee != null && this.employee.getEmployeeId() != null) {
            Employee emp = new Employee();
            emp.setEmployeeId(this.employee.getEmployeeId());
            sale.setEmployee(emp);
        }

        // Mapea el cliente creando una instancia dummy con solo su clave primaria
        if (this.customer != null && this.customer.getIdCustomer() != null) {
            Customer cust = new Customer();
            cust.setIdCustomer(this.customer.getIdCustomer());
            sale.setCustomer(cust);
        }

        // Mapea y enlaza la lista de detalles
        if (this.details != null) {
            List<SaleDetail> list = new ArrayList<>();
            for (SaleDetailRequest reqDetail : this.details) {
                SaleDetail detail = new SaleDetail();
                detail.setUnitPrice(reqDetail.getUnitPrice());
                detail.setProductAmount(reqDetail.getProductAmount());
                detail.setSubtotalCost(reqDetail.getSubtotalCost());
                
                // Mapea el producto del detalle con solo su ID
                if (reqDetail.getProductSale() != null && reqDetail.getProductSale().getProductsSaleId() != null) {
                    ProductSale prod = new ProductSale();
                    prod.setProductsSaleId(reqDetail.getProductSale().getProductsSaleId());
                    detail.setProductSale(prod);
                }
                
                detail.setSale(sale); // Asigna la relación bidireccional hacia la cabecera
                list.add(detail);
            }
            sale.setDetails(list);
        }
        return sale;
    }
}
