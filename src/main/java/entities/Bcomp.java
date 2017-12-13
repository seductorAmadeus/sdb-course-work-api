package entities;

import javax.persistence.*;
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
     * This field contains bcomp identifier from the database
     */
    @Id
    @GeneratedValue
    @Column(name = "bcomp_id")
    private BigDecimal id;

    /**
     * This field contains the unique session identifier
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "session_id")
    private UserSession sessionId;

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
    private String rs;

    /**
     * This field contains the value of the address register
     */
    @Column(name = "ra")
    private String ra;

    /**
     * This field contains the value of the data register
     */
    @Column(name = "rd")
    private String rd;

    /**
     * This field contains the value of the command register
     */
    @Column(name = "rc")
    private String rc;

    /**
     * This field contains the value of the command counter
     */
    @Column(name = "cc")
    private String cc;

    /**
     * This field contains the value of the buffer register
     */
    @Column(name = "br")
    private String br;

    /**
     * This field contains the value of the accumulator
     */
    @Column(name = "ac")
    private String ac;

    /**
     * This field contains the value of bit "c"
     */
    @Column(name = "c")
    private String c;

    /**
     * This field contains the value of the key register
     */
    @Column(name = "kr")
    private String kr;

    /**
     * This field contains single bit denoting the position in the key register
     */
    @Column(name = "bit")
    private String bit;

    /**
     * This field contains the interrupt request status of the external device №1
     */
    @Column(name = "int_req_ed_1")
    private String intReqEd1;

    /**
     * This field contains the interrupt request status of the external device №2
     */
    @Column(name = "int_req_ed_2")
    private String intReqEd2;

    /**
     * This field contains the interrupt request status of the external device №3
     */
    @Column(name = "int_req_ed_3")
    private String intReqEd3;

    /**
     * This field contains the value of the data register of the external device №1
     */
    @Column(name = "rd_ed_1")
    private String rdEd1;

    /**
     * This field contains the value of the data register of the external device №2
     */
    @Column(name = "rd_ed_2")
    private String rdEd2;

    /**
     * This field contains the value of the data register of the external device №3
     */
    @Column(name = "rd_ed_3")
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
    private String  cMc;

    /**
     * This field contains the value of the register of the micro-command
     */
    @Column(name = "r_mc")
    private String rMc;

    /**
     * This field contains an assembler code
     */
    @Column(name = "asm")
    @Lob
    private String asm;

    public Bcomp() {

    }

    public Bcomp(BigDecimal id, UserSession sessionId, String memory, String rs, String ra,
                 String rd, String rc, String cc, String br, String ac, String c, String kr,
                 String bit, String intReqEd1, String intReqEd2, String intReqEd3, String rdEd1,
                 String rdEd2, String rdEd3, String memoryMc, String cMc, String rMc, String asm) {
        this.id = id;
        this.sessionId = sessionId;
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

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
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

    public String getMemoryMc() {
        return memoryMc;
    }

    public void setMemoryMc(String memoryMc) {
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

    public String getAsm() {
        return asm;
    }

    public void setAsm(String asm) {
        this.asm = asm;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public UserSession getSessionId() {
        return sessionId;
    }

    public void setSessionId(UserSession sessionId) {
        this.sessionId = sessionId;
    }
}
