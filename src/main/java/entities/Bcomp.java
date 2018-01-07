package entities;

import validation.constraints.CheckConstraintsIn;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * This class is an entity that describes bcomp.
 *
 * @author Rayla Martin
 * @version 0.1
 * @since 0.1
 */
@Entity
@Table(name = "bcomp")
public class Bcomp {

    /**
     * This field contains bcomp identifier
     */
    @Id
    @SequenceGenerator(name = "bcomp_seq", sequenceName = "BCOMP_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bcomp_seq")
    @Column(name = "bcomp_id")
    private BigDecimal id;

    /**
     * This field contains entity that represents user's session
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "session_id")
    private UserSession userSession;

    /**
     * This field contains bcomp memory
     */
    @Column(name = "memory")
    @Lob
    private String memory;

    /**
     * This field contains the value of the status register
     */
    @Column(name = "rs")
    @Size(min = 9, max = 9)
    private String rs;

    /**
     * This field contains the value of the address register
     */
    @Column(name = "ra")
    @Size(min = 11, max = 11)
    private String ra;

    /**
     * This field contains the value of the data register
     */
    @Column(name = "rd")
    @Size(min = 16, max = 16)
    private String rd;

    /**
     * This field contains the value of the command register
     */
    @Column(name = "rc")
    @Size(min = 16, max = 16)
    private String rc;

    /**
     * This field contains the value of the command counter
     */
    @Column(name = "cc")
    @Size(min = 11, max = 11)
    private String cc;

    /**
     * This field contains the value of the buffer register
     */
    @Column(name = "br")
    @Size(min = 11, max = 11)
    private String br;

    /**
     * This field contains the value of the accumulator
     */
    @Column(name = "ac")
    @Size(min = 16, max = 16)
    private String ac;

    /**
     * This field contains the value of bit "c"
     */
    @Column(name = "c")
    @Size(min = 1, max = 1)
    private String c;

    /**
     * This field contains the value of the key register
     */
    @Column(name = "kr")
    @Size(min = 16, max = 16)
    private String kr;

    /**
     * This field contains single bit denoting the position in the key register
     */
    @Column(name = "bit")
    @Size(min = 1, max = 1)
    private String bit;

    /**
     * This field contains the interrupt request status of the external device №1
     */
    @Column(name = "int_req_ed_1")
    @CheckConstraintsIn(constraints = {"set", "unset"})
    private String intReqEd1;

    /**
     * This field contains the interrupt request status of the external device №2
     */
    @Column(name = "int_req_ed_2")
    @CheckConstraintsIn(constraints = {"set", "unset"})
    private String intReqEd2;

    /**
     * This field contains the interrupt request status of the external device №3
     */
    @Column(name = "int_req_ed_3")
    @CheckConstraintsIn(constraints = {"set", "unset"})
    private String intReqEd3;

    /**
     * This field contains the value of the data register of the external device №1
     */
    @Column(name = "rd_ed_1")
    @Size(min = 8, max = 8)
    private String rdEd1;

    /**
     * This field contains the value of the data register of the external device №2
     */
    @Column(name = "rd_ed_2")
    @Size(min = 8, max = 8)
    private String rdEd2;

    /**
     * This field contains the value of the data register of the external device №3
     */
    @Column(name = "rd_ed_3")
    @Size(min = 8, max = 8)
    private String rdEd3;

    /**
     * This field contains the value of the micro-command memory
     */
    @Column(name = "memory_mc")
    @Lob
    private String memoryMc;

    /**
     * This field contains the value of the micro-command counter
     */
    @Column(name = "c_mc")
    @Size(min = 8, max = 8)
    private String cMc;

    /**
     * This field contains the value of the register of the micro-command
     */
    @Column(name = "r_mc")
    @Size(min = 16, max = 16)
    private String rMc;

    /**
     * This field contains an assembler code
     */
    @Column(name = "asm")
    @Lob
    private String asm;

    /**
     * Sole constructor.
     * For Hibernate code that creates objects via reflection using Class<T>.newInstance().
     * This method requires a public no-arg constructor to be able to instantiate the object
     */
    public Bcomp() {
    }

