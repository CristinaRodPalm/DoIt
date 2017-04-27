package com.doitteam.doit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Evento.
 */
@Entity
@Table(name = "evento")
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "hora")
    private ZonedDateTime hora;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    @Column(name = "imagen_content_type")
    private String imagenContentType;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "latitud")
    private Double latitud;

    @Column(name = "longitud")
    private Double longitud;

    @Column(name = "fecha_evento")
    private ZonedDateTime fechaEvento;

    @ManyToOne
    private Reto reto;

    @ManyToOne
    private User admin;

    @OneToMany(mappedBy = "evento")
    @JsonIgnore
    private Set<InvitacionEvento> miembroEventos = new HashSet<>();

    @OneToMany(mappedBy = "evento")
    @JsonIgnore
    private Set<Chat> chats = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Evento nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Evento descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ZonedDateTime getHora() {
        return hora;
    }

    public Evento hora(ZonedDateTime hora) {
        this.hora = hora;
        return this;
    }

    public void setHora(ZonedDateTime hora) {
        this.hora = hora;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public Evento imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public Evento imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public String getDireccion() {
        return direccion;
    }

    public Evento direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Double getLatitud() {
        return latitud;
    }

    public Evento latitud(Double latitud) {
        this.latitud = latitud;
        return this;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public Evento longitud(Double longitud) {
        this.longitud = longitud;
        return this;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public ZonedDateTime getFechaEvento() {
        return fechaEvento;
    }

    public Evento fechaEvento(ZonedDateTime fechaEvento) {
        this.fechaEvento = fechaEvento;
        return this;
    }

    public void setFechaEvento(ZonedDateTime fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public Reto getReto() {
        return reto;
    }

    public Evento reto(Reto reto) {
        this.reto = reto;
        return this;
    }

    public void setReto(Reto reto) {
        this.reto = reto;
    }

    public User getAdmin() {
        return admin;
    }

    public Evento admin(User user) {
        this.admin = user;
        return this;
    }

    public void setAdmin(User user) {
        this.admin = user;
    }

    public Set<InvitacionEvento> getMiembroEventos() {
        return miembroEventos;
    }

    public Evento miembroEventos(Set<InvitacionEvento> invitacionEventos) {
        this.miembroEventos = invitacionEventos;
        return this;
    }

    public Evento addMiembroEvento(InvitacionEvento invitacionEvento) {
        this.miembroEventos.add(invitacionEvento);
        invitacionEvento.setEvento(this);
        return this;
    }

    public Evento removeMiembroEvento(InvitacionEvento invitacionEvento) {
        this.miembroEventos.remove(invitacionEvento);
        invitacionEvento.setEvento(null);
        return this;
    }

    public void setMiembroEventos(Set<InvitacionEvento> invitacionEventos) {
        this.miembroEventos = invitacionEventos;
    }

    public Set<Chat> getChats() {
        return chats;
    }

    public Evento chats(Set<Chat> chats) {
        this.chats = chats;
        return this;
    }

    public Evento addChat(Chat chat) {
        this.chats.add(chat);
        chat.setEvento(this);
        return this;
    }

    public Evento removeChat(Chat chat) {
        this.chats.remove(chat);
        chat.setEvento(null);
        return this;
    }

    public void setChats(Set<Chat> chats) {
        this.chats = chats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Evento evento = (Evento) o;
        if (evento.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, evento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Evento{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", descripcion='" + descripcion + "'" +
            ", hora='" + hora + "'" +
            ", imagen='" + imagen + "'" +
            ", imagenContentType='" + imagenContentType + "'" +
            ", direccion='" + direccion + "'" +
            ", latitud='" + latitud + "'" +
            ", longitud='" + longitud + "'" +
            ", fechaEvento='" + fechaEvento + "'" +
            '}';
    }
}
