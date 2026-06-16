package vallegrande.luSanchezMiranda.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ProductionBatchRequest {
    private Integer orderDetailId;
    private Integer productsSaleId;
    private Integer employeeId;
    private Integer quantityInitial;
    private LocalDate startDate;
    private LocalDate estimatedReadyDate;
    private String status;
    private String observations;
}
