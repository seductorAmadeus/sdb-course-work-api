package entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is an entity that describes user's interface settings.
 *
 * @author Rayla Martin
 * @version 0.1
 * @since 0.1
 */

@Entity
@Table(name = "bcomp_settings")
public class BcompSettings {
    @Id
    @SequenceGenerator(name="bcomp_settings_seq", sequenceName="BCOMP_SETTINGS_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bcomp_settings_seq")
    @Column(name = "bcomp_settings_id")
    private BigDecimal id;

    @Column(name = "value")
    private String value;

    @Column(name = "type")
    private String type;

    @ManyToMany(mappedBy = "bcompSettings", fetch = FetchType.EAGER)
    private List<UserSession> userSessions = new ArrayList<>();

    public BcompSettings() {

    }

    public BcompSettings(String value, String type) {
        this.value = value;
        this.type = type;
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
