package vallegrande.luSanchezMiranda.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "UBIGEO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ubigeo {

    @Id
    @Column(name = "ubigeo_code")
    private Integer ubigeoCode;

    @Column(name = "department", length = 100, nullable = false)
    private String department;

    @Column(name = "province", length = 100, nullable = false)
    private String province;

    @Column(name = "district", length = 100, nullable = false)
    private String district;
}