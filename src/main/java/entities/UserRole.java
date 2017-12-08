package entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;

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
    private Users user;

    public UserRole() {
    }

    public UserRole(BigDecimal id, UserT type, Users user) {
        this.id = id;
        this.type = type;
        this.user = user;
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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
