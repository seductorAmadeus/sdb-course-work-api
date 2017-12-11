package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "registration_codes")
public class RegistrationCodes implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "inviteCode")
    private BigDecimal inviteCode;

    @Column(name = "invite_code_status")
    private String inviteCodeStatus;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "inviteCode", fetch = FetchType.EAGER)
    private Set<Users> users;

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
    public Set<Users> getUsers() {
        return users;
    }

    public void setUsers(Set<Users> user) {
        this.users = user;
    }
}
