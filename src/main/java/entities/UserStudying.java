package entities;

import javax.persistence.*;
import java.math.BigDecimal;
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

    @OneToMany(mappedBy = "userStudyingId", cascade = CascadeType.ALL)
    private Set<UserProfile> userProfiles;

    public UserStudying() {
    }

    public UserStudying(String userGroup, Set<UserProfile> userProfiles) {
        this.userGroup = userGroup;
        this.userProfiles = userProfiles;
    }

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

    public Set<UserProfile> getUserProfiles() {
        return userProfiles;
    }

    public void setUserProfiles(Set<UserProfile> userProfiles) {
        this.userProfiles = userProfiles;
    }
}
