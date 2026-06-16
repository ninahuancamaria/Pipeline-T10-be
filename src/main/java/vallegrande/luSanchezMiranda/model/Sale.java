package vallegrande.luSanchezMiranda.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidad JPA que representa la cabecera de una Venta.
 * Mapea la tabla SALE en la base de datos SQL Server.
 */
@Entity
@Table(name = "SALE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private Integer saleId; // Identificador único auto-incremental de la venta

    @Column(name = "sale_date", nullable = false)
    private LocalDateTime saleDate; // Fecha y hora del registro de la venta

    @Column(name = "receipt_type", length = 20, nullable = false)
    private String receiptType; // Tipo de comprobante de pago emitido (ej: BOLETA, FACTURA)

    @Column(name = "payment_method", length = 20, nullable = false)
    private String paymentMethod; // Método de pago utilizado (ej: EFECTIVO, YAPE)

    @Column(name = "status", length = 20, nullable = false)
    private String status = "completada"; // Estado de la venta ("completada" o "anulada")

    @Column(name = "total_cost", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalCost; // Costo total final de la venta

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee; // Empleado que registró la venta

    @ManyToOne
    @JoinColumn(name = "id_customer", nullable = false)
    private Customer customer; // Cliente al que se le realizó la venta

    /**
     * Relación uno-a-muchos hacia los detalles de la venta (SaleDetail).
     * cascade = CascadeType.ALL: Cualquier operación en Venta se propaga a sus detalles (guardar, borrar, etc.)
     * orphanRemoval = true: Si se quita un detalle de la lista, se borra automáticamente de la base de datos.
     * fetch = FetchType.EAGER: Carga automáticamente los detalles de venta cuando se consulta una Venta.
     * @JsonIgnoreProperties("sale"): Evita loops de serialización JSON infinita (Jackson).
     * Excluido de ToString y EqualsAndHashCode para evitar StackOverflowError recursivos de Lombok.
     */
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("sale")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<SaleDetail> details;
    
    /**
     * Callback JPA ejecutado automáticamente antes de la persistencia de una nueva entidad.
     * Inicializa la fecha de venta al momento actual y el estado predeterminado a "completada".
     */
    @PrePersist
    protected void onCreate() {
        if (saleDate == null) {
            saleDate = LocalDateTime.now();
        }
        if (status == null) {
            status = "completada";
        }
    }
}
