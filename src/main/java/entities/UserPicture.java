package entities;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.Blob;

/**
 * This class is an entity that describes user's profile picture in the system
 *
 * @author Rayla Martin
 * @version 0.1
 * @since 0.1
 */
@Entity
@Table(name = "user_picture")
public class UserPicture {

    /**
     * This field contains user picture identifier
     */
    @Id
    @Column(name = "user_profile_id")
    @Digits(integer = 7, fraction = 0)
    @NotNull
    private BigDecimal id;

    /**
     * This field contains name of the user picture
     */
    @Column(name = "picname")
    @Size(min = 1, max = 20)
    private String pictureName;

    /**
     * This field contains bytes array that represents picture
     */
    @Column(name = "picture")
    @Lob
    private Blob picture;

    /**
     * This field contains entity that represents user profile
     */
    @OneToOne
    @PrimaryKeyJoinColumn
    private UserProfile userProfile;

    /**
     * Sole constructor.
     * For Hibernate code that creates objects via reflection using Class<T>.newInstance().
     * This method requires a public no-arg constructor to be able to instantiate the object
     */
    public UserPicture() {
    }

    /**
     * Constructor.
     *
     * @param pictureName (optional) name of the picture (avatar)
     * @param picture     (optional) just picture
     * @param userProfile (required) {@link UserProfile} entity, that represents user profile
     *                    for particular user
     */
    public UserPicture(String pictureName, Blob picture, UserProfile userProfile) {
        this.pictureName = pictureName;
        this.picture = picture;
        this.userProfile = userProfile;
    }

    /**
     * Function to get the value of the field {@link UserPicture#id}
     *
     * @return BigDecimal contains value for representation user picture id number.
     * @since 0.1
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     * Procedure for determining the BigDecimal value {@link UserPicture#id}
     *
     * @param id - It's just user picture personal identifier
     * @since 0.1
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }

    /**
     * Function to get the value of the field {@link UserPicture#pictureName}
     *
     * @return String contains value for representation user picture name
     * @since 0.1
     */
    public String getPictureName() {
        return pictureName;
    }

    /**
     * Procedure for determining the String value {@link UserPicture#pictureName}
     *
     * @param pictureName - It's just user picture name
     * @since 0.1
     */
    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    /**
     * Function to get the value of the field {@link UserPicture#picture}
     *
     * @return {@link Blob} contains value for representation user picture
     * @see Blob
     * @since 0.1
     */
    public Blob getPicture() {
        return picture;
    }

    /**
     * Procedure for determining the {@link Blob} value {@link UserPicture#picture}
     *
     * @param picture - It's just user picture
     * @since 0.1
     */
    public void setPicture(Blob picture) {
        this.picture = picture;
    }

    /**
     * Function to get the value of the field {@link UserPicture#userProfile}
     *
     * @return user's profile that represents a particular user in the system
     * @see UserProfile
     * @since 0.1
     */
    public UserProfile getUserProfile() {
        return userProfile;
    }

    /**
     * Procedure for determining the UserProfile value {@link UserPicture#userProfile}
     *
     * @param userProfile - It's user's profile that represents a particular user in the system
     * @since 0.1
     */
    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(getId()).append(" ")
                .append(getPictureName()).append(" ")
                .append(getPicture()).append(" ")
                .toString();
    }
}