    /**
     * Constructor.
     *
     * @param userSession (required) {@link UserSession} entity, that represents user session
     *                    for particular bcomp
     * @param memory      (optional) bcomp memory
     * @param rs          (optional) bcomp status register
     * @param ra          (optional) bcomp address register
     * @param rd          (optional) bcomp data register
     * @param rc          (optional) bcomp command register
     * @param cc          (optional) bcomp command counter
     * @param br          (optional) bcomp buffer register
     * @param ac          (optional) bcomp accumulator
     * @param c           (optional) bcomp single bit c
     * @param kr          (optional) bcomp key register
     * @param bit         (optional) single bit denoting the position in the key register
     *                    in the bcomp
     * @param intReqEd1   (optional) interrupt request status of the external device №1 in the bcomp
     * @param intReqEd2   (optional) interrupt request status of the external device №2 in the bcomp
     * @param intReqEd3   (optional) interrupt request status of the external device №3 in the bcomp
     * @param rdEd1       (optional) data register of the external device №1 in the bcomp
     * @param rdEd2       (optional) data register of the external device №2 in the bcomp
     * @param rdEd3       (optional) data register of the external device №3 in the bcomp
     * @param memoryMc    (optional) bcomp micro-command memory
     * @param cMc         (optional) bcomp micro-command counter
     * @param rMc         (optional) bcomp register of the micro-command
     * @param asm         (optional) assembler code
     */
    public Bcomp(UserSession userSession, String memory, String rs, String ra,
                 String rd, String rc, String cc, String br, String ac, String c, String kr,
                 String bit, String intReqEd1, String intReqEd2, String intReqEd3, String rdEd1,
                 String rdEd2, String rdEd3, String memoryMc, String cMc, String rMc, String asm) {
        this.userSession = userSession;
        this.memory = memory;
        this.rs = rs;
        this.ra = ra;
        this.rd = rd;
        this.rc = rc;
        this.cc = cc;
        this.br = br;
        this.ac = ac;
        this.c = c;
        this.kr = kr;
        this.bit = bit;
        this.intReqEd1 = intReqEd1;
        this.intReqEd2 = intReqEd2;
        this.intReqEd3 = intReqEd3;
        this.rdEd1 = rdEd1;
        this.rdEd2 = rdEd2;
        this.rdEd3 = rdEd3;
        this.memoryMc = memoryMc;
        this.cMc = cMc;
        this.rMc = rMc;
        this.asm = asm;
    }

    /**
     * Function to get the value of the field {@link Bcomp#memory}
     *
     * @return String contains value for representation memory of the bcomp
     * @since 0.1
     */
    public String getMemory() {
        return memory;
    }

    /**
     * Procedure for determining the String value {@link Bcomp#memory}
     *
     * @param memory - It's bcomp memory
     * @since 0.1
     */
    public void setMemory(String memory) {
        this.memory = memory;
    }

    /**
     * Function to get the value of the field {@link Bcomp#rs}
     *
     * @return String contains value for representation status register of the bcomp
     * @since 0.1
     */
    public String getRs() {
        return rs;
    }

    /**
     * Procedure for determining the String value {@link Bcomp#rs}
     *
     * @param rs - It's bcomp status register
     * @since 0.1
     */
    public void setRs(String rs) {
        this.rs = rs;
    }

    /**
     * Function to get the value of the field {@link Bcomp#ra}
     *
     * @return String contains value for representation address register of the bcomp
     * @since 0.1
     */
    public String getRa() {
        return ra;
    }

    /**
     * Procedure for determining the String value {@link Bcomp#ra}
     *
     * @param ra - It's bcomp address register
     * @since 0.1
     */
    public void setRa(String ra) {
        this.ra = ra;
    }

    /**
     * Function to get the value of the field {@link Bcomp#rd}
     *
     * @return String contains value for representation data register of the bcomp
     * @since 0.1
     */
    public String getRd() {
        return rd;
    }

    /**
     * Procedure for determining the String value {@link Bcomp#rd}
     *
     * @param rd - It's bcomp data register
     * @since 0.1
     */
    public void setRd(String rd) {
        this.rd = rd;
    }

    /**
     * Function to get the value of the field {@link Bcomp#rc}
     *
     * @return String contains value for representation command register of the bcomp
     * @since 0.1
     */
    public String getRc() {
        return rc;
    }

    /**
     * Procedure for determining the String value {@link Bcomp#rc}
     *
     * @param rc - It's bcomp command register
     * @since 0.1
     */
    public void setRc(String rc) {
        this.rc = rc;
    }

    /**
     * Function to get the value of the field {@link Bcomp#cc}
     *
     * @return String contains value for representation command counter of the bcomp
     * @since 0.1
     */
    public String getCc() {
        return cc;
    }

    /**
     * Procedure for determining the String value {@link Bcomp#cc}
     *
     * @param cc - It's bcomp command counter
     * @since 0.1
     */
    public void setCc(String cc) {
        this.cc = cc;
    }

