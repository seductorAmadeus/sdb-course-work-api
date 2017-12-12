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
