package vallegrande.luSanchezMiranda.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private Integer orderId;
    private LocalDateTime orderDate;
    private LocalDate estimatedDelivery;
    private String status;
    private String notes;
    private BigDecimal totalEstimated;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer employeeId;
    private String employeeName;
    private Integer idCustomer;
    private String customerName;
    private List<OrderDetailResponse> details;

    @Data
    public static class OrderDetailResponse {
        private Integer idOrderDetail;
        private Integer productsSaleId;
        private String productName;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal subtotal;
    }
}
