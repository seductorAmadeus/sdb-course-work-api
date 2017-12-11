package entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "user_role")
public class UserRole {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private BigDecimal id;

    @Column(name = "type")
    @Type(type = "entities.UserT")
    private UserT type;

    /*TODO: Do I need to add an annotation <Column> ? */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users userID;

    @OneToMany(mappedBy = "userRole")
    private Set<UserProfile> userProfile;

    public UserRole() {
    }

    public UserRole(BigDecimal id, UserT type, Users userID) {
        this.id = id;
        this.type = type;
        this.userID = userID;
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

    public Users getUserID() {
        return userID;
    }

    public void setUserID(Users user) {
        this.userID = user;
    }
}
