package entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "user_session")
public class UserSession {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private BigDecimal id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    /*TODO: add constraint*/
    @Column(name = "status")
    private String status;


    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
