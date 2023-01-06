package pojo;

import javax.persistence.*;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "DRAW")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Draw.findAll", query = "SELECT d FROM Draw d")
        , @NamedQuery(name = "Draw.findById", query = "SELECT d FROM Draw d WHERE d.id = :id")
        , @NamedQuery(name = "Draw.findByDate", query = "SELECT d FROM Draw d WHERE d.date = :date")
        , @NamedQuery(name = "Draw.findByWinningnumber1", query = "SELECT d FROM Draw d WHERE d.winningnumber1 = :winningnumber1")
        , @NamedQuery(name = "Draw.findByWinningnumber2", query = "SELECT d FROM Draw d WHERE d.winningnumber2 = :winningnumber2")
        , @NamedQuery(name = "Draw.findByWinningnumber3", query = "SELECT d FROM Draw d WHERE d.winningnumber3 = :winningnumber3")
        , @NamedQuery(name = "Draw.findByWinningnumber4", query = "SELECT d FROM Draw d WHERE d.winningnumber4 = :winningnumber4")
        , @NamedQuery(name = "Draw.findByWinningnumber5", query = "SELECT d FROM Draw d WHERE d.winningnumber5 = :winningnumber5")
        , @NamedQuery(name = "Draw.findByBonus", query = "SELECT d FROM Draw d WHERE d.bonus = :bonus")})
public class Draw implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @Column(name = "WINNINGNUMBER1")
    private int winningnumber1;
    @Basic(optional = false)
    @Column(name = "WINNINGNUMBER2")
    private int winningnumber2;
    @Basic(optional = false)
    @Column(name = "WINNINGNUMBER3")
    private int winningnumber3;
    @Basic(optional = false)
    @Column(name = "WINNINGNUMBER4")
    private int winningnumber4;
    @Basic(optional = false)
    @Column(name = "WINNINGNUMBER5")
    private int winningnumber5;
    @Basic(optional = false)
    @Column(name = "BONUS")
    private int bonus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "draw")
    private List<Prizecategory> prizecategoryList;

    public Draw() {
    }

    public Draw(Integer id) {
        this.id = id;
    }

    public Draw(Integer id, Date date, int winningnumber1, int winningnumber2, int winningnumber3, int winningnumber4, int winningnumber5, int bonus) {
        this.id = id;
        this.date = date;
        this.winningnumber1 = winningnumber1;
        this.winningnumber2 = winningnumber2;
        this.winningnumber3 = winningnumber3;
        this.winningnumber4 = winningnumber4;
        this.winningnumber5 = winningnumber5;
        this.bonus = bonus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getWinningnumber1() {
        return winningnumber1;
    }

    public void setWinningnumber1(int winningnumber1) {
        this.winningnumber1 = winningnumber1;
    }

    public int getWinningnumber2() {
        return winningnumber2;
    }

    public void setWinningnumber2(int winningnumber2) {
        this.winningnumber2 = winningnumber2;
    }

    public int getWinningnumber3() {
        return winningnumber3;
    }

    public void setWinningnumber3(int winningnumber3) {
        this.winningnumber3 = winningnumber3;
    }

    public int getWinningnumber4() {
        return winningnumber4;
    }

    public void setWinningnumber4(int winningnumber4) {
        this.winningnumber4 = winningnumber4;
    }

    public int getWinningnumber5() {
        return winningnumber5;
    }

    public void setWinningnumber5(int winningnumber5) {
        this.winningnumber5 = winningnumber5;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    @XmlTransient
    public List<Prizecategory> getPrizecategoryList() {
        return prizecategoryList;
    }

    public void setPrizecategoryList(List<Prizecategory> prizecategoryList) {
        this.prizecategoryList = prizecategoryList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Draw other)) {
            return false;
        }
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "pojo.Draw[ id=" + id + " ]";
    }

}
