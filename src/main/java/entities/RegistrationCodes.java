package entities;

import validation.constraints.CheckConstraintsIn;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    /**
     * This field contains registration code identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reg_code_id")
    @Digits(integer = 7, fraction = 0)
    @NotNull
    private BigDecimal regCodeId;

    /**
     * This field contains user's invite code
     */
    @Column(name = "invite_code")
    @Digits(integer = 38, fraction = 0)
    @NotNull
    private BigDecimal inviteCode;

    /**
     * This field contains status for user's invite code
     */
    @Column(name = "invite_code_status")
    @NotNull
    @CheckConstraintsIn(constraints = {"available", "not available"})
    private String inviteCodeStatus;

    /**
     * This field contains email of the admin who issued the invite code to the user
     */
    @Column(name = "email")
    @NotNull
    //TODO: create new annotation for email validation
    @Email
    @Size(max = 25)
    private String email;

    /**
     * This field contains set of entities that represents users
     */
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "registrationCode")
    private Set<Users> users;

    /**
     * Sole constructor.
     * For Hibernate code that creates objects via reflection using Class<T>.newInstance().
     * This method requires a public no-arg constructor to be able to instantiate the object
     */
    public RegistrationCodes() {
    }

    /**
     * Constructor.
     *
     * @param inviteCode       (required) user's invite code
     * @param inviteCodeStatus (required) status for user's invite code
     * @param email            (required) admin's email
     */
    public RegistrationCodes(BigDecimal inviteCode, String inviteCodeStatus, String email) {
        this.inviteCode = inviteCode;
        this.inviteCodeStatus = inviteCodeStatus;
        this.email = email;
    }

    /**
     * Function to get the value of the field {@link RegistrationCodes#inviteCode}
     *
     * @return BigDecimal contains value for representation invite code
     * @since 0.1
     */
    public BigDecimal getInviteCode() {
        return inviteCode;
    }

    /**
     * Procedure for determining the BigDecimal value {@link RegistrationCodes#inviteCode}
     *
     * @param inviteCode - It's just invite code
     * @since 0.1
     */
    public void setInviteCode(BigDecimal inviteCode) {
        this.inviteCode = inviteCode;
    }

    /**
     * Function to get the value of the field {@link RegistrationCodes#inviteCodeStatus}
     *
     * @return String contains invite code status for user's invite code
     * @since 0.1
     */
    public String getInviteCodeStatus() {
        return inviteCodeStatus;
    }

    /**
     * Procedure for determining the String value {@link RegistrationCodes#inviteCodeStatus}
     *
     * @param inviteCodeStatus - It's just status for invite code
     * @since 0.1
     */
    public void setInviteCodeStatus(String inviteCodeStatus) {
        this.inviteCodeStatus = inviteCodeStatus;
    }

    /**
     * Function to get the value of the field {@link RegistrationCodes#email}
     *
     * @return String contains value for representation admin's email
     * @since 0.1
     */
    public String getEmail() {
        return email;
    }

    /**
     * Procedure for determining the String value {@link RegistrationCodes#email}
     *
     * @param email - It's just admin's email
     * @since 0.1
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Function to get the value of the field {@link RegistrationCodes#users}
     *
     * @return set of users in the system that associated with registration code
     * @since 0.1
     */
    public Set<Users> getUsers() {
        return users;
    }

    /**
     * Procedure for determining the String value {@link RegistrationCodes#users}
     *
     * @param user - It's just set of users
     * @since 0.1
     */
    public void setUsers(Set<Users> user) {
        this.users = user;
    }

    /**
     * Function to get the value of the field {@link RegistrationCodes#regCodeId}
     *
     * @return BigDecimal contains value for representation id number of the registration code
     * @since 0.1
     */
    public BigDecimal getRegCodeId() {
        return regCodeId;
    }

    /**
     * Procedure for determining the BigDecimal value {@link RegistrationCodes#regCodeId}
     *
     * @param regCodeId - It's just registration code id
     * @since 0.1
     */
    public void setRegCodeId(BigDecimal regCodeId) {
        this.regCodeId = regCodeId;
    }

    /**
     * It's just my implementation of toString method
     *
     * @since 0.1
     */
    @Override
    public String toString() {
        return getRegCodeId() + " " + getInviteCode() + " " + getInviteCodeStatus() + " " + getEmail();
    }
}
