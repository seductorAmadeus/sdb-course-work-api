package entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bcomp_settings")
public class BcompSettings {
    @Id
    @GeneratedValue
    @Column(name = "bcomp_settings_id")
    private BigDecimal id;

    @Column(name = "value")
    private String value;

    @Column(name = "type")
    private String type;

    @ManyToMany(mappedBy = "bcompSettings")
    private List<UserSession> userSessions = new ArrayList<>();

    public BcompSettings() {

    }

    public BcompSettings(String value, String type, List<UserSession> userSessions) {
        this.value = value;
        this.type = type;
        this.userSessions = userSessions;
    }

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

    public List<UserSession> getUserSessions() {
        return userSessions;
    }

    public void setUserSessions(List<UserSession> userSessions) {
        this.userSessions = userSessions;
    }
}
