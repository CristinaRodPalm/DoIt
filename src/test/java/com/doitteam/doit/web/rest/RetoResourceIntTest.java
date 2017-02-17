package com.doitteam.doit.web.rest;

import com.doitteam.doit.DoitApp;

import com.doitteam.doit.domain.Reto;
import com.doitteam.doit.repository.RetoRepository;

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
 * Test class for the RetoResource REST controller.
 *
 * @see RetoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DoitApp.class)
public class RetoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_HORA_PUBLICACION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HORA_PUBLICACION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final byte[] DEFAULT_IMAGEN = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGEN_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_CONTENT_TYPE = "image/png";

    @Autowired
    private RetoRepository retoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restRetoMockMvc;

    private Reto reto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            RetoResource retoResource = new RetoResource(retoRepository);
        this.restRetoMockMvc = MockMvcBuilders.standaloneSetup(retoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reto createEntity(EntityManager em) {
        Reto reto = new Reto()
                .nombre(DEFAULT_NOMBRE)
                .descripcion(DEFAULT_DESCRIPCION)
                .horaPublicacion(DEFAULT_HORA_PUBLICACION)
                .imagen(DEFAULT_IMAGEN)
                .imagenContentType(DEFAULT_IMAGEN_CONTENT_TYPE);
        return reto;
    }

    @Before
    public void initTest() {
        reto = createEntity(em);
    }

    @Test
    @Transactional
    public void createReto() throws Exception {
        int databaseSizeBeforeCreate = retoRepository.findAll().size();

        // Create the Reto

        restRetoMockMvc.perform(post("/api/retos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reto)))
            .andExpect(status().isCreated());

        // Validate the Reto in the database
        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeCreate + 1);
        Reto testReto = retoList.get(retoList.size() - 1);
        assertThat(testReto.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testReto.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testReto.getHoraPublicacion()).isEqualTo(DEFAULT_HORA_PUBLICACION);
        assertThat(testReto.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testReto.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createRetoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = retoRepository.findAll().size();

        // Create the Reto with an existing ID
        Reto existingReto = new Reto();
        existingReto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRetoMockMvc.perform(post("/api/retos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingReto)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRetos() throws Exception {
        // Initialize the database
        retoRepository.saveAndFlush(reto);

        // Get all the retoList
        restRetoMockMvc.perform(get("/api/retos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].horaPublicacion").value(hasItem(sameInstant(DEFAULT_HORA_PUBLICACION))))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))));
    }

    @Test
    @Transactional
    public void getReto() throws Exception {
        // Initialize the database
        retoRepository.saveAndFlush(reto);

        // Get the reto
        restRetoMockMvc.perform(get("/api/retos/{id}", reto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reto.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.horaPublicacion").value(sameInstant(DEFAULT_HORA_PUBLICACION)))
            .andExpect(jsonPath("$.imagenContentType").value(DEFAULT_IMAGEN_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen").value(Base64Utils.encodeToString(DEFAULT_IMAGEN)));
    }

    @Test
    @Transactional
    public void getNonExistingReto() throws Exception {
        // Get the reto
        restRetoMockMvc.perform(get("/api/retos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReto() throws Exception {
        // Initialize the database
        retoRepository.saveAndFlush(reto);
        int databaseSizeBeforeUpdate = retoRepository.findAll().size();

        // Update the reto
        Reto updatedReto = retoRepository.findOne(reto.getId());
        updatedReto
                .nombre(UPDATED_NOMBRE)
                .descripcion(UPDATED_DESCRIPCION)
                .horaPublicacion(UPDATED_HORA_PUBLICACION)
                .imagen(UPDATED_IMAGEN)
                .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE);

        restRetoMockMvc.perform(put("/api/retos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReto)))
            .andExpect(status().isOk());

        // Validate the Reto in the database
        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeUpdate);
        Reto testReto = retoList.get(retoList.size() - 1);
        assertThat(testReto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testReto.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testReto.getHoraPublicacion()).isEqualTo(UPDATED_HORA_PUBLICACION);
        assertThat(testReto.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testReto.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingReto() throws Exception {
        int databaseSizeBeforeUpdate = retoRepository.findAll().size();

        // Create the Reto

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRetoMockMvc.perform(put("/api/retos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reto)))
            .andExpect(status().isCreated());

        // Validate the Reto in the database
        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReto() throws Exception {
        // Initialize the database
        retoRepository.saveAndFlush(reto);
        int databaseSizeBeforeDelete = retoRepository.findAll().size();

        // Get the reto
        restRetoMockMvc.perform(delete("/api/retos/{id}", reto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reto.class);
    }
}
