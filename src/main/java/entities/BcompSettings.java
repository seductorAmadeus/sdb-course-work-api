package entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "bcomp_settings")
public class BcompSettings {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private BigDecimal id;

    @Column(name = "value")
    private String value;

    @Column(name = "type")
    private String type;


    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