    /**
     * Function to get the value of the field {@link Bcomp#br}
     *
     * @return String contains value for representation buffer register of the bcomp
     * @since 0.1
     */
    public String getBr() {
        return br;
    }

    /**
     * Procedure for determining the String value {@link Bcomp#br}
     *
     * @param br - It's bcomp buffer register
     * @since 0.1
     */
    public void setBr(String br) {
        this.br = br;
    }

    /**
     * Function to get the value of the field {@link Bcomp#ac}
     *
     * @return String contains value for representation accumulator of the bcomp
     * @since 0.1
     */
    public String getAc() {
        return ac;
    }

    /**
     * Procedure for determining the String value {@link Bcomp#ac}
     *
     * @param ac - It's bcomp accumulator
     * @since 0.1
     */
    public void setAc(String ac) {
        this.ac = ac;
    }

    /**
     * Function to get the value of the field {@link Bcomp#c}
     *
     * @return String contains value for representation single bit c of the bcomp
     * @since 0.1
     */
    public String getC() {
        return c;
    }

    /**
     * Procedure for determining the String value {@link Bcomp#c}
     *
     * @param c - It's bcomp single bit c
     * @since 0.1
     */
    public void setC(String c) {
        this.c = c;
    }

    /**
     * Function to get the value of the field {@link Bcomp#kr}
     *
     * @return String contains value for representation key register of the bcomp
     * @since 0.1
     */
    public String getKr() {
        return kr;
    }

    /**
     * Procedure for determining the String value {@link Bcomp#kr}
     *
     * @param kr - It's bcomp key register
     * @since 0.1
     */
    public void setKr(String kr) {
        this.kr = kr;
    }

    /**
     * Function to get the value of the field {@link Bcomp#bit}
     *
     * @return String contains value for representation single bit denoting the position
     * in the key register in the bcomp
     * @since 0.1
     */
    public String getBit() {
        return bit;
    }

    /**
     * Procedure for determining the String value {@link Bcomp#bit}
     *
     * @param bit - It's single bit denoting the position in the key register
     *            in the bcomp
     * @since 0.1
     */
    public void setBit(String bit) {
        this.bit = bit;
    }

    /**
     * Function to get the value of the field {@link Bcomp#intReqEd1}
     *
     * @return String contains value for representation interrupt request status
     * of the external device №1 in the bcomp
     * @since 0.1
     */
    public String getIntReqEd1() {
        return intReqEd1;
    }

    /**
     * Procedure for determining the String value {@link Bcomp#intReqEd1}
     *
     * @param intReqEd1 - It's interrupt request status of the external device №1 in the bcomp
     * @since 0.1
     */
    public void setIntReqEd1(String intReqEd1) {
        this.intReqEd1 = intReqEd1;
    }

    /**
     * Function to get the value of the field {@link Bcomp#intReqEd2}
     *
     * @return String contains value for representation interrupt request status
     * of the external device №2 in the bcomp
     * @since 0.1
     */
    public String getIntReqEd2() {
        return intReqEd2;
    }

    /**
     * Procedure for determining the String value {@link Bcomp#intReqEd2}
     *
     * @param intReqEd2 - It's interrupt request status of the external device №2 in the bcomp
     * @since 0.1
     */
    public void setIntReqEd2(String intReqEd2) {
        this.intReqEd2 = intReqEd2;
    }

    /**
     * Function to get the value of the field {@link Bcomp#intReqEd3}
     *
     * @return String contains value for representation interrupt request status
     * of the external device №3 in the bcomp
     * @since 0.1
     */
    public String getIntReqEd3() {
        return intReqEd3;
    }

    /**
     * Procedure for determining the String value {@link Bcomp#intReqEd3}
     *
     * @param intReqEd3 - It's interrupt request status of the external device №3 in the bcomp
     * @since 0.1
     */
    public void setIntReqEd3(String intReqEd3) {
        this.intReqEd3 = intReqEd3;
    }

    /**
     * Function to get the value of the field {@link Bcomp#rdEd1}
     *
     * @return String contains value for representation data register
     * of the external device №1 in the bcomp
     * @since 0.1
     */
    public String getRdEd1() {
        return rdEd1;
    }

    /**
     * Procedure for determining the String value {@link Bcomp#rdEd1}
     *
     * @param rdEd1 - It's data register of the external device №1 in the bcomp
     * @since 0.1
     */
    public void setRdEd1(String rdEd1) {
        this.rdEd1 = rdEd1;
    }

    /**
     * Function to get the value of the field {@link Bcomp#rdEd2}
     *
     * @return String contains value for representation data register
     * of the external device №2 in the bcomp
     * @since 0.1
     */
    public String getRdEd2() {
        return rdEd2;
    }

