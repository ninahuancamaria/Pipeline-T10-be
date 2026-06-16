package vallegrande.luSanchezMiranda.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "CUSTOMER")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_customer")
    private Integer idCustomer;

    @ManyToOne
    @JoinColumn(name = "ubigeo_code", nullable = false)
    private Ubigeo ubigeo;

    @NotNull(message = "El nombre no puede ser nulo")
    @Size(max = 50, message = "El nombre no puede exceder los 50 caracteres")
    @Column(name = "customer_name", length = 50, nullable = false)
    private String customerName;

    @NotNull(message = "El apellido no puede ser nulo")
    @Size(max = 60, message = "El apellido no puede exceder los 60 caracteres")
    @Column(name = "customer_lastname", length = 60, nullable = false)
    private String customerLastname;

    @NotNull(message = "El tipo de cliente no puede ser nulo")
    @Pattern(regexp = "^(Natural|Empresa)$", message = "El tipo de cliente debe ser 'Natural' o 'Empresa'")
    @Column(name = "customer_type", length = 20, nullable = false)
    private String customerType;

    @NotNull(message = "El tipo de documento no puede ser nulo")
    @Pattern(regexp = "^(DNI|RUC|CE)$", message = "El tipo de documento debe ser 'DNI', 'RUC' o 'CE'")
    @Column(name = "document_type", columnDefinition = "char(3)", nullable = false)
    private String documentType;

    @NotNull(message = "El número de documento no puede ser nulo")
    @Size(max = 15, message = "El número de documento no puede exceder los 15 caracteres")
    @Column(name = "document_number", length = 15, nullable = false, unique = true)
    private String documentNumber;

    @NotNull(message = "El correo electrónico no puede ser nulo")
    @Email(message = "El formato del correo electrónico no es válido")
    @Size(max = 150, message = "El correo no puede exceder los 150 caracteres")
    @Column(name = "email", length = 150, nullable = false, unique = true)
    private String email;

    @NotNull(message = "El teléfono no puede ser nulo")
    @Size(min = 9, max = 9, message = "El teléfono debe tener exactamente 9 dígitos")
    @Pattern(regexp = "\\d{9}", message = "El teléfono debe contener solo dígitos")
    @Column(name = "phone", columnDefinition = "char(9)", nullable = false)
    private String phone;

    @NotNull(message = "La dirección no puede ser nula")
    @Size(max = 200, message = "La dirección no puede exceder los 200 caracteres")
    @Column(name = "address", length = 200, nullable = false, columnDefinition = "nvarchar(200)")
    private String address;

    @Column(name = "status", nullable = false)
    private Boolean status = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "restored_at")
    private LocalDateTime restoredAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Lima"));
        this.createdAt = now;
        this.updatedAt = now;
        if (this.status == null) {
            this.status = true;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now(ZoneId.of("America/Lima"));
    }
}