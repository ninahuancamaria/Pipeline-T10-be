package vallegrande.luSanchezMiranda.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad que representa un producto destinado a la venta.
 * Mapea la tabla PRODUCTS_SALE de la base de datos.
 */
@Entity
@Table(name = "PRODUCTS_SALE")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductSale {

    /** Identificador único del producto de venta. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "products_sale_id")
    private Integer productsSaleId;

    /** Nombre del producto de venta. Máximo 100 caracteres. No puede estar vacío. */
    @NotNull(message = "El nombre del producto no puede ser nulo")
    @NotBlank(message = "El nombre del producto no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre del producto debe tener entre 3 y 100 caracteres")
    @Column(name = "product_name", length = 100, nullable = false)
    private String productName;

    /** Precio unitario del producto. Debe ser mayor a 0 y no exceder 99999.99. */
    @NotNull(message = "El precio no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    @DecimalMax(value = "99999.99", message = "El precio no puede exceder 99999.99")
    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    /** Cantidad de unidades disponibles en stock. No puede ser negativa ni exceder 100000. */
    @NotNull(message = "El stock disponible no puede ser nulo")
    @Min(value = 0, message = "El stock disponible no puede ser negativo")
    @Max(value = 100000, message = "El stock disponible no puede exceder 100000 unidades")
    @Column(name = "available_stock", nullable = false)
    private Integer availableStock = 0;

    /** Unidad de medida del producto. Valores permitidos: UNIDAD, KG, LITRO, SACO, SOBRE, ROLLO. */
    @NotNull(message = "La unidad de medida no puede ser nula")
    @Pattern(regexp = "^(UNIDAD|KG|LITRO|SACO|SOBRE|ROLLO)$", message = "La unidad de medida debe ser una de las siguientes: UNIDAD, KG, LITRO, SACO, SOBRE, ROLLO")
    @Column(name = "unit_measurement", length = 30, nullable = false)
    private String unitMeasurement;

    /** Descripción detallada del producto. Entre 10 y 255 caracteres. No puede estar vacía. */
    @NotNull(message = "La descripción no puede ser nula")
    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(min = 10, max = 255, message = "La descripción debe tener entre 10 y 255 caracteres")
    @Column(name = "description", columnDefinition = "CHAR(255)", nullable = false)
    private String description;

    /** Fecha y hora de creación del registro. No se actualiza. */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /** Fecha y hora de la última actualización del registro. */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /** Fecha y hora de eliminación lógica del registro. Nulo si está activo. */
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    /** Fecha y hora de restauración del registro. Nulo si no fue restaurado. */
    @Column(name = "restored_at")
    private LocalDateTime restoredAt;

    /** Categoría a la que pertenece el producto de venta. No puede ser nula. */
    @NotNull(message = "La categoría del producto no puede ser nula")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (availableStock == null) {
            availableStock = 0;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

