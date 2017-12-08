package entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "registration_codes")
public class RegistrationCodes {
    @Id
    @GeneratedValue
    @Column(name = "inviteCode")
    private BigDecimal inviteCode;

    @Column(name = "invite_code_status")
    private String inviteCodeStatus;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "inviteCode")
    private Set<Users> user;

    public BigDecimal getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(BigDecimal inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getInviteCodeStatus() {
        return inviteCodeStatus;
    }

    public void setInviteCodeStatus(String inviteCodeStatus) {
        this.inviteCodeStatus = inviteCodeStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * TODO: ?
     */
    public Set<Users> getUser() {
        return user;
    }

    public void setUser(Set<Users> user) {
        this.user = user;
    }
}
