package vallegrande.luSanchezMiranda.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class PurchaseRequest {
    private Integer supplierId;
    private Integer employeeId;
    private String status;
    private List<PurchaseDetailRequest> details;

    @Data
    public static class PurchaseDetailRequest {
        private Integer productId;
        private Integer amountProduct;
        private BigDecimal unitPrice;
    }
}
