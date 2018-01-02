package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

/**
 * This class is an entity that describes user's invite code
 *
 * @author Rayla Martin
 * @version 0.1
 * @since 0.1
 */

@Entity
@Table(name = "registration_codes")
public class RegistrationCodes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reg_code_id")
    private BigDecimal regCodeId;

    @Column(name = "invite_code")
    private BigDecimal inviteCode;

    @Column(name = "invite_code_status")
    private String inviteCodeStatus;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "registrationCode")
    private Set<Users> users;

    public RegistrationCodes() {
    }

    public RegistrationCodes(BigDecimal inviteCode, String inviteCodeStatus, String email) {
        this.inviteCode = inviteCode;
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

    public BigDecimal getRegCodeId() {
        return regCodeId;
    }

    public void setRegCodeId(BigDecimal regCodeId) {
        this.regCodeId = regCodeId;
    }

    @Override
    public String toString() {
        return getInviteCode() + " " + getInviteCodeStatus() + " " + getEmail();
    }
}
