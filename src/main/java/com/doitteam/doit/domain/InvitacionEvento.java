package com.doitteam.doit.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A InvitacionEvento.
 */
@Entity
@Table(name = "invitacion_evento")
public class InvitacionEvento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hora_resolucion")
    private ZonedDateTime horaResolucion;

    @Column(name = "hora_invitacion")
    private ZonedDateTime horaInvitacion;

    @Column(name = "resolucion")
    private Boolean resolucion;

    @ManyToOne
    private Evento evento;

    @ManyToOne
    private User miembroEvento;

    @ManyToOne(optional = false)
    @NotNull
    private User invitado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getHoraResolucion() {
        return horaResolucion;
    }

    public InvitacionEvento horaResolucion(ZonedDateTime horaResolucion) {
        this.horaResolucion = horaResolucion;
        return this;
    }

    public void setHoraResolucion(ZonedDateTime horaResolucion) {
        this.horaResolucion = horaResolucion;
    }

    public ZonedDateTime getHoraInvitacion() {
        return horaInvitacion;
    }

    public InvitacionEvento horaInvitacion(ZonedDateTime horaInvitacion) {
        this.horaInvitacion = horaInvitacion;
        return this;
    }

    public void setHoraInvitacion(ZonedDateTime horaInvitacion) {
        this.horaInvitacion = horaInvitacion;
    }

    public Boolean isResolucion() {
        return resolucion;
    }

    public InvitacionEvento resolucion(Boolean resolucion) {
        this.resolucion = resolucion;
        return this;
    }

    public void setResolucion(Boolean resolucion) {
        this.resolucion = resolucion;
    }

    public Evento getEvento() {
        return evento;
    }

    public InvitacionEvento evento(Evento evento) {
        this.evento = evento;
        return this;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public User getMiembroEvento() {
        return miembroEvento;
    }

    public InvitacionEvento miembroEvento(User user) {
        this.miembroEvento = user;
        return this;
    }

    public void setMiembroEvento(User user) {
        this.miembroEvento = user;
    }

    public User getInvitado() {
        return invitado;
    }

    public InvitacionEvento invitado(User user) {
        this.invitado = user;
        return this;
    }

    public void setInvitado(User user) {
        this.invitado = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InvitacionEvento invitacionEvento = (InvitacionEvento) o;
        if (invitacionEvento.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, invitacionEvento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InvitacionEvento{" +
            "id=" + id +
            ", horaResolucion='" + horaResolucion + "'" +
            ", horaInvitacion='" + horaInvitacion + "'" +
            ", resolucion='" + resolucion + "'" +
            '}';
    }
}
