package com.doitteam.doit.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A LikesReto.
 */
@Entity
@Table(name = "likes_reto")
public class LikesReto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "puntuacion")
    private Integer puntuacion;

    @Column(name = "hora_like")
    private ZonedDateTime horaLike;

    @ManyToOne
    private User usuario;

    @ManyToOne
    private ParticipacionReto participacionReto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public LikesReto puntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
        return this;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public ZonedDateTime getHoraLike() {
        return horaLike;
    }

    public LikesReto horaLike(ZonedDateTime horaLike) {
        this.horaLike = horaLike;
        return this;
    }

    public void setHoraLike(ZonedDateTime horaLike) {
        this.horaLike = horaLike;
    }

    public User getUsuario() {
        return usuario;
    }

    public LikesReto usuario(User user) {
        this.usuario = user;
        return this;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }

    public ParticipacionReto getParticipacionReto() {
        return participacionReto;
    }

    public LikesReto participacionReto(ParticipacionReto participacionReto) {
        this.participacionReto = participacionReto;
        return this;
    }

    public void setParticipacionReto(ParticipacionReto participacionReto) {
        this.participacionReto = participacionReto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LikesReto likesReto = (LikesReto) o;
        if (likesReto.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, likesReto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LikesReto{" +
            "id=" + id +
            ", puntuacion='" + puntuacion + "'" +
            ", horaLike='" + horaLike + "'" +
            '}';
    }
}
