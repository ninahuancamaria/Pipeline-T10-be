package vallegrande.luSanchezMiranda.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SaleResponse {
    private Integer saleId;
    private LocalDateTime saleDate;
    private String receiptType;
    private String paymentMethod;
    private String status;
    private BigDecimal totalCost;
    private Integer employeeId;
    private String employeeName;
    private Integer idCustomer;
    private String customerName;
    private List<SaleDetailResponse> details;

    @Data
    public static class SaleDetailResponse {
        private Integer idSaleDetail;
        private Integer productsSaleId;
        private String productName;
        private Integer productAmount;
        private BigDecimal unitPrice;
        private BigDecimal subtotalCost;
    }
}
