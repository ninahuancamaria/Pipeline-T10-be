package vallegrande.luSanchezMiranda.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class StockMovementResponse {
    private Integer movement;
    private String movementType;
    private String movementReason;
    private BigDecimal quantity;
    private BigDecimal stockBefore;
    private BigDecimal stockAfter;
    private String comments;
    private LocalDateTime movementDate;
    private LocalDateTime createdAt;
    private String status;
    private LocalDateTime deletedAt;
    private LocalDateTime restoredAt;
    private Integer employeeId;
    private String employeeName;
    private Integer saleId;
    private Integer batchId;
    private Integer productsSaleId;
    private String productSaleName;
    private Integer productId;
    private String productSupplyName;
    private Integer idPurchase;
}
