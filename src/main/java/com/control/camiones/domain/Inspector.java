package com.control.camiones.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Inspector.
 */
@Entity
@Table(name = "inspector")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Inspector implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "fotopatente")
    private byte[] fotopatente;

    @Column(name = "fotopatente_content_type")
    private String fotopatenteContentType;

    @Lob
    @Column(name = "fotocamion")
    private byte[] fotocamion;

    @Column(name = "fotocamion_content_type")
    private String fotocamionContentType;

    @Column(name = "vehiculomodelo")
    private String vehiculomodelo;

    @Column(name = "infoadicional")
    private String infoadicional;

    @Column(name = "celular")
    private Integer celular;

    @Column(name = "cuit")
    private String cuit;

    @Column(name = "patente")
    private String patente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFotopatente() {
        return fotopatente;
    }

    public Inspector fotopatente(byte[] fotopatente) {
        this.fotopatente = fotopatente;
        return this;
    }

    public void setFotopatente(byte[] fotopatente) {
        this.fotopatente = fotopatente;
    }

    public String getFotopatenteContentType() {
        return fotopatenteContentType;
    }

    public Inspector fotopatenteContentType(String fotopatenteContentType) {
        this.fotopatenteContentType = fotopatenteContentType;
        return this;
    }

    public void setFotopatenteContentType(String fotopatenteContentType) {
        this.fotopatenteContentType = fotopatenteContentType;
    }

    public byte[] getFotocamion() {
        return fotocamion;
    }

    public Inspector fotocamion(byte[] fotocamion) {
        this.fotocamion = fotocamion;
        return this;
    }

    public void setFotocamion(byte[] fotocamion) {
        this.fotocamion = fotocamion;
    }

    public String getFotocamionContentType() {
        return fotocamionContentType;
    }

    public Inspector fotocamionContentType(String fotocamionContentType) {
        this.fotocamionContentType = fotocamionContentType;
        return this;
    }

    public void setFotocamionContentType(String fotocamionContentType) {
        this.fotocamionContentType = fotocamionContentType;
    }

    public String getVehiculomodelo() {
        return vehiculomodelo;
    }

    public Inspector vehiculomodelo(String vehiculomodelo) {
        this.vehiculomodelo = vehiculomodelo;
        return this;
    }

    public void setVehiculomodelo(String vehiculomodelo) {
        this.vehiculomodelo = vehiculomodelo;
    }

    public String getInfoadicional() {
        return infoadicional;
    }

    public Inspector infoadicional(String infoadicional) {
        this.infoadicional = infoadicional;
        return this;
    }

    public void setInfoadicional(String infoadicional) {
        this.infoadicional = infoadicional;
    }

    public Integer getCelular() {
        return celular;
    }

    public Inspector celular(Integer celular) {
        this.celular = celular;
        return this;
    }

    public void setCelular(Integer celular) {
        this.celular = celular;
    }

    public String getCuit() {
        return cuit;
    }

    public Inspector cuit(String cuit) {
        this.cuit = cuit;
        return this;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getPatente() {
        return patente;
    }

    public Inspector patente(String patente) {
        this.patente = patente;
        return this;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inspector)) {
            return false;
        }
        return id != null && id.equals(((Inspector) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Inspector{" +
            "id=" + getId() +
            ", fotopatente='" + getFotopatente() + "'" +
            ", fotopatenteContentType='" + getFotopatenteContentType() + "'" +
            ", fotocamion='" + getFotocamion() + "'" +
            ", fotocamionContentType='" + getFotocamionContentType() + "'" +
            ", vehiculomodelo='" + getVehiculomodelo() + "'" +
            ", infoadicional='" + getInfoadicional() + "'" +
            ", celular=" + getCelular() +
            ", cuit='" + getCuit() + "'" +
            ", patente='" + getPatente() + "'" +
            "}";
    }

 
}
