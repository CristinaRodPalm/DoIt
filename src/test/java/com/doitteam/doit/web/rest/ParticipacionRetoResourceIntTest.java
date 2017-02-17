package com.doitteam.doit.web.rest;

import com.doitteam.doit.DoitApp;

import com.doitteam.doit.domain.ParticipacionReto;
import com.doitteam.doit.repository.ParticipacionRetoRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.doitteam.doit.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ParticipacionRetoResource REST controller.
 *
 * @see ParticipacionRetoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DoitApp.class)
public class ParticipacionRetoResourceIntTest {

    private static final byte[] DEFAULT_IMAGEN = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGEN_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_CONTENT_TYPE = "image/png";

    private static final ZonedDateTime DEFAULT_HORA_PUBLICACION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HORA_PUBLICACION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private ParticipacionRetoRepository participacionRetoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restParticipacionRetoMockMvc;

    private ParticipacionReto participacionReto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            ParticipacionRetoResource participacionRetoResource = new ParticipacionRetoResource(participacionRetoRepository);
        this.restParticipacionRetoMockMvc = MockMvcBuilders.standaloneSetup(participacionRetoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParticipacionReto createEntity(EntityManager em) {
        ParticipacionReto participacionReto = new ParticipacionReto()
                .imagen(DEFAULT_IMAGEN)
                .imagenContentType(DEFAULT_IMAGEN_CONTENT_TYPE)
                .horaPublicacion(DEFAULT_HORA_PUBLICACION)
                .descripcion(DEFAULT_DESCRIPCION);
        return participacionReto;
    }

    @Before
    public void initTest() {
        participacionReto = createEntity(em);
    }

    @Test
    @Transactional
    public void createParticipacionReto() throws Exception {
        int databaseSizeBeforeCreate = participacionRetoRepository.findAll().size();

        // Create the ParticipacionReto

        restParticipacionRetoMockMvc.perform(post("/api/participacion-retos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(participacionReto)))
            .andExpect(status().isCreated());

        // Validate the ParticipacionReto in the database
        List<ParticipacionReto> participacionRetoList = participacionRetoRepository.findAll();
        assertThat(participacionRetoList).hasSize(databaseSizeBeforeCreate + 1);
        ParticipacionReto testParticipacionReto = participacionRetoList.get(participacionRetoList.size() - 1);
        assertThat(testParticipacionReto.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testParticipacionReto.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);
        assertThat(testParticipacionReto.getHoraPublicacion()).isEqualTo(DEFAULT_HORA_PUBLICACION);
        assertThat(testParticipacionReto.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createParticipacionRetoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = participacionRetoRepository.findAll().size();

        // Create the ParticipacionReto with an existing ID
        ParticipacionReto existingParticipacionReto = new ParticipacionReto();
        existingParticipacionReto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParticipacionRetoMockMvc.perform(post("/api/participacion-retos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingParticipacionReto)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ParticipacionReto> participacionRetoList = participacionRetoRepository.findAll();
        assertThat(participacionRetoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllParticipacionRetos() throws Exception {
        // Initialize the database
        participacionRetoRepository.saveAndFlush(participacionReto);

        // Get all the participacionRetoList
        restParticipacionRetoMockMvc.perform(get("/api/participacion-retos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(participacionReto.getId().intValue())))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))))
            .andExpect(jsonPath("$.[*].horaPublicacion").value(hasItem(sameInstant(DEFAULT_HORA_PUBLICACION))))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    public void getParticipacionReto() throws Exception {
        // Initialize the database
        participacionRetoRepository.saveAndFlush(participacionReto);

        // Get the participacionReto
        restParticipacionRetoMockMvc.perform(get("/api/participacion-retos/{id}", participacionReto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(participacionReto.getId().intValue()))
            .andExpect(jsonPath("$.imagenContentType").value(DEFAULT_IMAGEN_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen").value(Base64Utils.encodeToString(DEFAULT_IMAGEN)))
            .andExpect(jsonPath("$.horaPublicacion").value(sameInstant(DEFAULT_HORA_PUBLICACION)))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingParticipacionReto() throws Exception {
        // Get the participacionReto
        restParticipacionRetoMockMvc.perform(get("/api/participacion-retos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParticipacionReto() throws Exception {
        // Initialize the database
        participacionRetoRepository.saveAndFlush(participacionReto);
        int databaseSizeBeforeUpdate = participacionRetoRepository.findAll().size();

        // Update the participacionReto
        ParticipacionReto updatedParticipacionReto = participacionRetoRepository.findOne(participacionReto.getId());
        updatedParticipacionReto
                .imagen(UPDATED_IMAGEN)
                .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE)
                .horaPublicacion(UPDATED_HORA_PUBLICACION)
                .descripcion(UPDATED_DESCRIPCION);

        restParticipacionRetoMockMvc.perform(put("/api/participacion-retos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedParticipacionReto)))
            .andExpect(status().isOk());

        // Validate the ParticipacionReto in the database
        List<ParticipacionReto> participacionRetoList = participacionRetoRepository.findAll();
        assertThat(participacionRetoList).hasSize(databaseSizeBeforeUpdate);
        ParticipacionReto testParticipacionReto = participacionRetoList.get(participacionRetoList.size() - 1);
        assertThat(testParticipacionReto.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testParticipacionReto.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
        assertThat(testParticipacionReto.getHoraPublicacion()).isEqualTo(UPDATED_HORA_PUBLICACION);
        assertThat(testParticipacionReto.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingParticipacionReto() throws Exception {
        int databaseSizeBeforeUpdate = participacionRetoRepository.findAll().size();

        // Create the ParticipacionReto

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restParticipacionRetoMockMvc.perform(put("/api/participacion-retos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(participacionReto)))
            .andExpect(status().isCreated());

        // Validate the ParticipacionReto in the database
        List<ParticipacionReto> participacionRetoList = participacionRetoRepository.findAll();
        assertThat(participacionRetoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteParticipacionReto() throws Exception {
        // Initialize the database
        participacionRetoRepository.saveAndFlush(participacionReto);
        int databaseSizeBeforeDelete = participacionRetoRepository.findAll().size();

        // Get the participacionReto
        restParticipacionRetoMockMvc.perform(delete("/api/participacion-retos/{id}", participacionReto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ParticipacionReto> participacionRetoList = participacionRetoRepository.findAll();
        assertThat(participacionRetoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParticipacionReto.class);
    }
}
