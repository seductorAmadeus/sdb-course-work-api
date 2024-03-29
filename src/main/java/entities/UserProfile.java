package entities;

import org.hibernate.annotations.GenericGenerator;
import validation.constraints.CheckConstraintsIn;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * This class is an entity that describes user's profile
 *
 * @author Rayla Martin
 * @version 0.1
 * @since 0.1
 */
@Entity
@Table(name = "user_profile", uniqueConstraints = {@UniqueConstraint(columnNames = "profile_id")})
public class UserProfile implements Serializable {

    @Id
    @GeneratedValue(generator = "gen")
    @GenericGenerator(name = "gen", strategy = "foreign", parameters = @org.hibernate.annotations.Parameter(name = "property", value = "users"))
    @Column(name = "profile_id", unique = true, nullable = false)
    @Digits(integer = 7, fraction = 0)
    private BigDecimal profileId;

    @ManyToOne()
    @JoinColumn(name = "user_role_id")
    private UserRole userRoleId;

    @ManyToOne()
    @JoinColumn(name = "user_studying_id")
    private UserStudying userStudyingId;

    @Column(name = "last_seen")
    //TODO: Change Timestamp to Date using @Temporal annotation and fix validation
    private Timestamp lastSeen;

    @Column(name = "register_date")
    private Timestamp registerDate;

    @Column(name = "studying_status")
    @NotNull
    @CheckConstraintsIn(constraints = {"YES", "NOT"})
    private String studyingStatus;

    @Column(name = "first_name")
    @NotNull
    @Size(min = 3, max = 20)
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    @Size(min = 3, max = 20)
    private String lastName;

    @Column(name = "middle_name")
    @Size(min = 3, max = 20)
    private String middleName;

    @Column(name = "gender")
    @NotNull
    @CheckConstraintsIn(constraints = {"M", "F"})
    private String gender;

    @Column(name = "date_of_birth")
    @NotNull
    private Timestamp dateOfBirth;

    @Column(name = "reg_status")
    @CheckConstraintsIn(constraints = {"Y", "N"})
    private String regStatus;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Users users;

    @OneToOne(mappedBy = "userProfile", cascade = CascadeType.REMOVE)
    private UserPicture picture;

    public UserProfile() {

    }

    public UserProfile(UserRole userRoleId, UserStudying userStudyingId, Timestamp lastSeen, Timestamp registerDate, String studyingStatus, String firstName, String lastName, String middleName, String gender, Timestamp dateOfBirth, String regStatus, Users users, UserPicture picture) {
        this.userRoleId = userRoleId;
        this.userStudyingId = userStudyingId;
        this.lastSeen = lastSeen;
        this.registerDate = registerDate;
        this.studyingStatus = studyingStatus;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.regStatus = regStatus;
        this.picture = picture;
    }

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

    public UserStudying getUserStudyingId() {
        return userStudyingId;
    }

    public void setUserStudyingId(UserStudying userStudyingId) {
        this.userStudyingId = userStudyingId;
    }

    public UserRole getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(UserRole userRoleId) {
        this.userRoleId = userRoleId;
    }

    public UserPicture getPicture() {
        return picture;
    }

    public void setPicture(UserPicture picture) {
        this.picture = picture;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(getProfileId()).append(" ")
                .append(getUserRoleId().getId()).append(" ")
                .append(getUserStudyingId().getId()).append(" ")
                .append(getLastSeen()).append(" ")
                .append(getRegisterDate()).append(" ")
                .append(getStudyingStatus()).append(" ")
                .append(getFirstName()).append(" ")
                .append(getLastName()).append(" ")
                .append(getMiddleName()).append(" ")
                .append(getGender()).append(" ")
                .append(getDateOfBirth()).append(" ")
                .append(getRegStatus()).append(" ")
                .toString();
    }
}
