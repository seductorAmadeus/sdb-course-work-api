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

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_role_id")
    private UserRole userRoleId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id")
    private UserStudying userStudyingId;

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

    @OneToOne
    @PrimaryKeyJoinColumn
    private Users users;

    @OneToOne(mappedBy = "userProfile", cascade = CascadeType.ALL)
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
        this.users = users;
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

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public UserPicture getPicture() {
        return picture;
    }

    public void setPicture(UserPicture picture) {
        this.picture = picture;
    }
}
