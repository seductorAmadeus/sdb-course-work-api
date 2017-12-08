package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.sql.Clob;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bcomp_guide")
public class BcompGuide {

    @Column(name = "command")
    private String command;

    @Column(name = "admin_comment")
    private Clob adminComment;

    @ManyToMany(mappedBy = "bcomp_guide")
    private Set<Bcomp> bcomps = new HashSet<>();

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Clob getAdminComment() {
        return adminComment;
    }

    public void setAdminComment(Clob adminComment) {
        this.adminComment = adminComment;
    }

    public Set<Bcomp> getBcomps() {
        return bcomps;
    }

    public void setBcomps(Set<Bcomp> bcomps) {
        this.bcomps = bcomps;
    }
}
