package pojo;

import javax.persistence.*;

import java.io.Serializable;

@Embeddable
public class PrizecategoryPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "DRAWID")
    private int drawid;
    @Basic(optional = false)
    @Column(name = "ID")
    private int id;

    public PrizecategoryPK() {
    }

    public PrizecategoryPK(int drawid, int id) {
        this.drawid = drawid;
        this.id = id;
    }

    public int getDrawid() {
        return drawid;
    }

    public void setDrawid(int drawid) {
        this.drawid = drawid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += drawid;
        hash += id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrizecategoryPK other)) {
            return false;
        }
        if (this.drawid != other.drawid) {
            return false;
        }
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "pojo.PrizecategoryPK[ drawid=" + drawid + ", id=" + id + " ]";
    }

}
