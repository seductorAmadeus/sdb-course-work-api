package entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Set;

@Entity
@Table(name = "user_studying")
public class UserStudying {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private BigDecimal id;

    @Column(name = "user_group")
    private String userGroup;

    @Column(name = "status")
    private Blob picture;

    @OneToMany(mappedBy = "user_studying_id")
    private Set<UserProfile> userProfile;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    public Blob getPicture() {
        return picture;
    }

    public void setPicture(Blob picture) {
        this.picture = picture;
    }

    public Set<UserProfile> getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(Set<UserProfile> userProfile) {
        this.userProfile = userProfile;
    }
}
