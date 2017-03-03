package com.doitteam.doit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Reto.
 */
@Entity
@Table(name = "reto")
public class Reto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "hora_publicacion")
    private ZonedDateTime horaPublicacion;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    @Column(name = "imagen_content_type")
    private String imagenContentType;

    @OneToMany(mappedBy = "reto")
    @JsonIgnore
    private Set<ParticipacionReto> participacionRetos = new HashSet<>();

    @OneToMany(mappedBy = "reto")
    @JsonIgnore
    private Set<Evento> eventos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Reto nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Reto descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ZonedDateTime getHoraPublicacion() {
        return horaPublicacion;
    }

    public Reto horaPublicacion(ZonedDateTime horaPublicacion) {
        this.horaPublicacion = horaPublicacion;
        return this;
    }

    public void setHoraPublicacion(ZonedDateTime horaPublicacion) {
        this.horaPublicacion = horaPublicacion;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public Reto imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public Reto imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public Set<ParticipacionReto> getParticipacionRetos() {
        return participacionRetos;
    }

    public Reto participacionRetos(Set<ParticipacionReto> participacionRetos) {
        this.participacionRetos = participacionRetos;
        return this;
    }

    public Reto addParticipacionReto(ParticipacionReto participacionReto) {
        this.participacionRetos.add(participacionReto);
        participacionReto.setReto(this);
        return this;
    }

    public Reto removeParticipacionReto(ParticipacionReto participacionReto) {
        this.participacionRetos.remove(participacionReto);
        participacionReto.setReto(null);
        return this;
    }

    public void setParticipacionRetos(Set<ParticipacionReto> participacionRetos) {
        this.participacionRetos = participacionRetos;
    }

    public Set<Evento> getEventos() {
        return eventos;
    }

    public Reto eventos(Set<Evento> eventos) {
        this.eventos = eventos;
        return this;
    }

    public Reto addEvento(Evento evento) {
        this.eventos.add(evento);
        evento.setReto(this);
        return this;
    }

    public Reto removeEvento(Evento evento) {
        this.eventos.remove(evento);
        evento.setReto(null);
        return this;
    }

    public void setEventos(Set<Evento> eventos) {
        this.eventos = eventos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reto reto = (Reto) o;
        if (reto.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, reto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Reto{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", descripcion='" + descripcion + "'" +
            ", horaPublicacion='" + horaPublicacion + "'" +
            ", imagen='" + imagen + "'" +
            ", imagenContentType='" + imagenContentType + "'" +
            '}';
    }
}
