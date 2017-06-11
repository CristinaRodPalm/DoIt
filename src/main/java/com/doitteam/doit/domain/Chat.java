package com.doitteam.doit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Chat.
 */
@Entity
@Table(name = "chat")
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "hora_creacion")
    private ZonedDateTime horaCreacion;

    @Column(name = "emisor")
    private Long emisor;

    @Column(name = "receptor")
    private Long receptor;

    @ManyToOne
    private Evento evento;

    @OneToMany(mappedBy = "chat")
    @JsonIgnore
    private Set<Mensaje> mensajes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Chat nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ZonedDateTime getHoraCreacion() {
        return horaCreacion;
    }

    public Chat horaCreacion(ZonedDateTime horaCreacion) {
        this.horaCreacion = horaCreacion;
        return this;
    }

    public void setHoraCreacion(ZonedDateTime horaCreacion) {
        this.horaCreacion = horaCreacion;
    }

    public Long getEmisor() {
        return emisor;
    }

    public Chat emisor(Long emisor) {
        this.emisor = emisor;
        return this;
    }

    public void setEmisor(Long emisor) {
        this.emisor = emisor;
    }

    public Long getReceptor() {
        return receptor;
    }

    public Chat receptor(Long receptor) {
        this.receptor = receptor;
        return this;
    }

    public void setReceptor(Long receptor) {
        this.receptor = receptor;
    }

    public Evento getEvento() {
        return evento;
    }

    public Chat evento(Evento evento) {
        this.evento = evento;
        return this;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Set<Mensaje> getMensajes() {
        return mensajes;
    }

    public Chat mensajes(Set<Mensaje> mensajes) {
        this.mensajes = mensajes;
        return this;
    }

    public Chat addMensaje(Mensaje mensaje) {
        this.mensajes.add(mensaje);
        mensaje.setChat(this);
        return this;
    }

    public Chat removeMensaje(Mensaje mensaje) {
        this.mensajes.remove(mensaje);
        mensaje.setChat(null);
        return this;
    }

    public void setMensajes(Set<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Chat chat = (Chat) o;
        if (chat.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, chat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Chat{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", horaCreacion='" + horaCreacion + "'" +
            ", emisor='" + emisor + "'" +
            ", receptor='" + receptor + "'" +
            '}';
    }
}
