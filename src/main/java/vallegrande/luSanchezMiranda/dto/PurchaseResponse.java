package vallegrande.luSanchezMiranda.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PurchaseResponse {
    private Integer idPurchase;
    private Integer supplierId;
    private String supplierCompanyName;
    private Integer employeeId;
    private String employeeName;
    private LocalDateTime datePurchases;
    private BigDecimal totalAmount;
    private String status;
    private List<PurchaseDetailResponse> details;

    @Data
    public static class PurchaseDetailResponse {
        private Integer idPurchasesDetail;
        private Integer productId;
        private String productName;
        private Integer amountProduct;
        private BigDecimal unitPrice;
        private BigDecimal totalCost;
    }
}
