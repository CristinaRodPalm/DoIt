package com.doitteam.doit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A ParticipacionReto.
 */
@Entity
@Table(name = "participacion_reto")
public class ParticipacionReto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    @Column(name = "imagen_content_type")
    private String imagenContentType;

    @Column(name = "hora_publicacion")
    private ZonedDateTime horaPublicacion;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne
    private User usuario;

    @ManyToOne
    private Reto reto;

    @OneToMany(mappedBy = "participacionReto")
    @JsonIgnore
    private Set<LikesReto> likesRetos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public ParticipacionReto imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public ParticipacionReto imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public ZonedDateTime getHoraPublicacion() {
        return horaPublicacion;
    }

    public ParticipacionReto horaPublicacion(ZonedDateTime horaPublicacion) {
        this.horaPublicacion = horaPublicacion;
        return this;
    }

    public void setHoraPublicacion(ZonedDateTime horaPublicacion) {
        this.horaPublicacion = horaPublicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public ParticipacionReto descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public User getUsuario() {
        return usuario;
    }

    public ParticipacionReto usuario(User user) {
        this.usuario = user;
        return this;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }

    public Reto getReto() {
        return reto;
    }

    public ParticipacionReto reto(Reto reto) {
        this.reto = reto;
        return this;
    }

    public void setReto(Reto reto) {
        this.reto = reto;
    }

    public Set<LikesReto> getLikesRetos() {
        return likesRetos;
    }

    public ParticipacionReto likesRetos(Set<LikesReto> likesRetos) {
        this.likesRetos = likesRetos;
        return this;
    }

    public ParticipacionReto addLikesReto(LikesReto likesReto) {
        this.likesRetos.add(likesReto);
        likesReto.setParticipacionReto(this);
        return this;
    }

    public ParticipacionReto removeLikesReto(LikesReto likesReto) {
        this.likesRetos.remove(likesReto);
        likesReto.setParticipacionReto(null);
        return this;
    }

    public void setLikesRetos(Set<LikesReto> likesRetos) {
        this.likesRetos = likesRetos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ParticipacionReto participacionReto = (ParticipacionReto) o;
        if (participacionReto.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, participacionReto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ParticipacionReto{" +
            "id=" + id +
            ", imagen='" + imagen + "'" +
            ", imagenContentType='" + imagenContentType + "'" +
            ", horaPublicacion='" + horaPublicacion + "'" +
            ", descripcion='" + descripcion + "'" +
            '}';
    }
}