    /**
     * Procedure for determining the String value {@link Bcomp#rdEd2}
     *
     * @param rdEd2 - It's data register of the external device №2 in the bcomp
     * @since 0.1
     */
    public void setRdEd2(String rdEd2) {
        this.rdEd2 = rdEd2;
    }

    /**
     * Function to get the value of the field {@link Bcomp#rdEd3}
     *
     * @return String contains value for representation data register
     * of the external device №3 in the bcomp
     * @since 0.1
     */
    public String getRdEd3() {
        return rdEd3;
    }

    /**
     * Procedure for determining the String value {@link Bcomp#rdEd3}
     *
     * @param rdEd3 - It's data register of the external device №3 in the bcomp
     * @since 0.1
     */
    public void setRdEd3(String rdEd3) {
        this.rdEd3 = rdEd3;
    }

    /**
     * Function to get the value of the field {@link Bcomp#memoryMc}
     *
     * @return String contains value for representation micro-command memory of the bcomp
     * @since 0.1
     */
    public String getMemoryMc() {
        return memoryMc;
    }

    /**
     * Procedure for determining the String value {@link Bcomp#memoryMc}
     *
     * @param memoryMc - It's bcomp micro-command memory
     * @since 0.1
     */
    public void setMemoryMc(String memoryMc) {
        this.memoryMc = memoryMc;
    }

    /**
     * Function to get the value of the field {@link Bcomp#cMc}
     *
     * @return String contains value for representation micro-command counter of the bcomp
     * @since 0.1
     */
    public String getcMc() {
        return cMc;
    }

    /**
     * Procedure for determining the String value {@link Bcomp#cMc}
     *
     * @param cMc - It's bcomp micro-command counter
     * @since 0.1
     */
    public void setcMc(String cMc) {
        this.cMc = cMc;
    }

    /**
     * Function to get the value of the field {@link Bcomp#rMc}
     *
     * @return String contains value for representation register of the micro-command of the bcomp
     * @since 0.1
     */
    public String getrMc() {
        return rMc;
    }

    /**
     * Procedure for determining the String value {@link Bcomp#rMc}
     *
     * @param rMc - It's bcomp register of the micro-command
     * @since 0.1
     */
    public void setrMc(String rMc) {
        this.rMc = rMc;
    }

    /**
     * Function to get the value of the field {@link Bcomp#asm}
     *
     * @return String contains value for representation assembler code
     * @since 0.1
     */
    public String getAsm() {
        return asm;
    }

    /**
     * Procedure for determining the String value {@link Bcomp#asm}
     *
     * @param asm - It's just assembler code
     * @since 0.1
     */
    public void setAsm(String asm) {
        this.asm = asm;
    }

    /**
     * Function to get the value of the field {@link Bcomp#id}
     *
     * @return BigDecimal contains value for representation id number of the bcomp.
     * @since 0.1
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     * Procedure for determining the BigDecimal value {@link Bcomp#id}
     *
     * @param id - It's just bcomp personal identifier
     * @since 0.1
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }

    /**
     * Function to get the value of the field {@link Bcomp#userSession}
     *
     * @return {@link UserSession} entity contains value for representation user session associated with bcomp.
     * @see UserSession
     * @since 0.1
     */
    public UserSession getUserSession() {
        return userSession;
    }

    /**
     * Procedure for determining the UserSession value {@link Bcomp#userSession}
     *
     * @param userSession - It's just user's session
     * @since 0.1
     */
    public void setUserSession(UserSession userSession) {
        this.userSession = userSession;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(getId()).append(" ")
                .append(getUserSession().getId()).append(" ")
                .append(getMemory()).append(" ")
                .append(getRs()).append(" ")
                .append(getRa()).append(" ")
                .append(getRd()).append(" ")
                .append(getRc()).append(" ")
                .append(getCc()).append(" ")
                .append(getBr()).append(" ")
                .append(getAc()).append(" ")
                .append(getC()).append(" ")
                .append(getKr()).append(" ")
                .append(getBit()).append(" ")
                .append(getIntReqEd1()).append(" ")
                .append(getIntReqEd2()).append(" ")
                .append(getIntReqEd3()).append(" ")
                .append(getRdEd1()).append(" ")
                .append(getRdEd2()).append(" ")
                .append(getRdEd3()).append(" ")
                .append(getMemoryMc()).append(" ")
                .append(getcMc()).append(" ")
                .append(getrMc()).append(" ")
                .append(getAsm())
                .toString();
    }
}
