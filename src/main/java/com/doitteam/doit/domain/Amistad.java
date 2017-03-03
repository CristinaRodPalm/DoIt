package com.doitteam.doit.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Amistad.
 */
@Entity
@Table(name = "amistad")
public class Amistad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "time_stamp", nullable = false)
    private ZonedDateTime timeStamp;

    @Column(name = "aceptada")
    private Boolean aceptada;

    @Column(name = "hora_respuesta")
    private ZonedDateTime horaRespuesta;

    @ManyToOne(optional = false)
    @NotNull
    private User emisor;

    @ManyToOne(optional = false)
    @NotNull
    private User receptor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }

    public Amistad timeStamp(ZonedDateTime timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public void setTimeStamp(ZonedDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Boolean isAceptada() {
        return aceptada;
    }

    public Amistad aceptada(Boolean aceptada) {
        this.aceptada = aceptada;
        return this;
    }

    public void setAceptada(Boolean aceptada) {
        this.aceptada = aceptada;
    }

    public ZonedDateTime getHoraRespuesta() {
        return horaRespuesta;
    }

    public Amistad horaRespuesta(ZonedDateTime horaRespuesta) {
        this.horaRespuesta = horaRespuesta;
        return this;
    }

    public void setHoraRespuesta(ZonedDateTime horaRespuesta) {
        this.horaRespuesta = horaRespuesta;
    }

    public User getEmisor() {
        return emisor;
    }

    public Amistad emisor(User user) {
        this.emisor = user;
        return this;
    }

    public void setEmisor(User user) {
        this.emisor = user;
    }

    public User getReceptor() {
        return receptor;
    }

    public Amistad receptor(User user) {
        this.receptor = user;
        return this;
    }

    public void setReceptor(User user) {
        this.receptor = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Amistad amistad = (Amistad) o;
        if (amistad.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, amistad.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Amistad{" +
            "id=" + id +
            ", timeStamp='" + timeStamp + "'" +
            ", aceptada='" + aceptada + "'" +
            ", horaRespuesta='" + horaRespuesta + "'" +
            '}';
    }
}
