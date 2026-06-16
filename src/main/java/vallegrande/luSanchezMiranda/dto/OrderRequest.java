package vallegrande.luSanchezMiranda.dto;

import lombok.Data;
import vallegrande.luSanchezMiranda.model.Customer;
import vallegrande.luSanchezMiranda.model.Employee;
import vallegrande.luSanchezMiranda.model.ProductSale;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderRequest {
    private LocalDate estimatedDelivery;
    private String status;
    private String notes;
    private BigDecimal totalEstimated;
    private Employee employee;
    private Customer customer;
    private List<OrderDetailRequest> details;

    @Data
    public static class OrderDetailRequest {
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal subtotal;
        private ProductSale productSale;
    }
}
