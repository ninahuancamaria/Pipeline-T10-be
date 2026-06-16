package vallegrande.luSanchezMiranda.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class StockMovementRequest {
    private String movementType;
    private String movementReason;
    private BigDecimal quantity;
    private BigDecimal stockBefore;
    private BigDecimal stockAfter;
    private String comments;
    private LocalDateTime movementDate;
    private String status;
    private Integer employeeId;
    private Integer saleId;
    private Integer batchId;
    private Integer productsSaleId;
    private Integer productId;
    private Integer idPurchase;
}
