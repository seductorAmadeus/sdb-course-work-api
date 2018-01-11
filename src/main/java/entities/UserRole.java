package entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

/**
 * This class is an entity that describes user's role
 *
 * @author Rayla Martin
 * @version 0.1
 * @since 0.1
 */
@Entity
@Table(name = "user_role")
public class UserRole {
    @Id
    @SequenceGenerator(name="user_role_seq", sequenceName="USER_ROLE_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_role_seq")
    @Column(name = "user_role_id")
    private BigDecimal id;

    @Column(name = "type")
    @Type(type = "entities.UserRoleType")
    private UserT type;

    @OneToMany(mappedBy = "userRoleId")
    private Set<UserProfile> userProfile;

    public UserRole() {
    }

    public UserRole(BigDecimal id, UserT type) {
        this.id = id;
        this.type = type;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public UserT getType() {
        return type;
    }

    public void setType(UserT type) {
        this.type = type;
    }

    public Set<UserProfile> getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(Set<UserProfile> userProfile) {
        this.userProfile = userProfile;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(getId()).append(" ")
                //TODO: fix getType() value.
                .append(getType()).append(" ")
                .toString();
    }
}
