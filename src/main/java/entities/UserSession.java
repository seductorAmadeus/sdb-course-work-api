package entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user_session")
public class UserSession {

    @Id
    @SequenceGenerator(name="user_session_seq", sequenceName="USER_SESSION_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_session_seq")
    @Column(name = "user_session_id")
    private BigDecimal id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private Users userID;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "sessionId", cascade = CascadeType.ALL)
    private Set<Bcomp> bcomps;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "session_settings",
            joinColumns = {@JoinColumn(name = "session_id")},
            inverseJoinColumns = {@JoinColumn(name = "setting_id")})
    private List<BcompSettings> bcompSettings = new ArrayList<BcompSettings>();

    public UserSession() {
    }

    public UserSession(Users userID, String status, Set<Bcomp> bcomps, List<BcompSettings> bcompSettings) {
        this.userID = userID;
        this.status = status;
        this.bcomps = bcomps;
        this.bcompSettings = bcompSettings;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Users getUserID() {
        return userID;
    }

    public void setUserID(Users userID) {
        this.userID = userID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Bcomp> getBcomps() {
        return bcomps;
    }

    public void setBcomps(Set<Bcomp> bcomps) {
        this.bcomps = bcomps;
    }

    public List<BcompSettings> getBcompSettings() {
        return bcompSettings;
    }

    public void setBcompSettings(List<BcompSettings> bcompSettings) {
        this.bcompSettings = bcompSettings;
    }
}
