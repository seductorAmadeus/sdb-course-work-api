package entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is an entity that describes user's interface settings.
 *
 * @author Rayla Martin
 * @version 0.1
 * @since 0.1
 */

@Entity
@Table(name = "bcomp_settings")
public class BcompSettings {

    /**
     * This field contains bcomp settings identifier
     */
    @Id
    @SequenceGenerator(name = "bcomp_settings_seq", sequenceName = "BCOMP_SETTINGS_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bcomp_settings_seq")
    @Column(name = "bcomp_settings_id")
    private BigDecimal id;

    /**
     * This field contains value that represents some bcomp setting
     */
    @Column(name = "value")
    private String value;

    /**
     * This field contains value that represents type of some bcomp setting
     */
    @Column(name = "type")
    private String type;

    /**
     * This field contains list of bcomp settings that represents user's session
     */
    @ManyToMany(cascade = CascadeType.REMOVE, mappedBy = "bcompSettings", fetch = FetchType.EAGER)
    private List<UserSession> userSessions = new ArrayList<>();

    /**
     * Sole constructor.
     * For Hibernate code that creates objects via reflection using Class<T>.newInstance().
     * This method requires a public no-arg constructor to be able to instantiate the object
     */
    public BcompSettings() {
    }

    /**
     * Constructor.
     *
     * @param value (required) bcomp setting value
     * @param type  (required) bcomp setting type
     */
    public BcompSettings(String value, String type) {
        this.value = value;
        this.type = type;
    }

    /**
     * Function to get the value of the field {@link BcompSettings#id}
     *
     * @return BigDecimal contains value for representation bcomp setting id number.
     * @since 0.1
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     * Procedure for determining the BigDecimal value {@link BcompSettings#id}
     *
     * @param id - It's just bcomp setting personal identifier
     * @since 0.1
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }

    /**
     * Function to get the value of the field {@link BcompSettings#value}
     *
     * @return String contains bcomp setting
     * @since 0.1
     */
    public String getValue() {
        return value;
    }

    /**
     * Procedure for determining the String value {@link BcompSettings#value}
     *
     * @param value - It's just bcomp setting
     * @since 0.1
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Function to get the value of the field {@link BcompSettings#type}
     *
     * @return String contains bcomp setting type
     * @since 0.1
     */
    public String getType() {
        return type;
    }

    /**
     * Procedure for determining the String value {@link BcompSettings#type}
     *
     * @param type - It's just bcomp setting type
     * @since 0.1
     */
    public void setType(String type) {
        this.type = type;
    }


    /**
     * Function to get the value of the field {@link BcompSettings#userSessions}
     *
     * @return list of user sessions in the system that assosiated with bcomp setting
     * @see UserSession
     * @since 0.1
     */
    public List<UserSession> getUserSessions() {
        return userSessions;
    }

    /**
     * Procedure for determining the List of user sessions {@link BcompSettings#userSessions}
     *
     * @param userSessions - It's set of user sessions in the system that associated with bcomp setting
     * @since 0.1
     */
    public void setUserSessions(List<UserSession> userSessions) {
        this.userSessions = userSessions;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(getId()).append(" ")
                .append(getValue()).append(" ")
                .append(getType()).append(" ")
                .toString();
    }
}
