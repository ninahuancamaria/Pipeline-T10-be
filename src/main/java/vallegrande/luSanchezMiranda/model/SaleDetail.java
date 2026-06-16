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

/**
 * Entidad JPA que representa una línea de detalle de una Venta.
 * Mapea la tabla SALE_DETAILS de la base de datos SQL Server.
 */
@Entity
@Table(name = "SALE_DETAILS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SaleDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sale_detail")
    private Integer idSaleDetail; // Identificador único auto-incremental del detalle de venta

    @Column(name = "unit_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal unitPrice; // Precio unitario del producto al momento de realizar la venta

    @Column(name = "product_amount", nullable = false)
    private Integer productAmount; // Cantidad de unidades vendidas del producto

    @Column(name = "subtotal_cost", precision = 10, scale = 2, nullable = false)
    private BigDecimal subtotalCost; // Subtotal de esta línea (precio unitario * cantidad)

    @ManyToOne
    @JoinColumn(name = "products_sale_id", nullable = false)
    private ProductSale productSale; // Producto vendido en esta línea de detalle

    /**
     * Venta padre a la cual pertenece este detalle.
     * Excluido de ToString y EqualsAndHashCode para evitar bucles recursivos con Lombok.
     */
    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Sale sale;
}
