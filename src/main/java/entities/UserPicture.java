package entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Blob;

@Entity
@Table(name = "user_picture")
public class UserPicture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_profile_id")
    private BigDecimal id;

    @Column(name = "picname")
    private String pictureName;

    @Column(name = "picture")
    @Lob
    private Blob picture;

    @OneToOne
    @PrimaryKeyJoinColumn
    private UserProfile userProfile;

    public UserPicture() {

    }

    public UserPicture(String pictureName, Blob picture, UserProfile userProfile) {
        this.pictureName = pictureName;
        this.picture = picture;
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

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
}
