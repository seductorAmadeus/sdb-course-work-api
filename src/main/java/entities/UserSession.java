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
    @GeneratedValue
    @Column(name = "id")
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
}
