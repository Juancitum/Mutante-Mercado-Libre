/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juancitum
 */
@Entity
@Table(name = "almacenocadenaadn")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Almacenocadenaadn.findAll", query = "SELECT a FROM Almacenocadenaadn a"),
    @NamedQuery(name = "Almacenocadenaadn.findById", query = "SELECT a FROM Almacenocadenaadn a WHERE a.id = :id"),
    @NamedQuery(name = "Almacenocadenaadn.findByMutante", query = "SELECT a FROM Almacenocadenaadn a WHERE a.mutante = :mutante"),
    @NamedQuery(name = "Almacenocadenaadn.findByAdn", query = "SELECT a FROM Almacenocadenaadn a WHERE a.adn = :adn")})
public class Almacenocadenaadn implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "mutante")
    private Boolean mutante;
    @Size(max = 250)
    @Column(name = "adn")
    private String adn;

    public Almacenocadenaadn() {
    }

    public Almacenocadenaadn(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getMutante() {
        return mutante;
    }

    public void setMutante(Boolean mutante) {
        this.mutante = mutante;
    }

    public String getAdn() {
        return adn;
    }

    public void setAdn(String adn) {
        this.adn = adn;
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
        if (!(object instanceof Almacenocadenaadn)) {
            return false;
        }
        Almacenocadenaadn other = (Almacenocadenaadn) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Almacenocadenaadn[ id=" + id + " ]";
    }
    
}
