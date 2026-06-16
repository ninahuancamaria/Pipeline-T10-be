package vallegrande.luSanchezMiranda.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "SUPPLIER")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private Integer supplierId;

    @NotNull(message = "La razón social no puede ser nula")
    @Size(max = 150, message = "La razón social no puede exceder los 150 caracteres")
    @Column(name = "company_name", length = 150, nullable = false)
    private String companyName;

    @NotNull(message = "El RUC no puede ser nulo")
    @Size(min = 11, max = 11, message = "El RUC debe tener exactamente 11 dígitos")
    @Pattern(regexp = "\\d{11}", message = "El RUC debe contener solo dígitos")
    @Column(name = "ruc", length = 11, nullable = false)
    private String ruc;

    @NotNull(message = "El teléfono no puede ser nulo")
    @Min(value = 1, message = "El teléfono debe ser un número positivo")
    @Column(name = "phone", nullable = false)
    private Long phone;

    @NotNull(message = "El correo electrónico no puede ser nulo")
    @Email(message = "El formato del correo electrónico no es válido")
    @Size(max = 100, message = "El correo electrónico no puede exceder los 100 caracteres")
    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @NotNull(message = "La dirección no puede ser nula")
    @Size(max = 200, message = "La dirección no puede exceder los 200 caracteres")
    @Column(name = "address", length = 200, nullable = false)
    private String address;

    @NotNull(message = "El estado no puede ser nulo")
    @Column(name = "status", nullable = false)
    private Boolean status = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "restored_at")
    private LocalDateTime restoredAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ubigeo_code", nullable = false)
    private Ubigeo ubigeo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) {
            status = true;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}