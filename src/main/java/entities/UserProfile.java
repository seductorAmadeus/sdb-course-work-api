package entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "user_profile")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "profile_id")
    private BigDecimal profileId;

    @Column(name = "user_role_id")
    private BigDecimal userRoleId;

    @Column(name = "user_studying_id")
    private BigDecimal userStudyingId;

    @Column(name = "last_seen")
    private Timestamp lastSeen;

    @Column(name = "register_date")
    private Timestamp registerDate;

    @Column(name = "studying_status")
    private String studyingStatus;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "date_of_birth")
    private Timestamp dateOfBirth;

    @Column(name = "reg_status")
    private String regStatus;

    public BigDecimal getProfileId() {
        return profileId;
    }

    public void setProfileId(BigDecimal profileId) {
        this.profileId = profileId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Timestamp getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Timestamp dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getRegStatus() {
        return regStatus;
    }

    public void setRegStatus(String regStatus) {
        this.regStatus = regStatus;
    }

    public BigDecimal getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(BigDecimal userRoleId) {
        this.userRoleId = userRoleId;
    }

    public BigDecimal getUserStudyingId() {
        return userStudyingId;
    }

    public void setUserStudyingId(BigDecimal userStudyingId) {
        this.userStudyingId = userStudyingId;
    }

    public Timestamp getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Timestamp lastSeen) {
        this.lastSeen = lastSeen;
    }

    public Timestamp getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Timestamp registerDate) {
        this.registerDate = registerDate;
    }

    public String getStudyingStatus() {
        return studyingStatus;
    }

    public void setStudyingStatus(String studyingStatus) {
        this.studyingStatus = studyingStatus;
    }
}
