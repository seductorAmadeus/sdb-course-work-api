package entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Set;

@Entity
@Table(name = "user_picture")
public class UserPicture {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private BigDecimal id;

    @Column(name = "picname")
    private String pictureName;

    @Column(name = "picture")
    private Blob picture;

    @OneToMany(mappedBy = "user_picture_id")
    private Set<UserProfile> userProfile;

    public Set<UserProfile> getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(Set<UserProfile> userProfile) {
        this.userProfile = userProfile;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public Blob getPicture() {
        return picture;
    }

    public void setPicture(Blob picture) {
        this.picture = picture;
    }
}
