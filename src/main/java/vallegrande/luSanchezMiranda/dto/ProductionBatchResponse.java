package vallegrande.luSanchezMiranda.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ProductionBatchResponse {
    private Integer batchId;
    private Integer orderDetailId;
    private Integer productsSaleId;
    private String productName;
    private Integer employeeId;
    private String employeeName;
    private Integer quantityInitial;
    private LocalDate startDate;
    private LocalDate estimatedReadyDate;
    private String status;
    private String observations;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
