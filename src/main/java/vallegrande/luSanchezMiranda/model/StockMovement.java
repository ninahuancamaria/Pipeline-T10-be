package vallegrande.luSanchezMiranda.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad que representa un movimiento de stock (entrada o salida).
 * Mapea la tabla STOCK_MOVEMENT de la base de datos.
 * Es una entidad transaccional que referencia múltiples entidades maestras.
 */
@Entity
@Table(name = "STOCK_MOVEMENT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StockMovement {

    /** Identificador único del movimiento de stock. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movement")
    private Integer movement;

    /** Tipo de movimiento: ENTRADA o SALIDA. */
    @Column(name = "movement_type", length = 20, nullable = false)
    private String movementType;

    /** Motivo o razón del movimiento de stock. */
    @Column(name = "movement_reason", length = 100, nullable = false)
    private String movementReason;

    /** Cantidad de unidades involucradas en el movimiento. */
    @Column(name = "quantity", precision = 10, scale = 2, nullable = false)
    private BigDecimal quantity;

    /** Stock disponible antes del movimiento. */
    @Column(name = "stock_before", precision = 10, scale = 2, nullable = false)
    private BigDecimal stockBefore;

    /** Stock disponible después del movimiento. */
    @Column(name = "stock_after", precision = 10, scale = 2, nullable = false)
    private BigDecimal stockAfter;

    /** Comentarios u observaciones adicionales sobre el movimiento. */
    @Column(name = "comments", length = 500, nullable = false)
    private String comments;

    /** Fecha y hora en que se realizó el movimiento. */
    @Column(name = "movement_date", nullable = false)
    private LocalDateTime movementDate;

    /** Fecha y hora de creación del registro. No se actualiza. */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /** Estado del movimiento. Valor por defecto: ACTIVO. */
    @Column(name = "status", length = 20, nullable = false)
    private String status = "ACTIVO";

    /** Fecha y hora de eliminación lógica. Nulo si está activo. */
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    /** Fecha y hora de restauración. Nulo si no fue restaurado. */
    @Column(name = "restored_at")
    private LocalDateTime restoredAt;

    /** Empleado responsable del movimiento. Campo opcional. */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", nullable = true)
    private Employee employee;

    /** Venta asociada al movimiento. Campo opcional. */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sale_id", nullable = true)
    private Sale sale;

    /** Lote de producción asociado al movimiento. Campo opcional. */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "batch_id", nullable = true)
    private ProductionBatch productionBatch;

    /** Producto de venta involucrado en el movimiento. Campo opcional. */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "products_sale_id", nullable = true)
    private ProductSale productSale;

    /** Producto de insumo involucrado en el movimiento. Campo opcional. */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = true)
    private ProductSupply productSupply;

    /** Compra asociada al movimiento. Campo opcional. */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_purchase", nullable = true)
    private Purchase purchase;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (movementDate == null) {
            movementDate = LocalDateTime.now();
        }
        if (status == null) {
            status = "ACTIVO";
        }
    }
}
