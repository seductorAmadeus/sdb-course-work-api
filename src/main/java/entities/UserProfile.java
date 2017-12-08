package entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "user_profile")
public class UserProfile {
    @Id
    @GeneratedValue
    @Column(name = "profile_id")
    private BigDecimal profileId;

    /*TODO: Do I need to add an annotation <Column> ? */
    @ManyToOne
    @JoinColumn(name = "user_role_id")
    private UserRole userRole;

    @ManyToOne
    @JoinColumn(name = "user_info_id")
    private UserInfo userInfo;

    @ManyToOne
    @JoinColumn(name = "user_studying_id")
    private UserStudying userStudying;

    @ManyToOne
    @JoinColumn(name = "user_picture_id")
    private UserPicture userPicture;

    @Column(name = "last_seen")
    private Date lastSeen;

    @Column(name = "register_date")
    private Date registerDate;

    public BigDecimal getProfileId() {
        return profileId;
    }

    public void setProfileId(BigDecimal profileId) {
        this.profileId = profileId;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserStudying getUserStudying() {
        return userStudying;
    }

    public void setUserStudying(UserStudying userStudying) {
        this.userStudying = userStudying;
    }

    public UserPicture getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(UserPicture userPicture) {
        this.userPicture = userPicture;
    }

    public Date getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Date lastSeen) {
        this.lastSeen = lastSeen;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
}
