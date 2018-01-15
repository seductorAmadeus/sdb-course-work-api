package entities;

import validation.constraints.CheckConstraintsIn;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This class is an entity that describes user's session in the system
 *
 * @author Rayla Martin
 * @version 0.1
 * @since 0.1
 */
@Entity
@Table(name = "user_session")
public class UserSession {

    @Id
    @SequenceGenerator(name = "user_session_seq", sequenceName = "USER_SESSION_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_session_seq")
    @Column(name = "user_session_id")
    @Digits(integer = 20, fraction = 0)
    @NotNull
    private BigDecimal id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    @NotNull
    private Users userId;

    @Column(name = "status")
    @NotNull
    @CheckConstraintsIn(constraints = {"active", "inactive"})
    private String status;

    @OneToMany(mappedBy = "userSession", cascade = CascadeType.ALL)
    private Set<Bcomp> bcomps;

    // TODO: fix it?
    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinTable(name = "session_settings",
            joinColumns = {@JoinColumn(name = "session_id")},
            inverseJoinColumns = {@JoinColumn(name = "setting_id")})
    private List<BcompSettings> bcompSettings = new ArrayList<>();

    public UserSession() {
    }

    public UserSession(Users userId, String status, Set<Bcomp> bcomps) {
        this.userId = userId;
        this.status = status;
        this.bcomps = bcomps;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userID) {
        this.userId = userID;
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

    @Override
    public String toString() {
        return new StringBuilder()
                .append(getId()).append(" ")
                .append(getUserId().getUserId()).append(" ")
                .append(getStatus()).append(" ")
                .toString();
    }
}
