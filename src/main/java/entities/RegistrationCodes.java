package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "registration_codes")
public class RegistrationCodes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "invite_code")
    private BigDecimal inviteCode;

    @Column(name = "invite_code_status")
    private String inviteCodeStatus;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "inviteCode")
    private Set<Users> users;

    public RegistrationCodes() {
    }

    public RegistrationCodes(String inviteCodeStatus, String email) {
        this.inviteCodeStatus = inviteCodeStatus;
        this.email = email;
    }

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

    public Set<Users> getUsers() {
        return users;
    }

    public void setUsers(Set<Users> user) {
        this.users = user;
    }

    @Override
    public String toString() {
        return getInviteCode() + " " + getInviteCodeStatus() + " " + getEmail();
    }
}
