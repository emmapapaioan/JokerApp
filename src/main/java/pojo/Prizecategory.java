package pojo;

import javax.persistence.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@Table(name = "PRIZECATEGORY")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Prizecategory.findAll", query = "SELECT p FROM Prizecategory p")
        , @NamedQuery(name = "Prizecategory.findByDrawid", query = "SELECT p FROM Prizecategory p WHERE p.prizecategoryPK.drawid = :drawid")
        , @NamedQuery(name = "Prizecategory.findById", query = "SELECT p FROM Prizecategory p WHERE p.prizecategoryPK.id = :id")
        , @NamedQuery(name = "Prizecategory.findByName", query = "SELECT p FROM Prizecategory p WHERE p.name = :name")
        , @NamedQuery(name = "Prizecategory.findByWinners", query = "SELECT p FROM Prizecategory p WHERE p.winners = :winners")
        , @NamedQuery(name = "Prizecategory.findByDivident", query = "SELECT p FROM Prizecategory p WHERE p.divident = :divident")})
public class Prizecategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PrizecategoryPK prizecategoryPK;
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;
    @Basic(optional = false)
    @Column(name = "WINNERS")
    private int winners;
    @Basic(optional = false)
    @Column(name = "DIVIDENT")
    private double divident;
    @JoinColumn(name = "DRAWID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Draw draw;

    public Prizecategory() {
    }

    public Prizecategory(PrizecategoryPK prizecategoryPK) {
        this.prizecategoryPK = prizecategoryPK;
    }

    public Prizecategory(PrizecategoryPK prizecategoryPK, String name, int winners, double divident) {
        this.prizecategoryPK = prizecategoryPK;
        this.name = name;
        this.winners = winners;
        this.divident = divident;
    }

    public Prizecategory(int drawid, int id) {
        this.prizecategoryPK = new PrizecategoryPK(drawid, id);
    }

    public PrizecategoryPK getPrizecategoryPK() {
        return prizecategoryPK;
    }

    public void setPrizecategoryPK(PrizecategoryPK prizecategoryPK) {
        this.prizecategoryPK = prizecategoryPK;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWinners() {
        return winners;
    }

    public void setWinners(int winners) {
        this.winners = winners;
    }

    public double getDivident() {
        return divident;
    }

    public void setDivident(double divident) {
        this.divident = divident;
    }

    public Draw getDraw() {
        return draw;
    }

    public void setDraw(Draw draw) {
        this.draw = draw;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prizecategoryPK != null ? prizecategoryPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prizecategory other)) {
            return false;
        }
        return (this.prizecategoryPK != null || other.prizecategoryPK == null) && (this.prizecategoryPK == null || this.prizecategoryPK.equals(other.prizecategoryPK));
    }

    @Override
    public String toString() {
        return "pojo.Prizecategory[ prizecategoryPK=" + prizecategoryPK + " ]";
    }

}
