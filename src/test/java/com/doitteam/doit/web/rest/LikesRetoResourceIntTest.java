package com.doitteam.doit.web.rest;

import com.doitteam.doit.DoitApp;

import com.doitteam.doit.domain.LikesReto;
import com.doitteam.doit.repository.LikesRetoRepository;

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
 * Test class for the LikesRetoResource REST controller.
 *
 * @see LikesRetoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DoitApp.class)
public class LikesRetoResourceIntTest {

    private static final Integer DEFAULT_PUNTUACION = 1;
    private static final Integer UPDATED_PUNTUACION = 2;

    private static final ZonedDateTime DEFAULT_HORA_LIKE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HORA_LIKE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private LikesRetoRepository likesRetoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restLikesRetoMockMvc;

    private LikesReto likesReto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            LikesRetoResource likesRetoResource = new LikesRetoResource(likesRetoRepository);
        this.restLikesRetoMockMvc = MockMvcBuilders.standaloneSetup(likesRetoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LikesReto createEntity(EntityManager em) {
        LikesReto likesReto = new LikesReto()
                .puntuacion(DEFAULT_PUNTUACION)
                .horaLike(DEFAULT_HORA_LIKE);
        return likesReto;
    }

    @Before
    public void initTest() {
        likesReto = createEntity(em);
    }

    @Test
    @Transactional
    public void createLikesReto() throws Exception {
        int databaseSizeBeforeCreate = likesRetoRepository.findAll().size();

        // Create the LikesReto

        restLikesRetoMockMvc.perform(post("/api/likes-retos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(likesReto)))
            .andExpect(status().isCreated());

        // Validate the LikesReto in the database
        List<LikesReto> likesRetoList = likesRetoRepository.findAll();
        assertThat(likesRetoList).hasSize(databaseSizeBeforeCreate + 1);
        LikesReto testLikesReto = likesRetoList.get(likesRetoList.size() - 1);
        assertThat(testLikesReto.getPuntuacion()).isEqualTo(DEFAULT_PUNTUACION);
        assertThat(testLikesReto.getHoraLike()).isEqualTo(DEFAULT_HORA_LIKE);
    }

    @Test
    @Transactional
    public void createLikesRetoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = likesRetoRepository.findAll().size();

        // Create the LikesReto with an existing ID
        LikesReto existingLikesReto = new LikesReto();
        existingLikesReto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLikesRetoMockMvc.perform(post("/api/likes-retos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLikesReto)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LikesReto> likesRetoList = likesRetoRepository.findAll();
        assertThat(likesRetoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLikesRetos() throws Exception {
        // Initialize the database
        likesRetoRepository.saveAndFlush(likesReto);

        // Get all the likesRetoList
        restLikesRetoMockMvc.perform(get("/api/likes-retos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(likesReto.getId().intValue())))
            .andExpect(jsonPath("$.[*].puntuacion").value(hasItem(DEFAULT_PUNTUACION)))
            .andExpect(jsonPath("$.[*].horaLike").value(hasItem(sameInstant(DEFAULT_HORA_LIKE))));
    }

    @Test
    @Transactional
    public void getLikesReto() throws Exception {
        // Initialize the database
        likesRetoRepository.saveAndFlush(likesReto);

        // Get the likesReto
        restLikesRetoMockMvc.perform(get("/api/likes-retos/{id}", likesReto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(likesReto.getId().intValue()))
            .andExpect(jsonPath("$.puntuacion").value(DEFAULT_PUNTUACION))
            .andExpect(jsonPath("$.horaLike").value(sameInstant(DEFAULT_HORA_LIKE)));
    }

    @Test
    @Transactional
    public void getNonExistingLikesReto() throws Exception {
        // Get the likesReto
        restLikesRetoMockMvc.perform(get("/api/likes-retos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLikesReto() throws Exception {
        // Initialize the database
        likesRetoRepository.saveAndFlush(likesReto);
        int databaseSizeBeforeUpdate = likesRetoRepository.findAll().size();

        // Update the likesReto
        LikesReto updatedLikesReto = likesRetoRepository.findOne(likesReto.getId());
        updatedLikesReto
                .puntuacion(UPDATED_PUNTUACION)
                .horaLike(UPDATED_HORA_LIKE);

        restLikesRetoMockMvc.perform(put("/api/likes-retos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLikesReto)))
            .andExpect(status().isOk());

        // Validate the LikesReto in the database
        List<LikesReto> likesRetoList = likesRetoRepository.findAll();
        assertThat(likesRetoList).hasSize(databaseSizeBeforeUpdate);
        LikesReto testLikesReto = likesRetoList.get(likesRetoList.size() - 1);
        assertThat(testLikesReto.getPuntuacion()).isEqualTo(UPDATED_PUNTUACION);
        assertThat(testLikesReto.getHoraLike()).isEqualTo(UPDATED_HORA_LIKE);
    }

    @Test
    @Transactional
    public void updateNonExistingLikesReto() throws Exception {
        int databaseSizeBeforeUpdate = likesRetoRepository.findAll().size();

        // Create the LikesReto

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLikesRetoMockMvc.perform(put("/api/likes-retos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(likesReto)))
            .andExpect(status().isCreated());

        // Validate the LikesReto in the database
        List<LikesReto> likesRetoList = likesRetoRepository.findAll();
        assertThat(likesRetoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLikesReto() throws Exception {
        // Initialize the database
        likesRetoRepository.saveAndFlush(likesReto);
        int databaseSizeBeforeDelete = likesRetoRepository.findAll().size();

        // Get the likesReto
        restLikesRetoMockMvc.perform(delete("/api/likes-retos/{id}", likesReto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LikesReto> likesRetoList = likesRetoRepository.findAll();
        assertThat(likesRetoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LikesReto.class);
    }
}
