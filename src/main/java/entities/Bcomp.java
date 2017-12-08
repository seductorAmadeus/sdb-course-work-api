package entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Clob;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bcomp")
public class Bcomp {

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "user_bcomp_settings",
            joinColumns = {@JoinColumn(name = "user_settings")}, inverseJoinColumns = {@JoinColumn(name = "description")})
    private
    Set<BcompGuide> bcompGuides = new HashSet<>();
    /*TODO: Do I need to add an annotation <Column> ? */
    @ManyToOne
    @JoinColumn(name = "session_id")
    private UserSession userSessionID;
    @Id
    @GeneratedValue
    @Column(name = "user_settings")
    private BigDecimal userSettings;
    @Column(name = "memory")
    private Clob memory;
    @Column(name = "rs")
    private String rs;
    @Column(name = "ra")
    private String ra;
    @Column(name = "rd")
    private String rd;
    @Column(name = "rc")
    private String rc;
    @Column(name = "cc")
    private String cc;
    @Column(name = "br")
    private String br;
    @Column(name = "ac")
    private String ac;
    @Column(name = "c")
    private String c;
    @Column(name = "kr")
    private String kr;
    @Column(name = "bit")
    private String bit;
    @Column(name = "int_req_ed_1")
    private String intReqEd1;
    @Column(name = "int_req_ed_2")
    private String intReqEd2;
    @Column(name = "int_req_ed_3")
    private String intReqEd3;
    @Column(name = "rd_ed_1")
    private String rdEd1;
    @Column(name = "rd_ed_2")
    private String rdEd2;
    @Column(name = "rd_ed_3")
    private String rdEd3;
    @Column(name = "memory_mc")
    private Clob memoryMc;
    @Column(name = "c_mc")
    private String cMc;
    @Column(name = "r_mc")
    private String rMc;
    @Column(name = "asm")
    private Clob asm;

    public UserSession getUserSessionID() {
        return userSessionID;
    }

    public void setUserSessionID(UserSession userSessionID) {
        this.userSessionID = userSessionID;
    }

    public BigDecimal getUserSettings() {
        return userSettings;
    }

    public void setUserSettings(BigDecimal userSettings) {
        this.userSettings = userSettings;
    }

    public Clob getMemory() {
        return memory;
    }

    public void setMemory(Clob memory) {
        this.memory = memory;
    }

    public String getRs() {
        return rs;
    }

    public void setRs(String rs) {
        this.rs = rs;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getRd() {
        return rd;
    }

    public void setRd(String rd) {
        this.rd = rd;
    }

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBr() {
        return br;
    }

    public void setBr(String br) {
        this.br = br;
    }

    public String getAc() {
        return ac;
    }

    public void setAc(String ac) {
        this.ac = ac;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getKr() {
        return kr;
    }

    public void setKr(String kr) {
        this.kr = kr;
    }

    public String getBit() {
        return bit;
    }

    public void setBit(String bit) {
        this.bit = bit;
    }

    public String getIntReqEd1() {
        return intReqEd1;
    }

    public void setIntReqEd1(String intReqEd1) {
        this.intReqEd1 = intReqEd1;
    }

    public String getIntReqEd2() {
        return intReqEd2;
    }

    public void setIntReqEd2(String intReqEd2) {
        this.intReqEd2 = intReqEd2;
    }

    public String getIntReqEd3() {
        return intReqEd3;
    }

    public void setIntReqEd3(String intReqEd3) {
        this.intReqEd3 = intReqEd3;
    }

    public String getRdEd1() {
        return rdEd1;
    }

    public void setRdEd1(String rdEd1) {
        this.rdEd1 = rdEd1;
    }

    public String getRdEd2() {
        return rdEd2;
    }

    public void setRdEd2(String rdEd2) {
        this.rdEd2 = rdEd2;
    }

    public String getRdEd3() {
        return rdEd3;
    }

    public void setRdEd3(String rdEd3) {
        this.rdEd3 = rdEd3;
    }

    public Clob getMemoryMc() {
        return memoryMc;
    }

    public void setMemoryMc(Clob memoryMc) {
        this.memoryMc = memoryMc;
    }

    public String getcMc() {
        return cMc;
    }

    public void setcMc(String cMc) {
        this.cMc = cMc;
    }

    public String getrMc() {
        return rMc;
    }

    public void setrMc(String rMc) {
        this.rMc = rMc;
    }

    public Clob getAsm() {
        return asm;
    }

    public void setAsm(Clob asm) {
        this.asm = asm;
    }

    public Set<BcompGuide> getBcompGuides() {
        return bcompGuides;
    }

    public void setBcompGuides(Set<BcompGuide> bcompGuides) {
        this.bcompGuides = bcompGuides;
    }
}
