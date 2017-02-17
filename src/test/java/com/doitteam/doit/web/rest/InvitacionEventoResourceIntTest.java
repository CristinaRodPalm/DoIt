package com.doitteam.doit.web.rest;

import com.doitteam.doit.DoitApp;

import com.doitteam.doit.domain.InvitacionEvento;
import com.doitteam.doit.repository.InvitacionEventoRepository;

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
 * Test class for the InvitacionEventoResource REST controller.
 *
 * @see InvitacionEventoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DoitApp.class)
public class InvitacionEventoResourceIntTest {

    private static final ZonedDateTime DEFAULT_HORA_RESOLUCION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HORA_RESOLUCION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_HORA_INVITACION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HORA_INVITACION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_RESOLUCION = false;
    private static final Boolean UPDATED_RESOLUCION = true;

    @Autowired
    private InvitacionEventoRepository invitacionEventoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restInvitacionEventoMockMvc;

    private InvitacionEvento invitacionEvento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            InvitacionEventoResource invitacionEventoResource = new InvitacionEventoResource(invitacionEventoRepository);
        this.restInvitacionEventoMockMvc = MockMvcBuilders.standaloneSetup(invitacionEventoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvitacionEvento createEntity(EntityManager em) {
        InvitacionEvento invitacionEvento = new InvitacionEvento()
                .horaResolucion(DEFAULT_HORA_RESOLUCION)
                .horaInvitacion(DEFAULT_HORA_INVITACION)
                .resolucion(DEFAULT_RESOLUCION);
        return invitacionEvento;
    }

    @Before
    public void initTest() {
        invitacionEvento = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvitacionEvento() throws Exception {
        int databaseSizeBeforeCreate = invitacionEventoRepository.findAll().size();

        // Create the InvitacionEvento

        restInvitacionEventoMockMvc.perform(post("/api/invitacion-eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invitacionEvento)))
            .andExpect(status().isCreated());

        // Validate the InvitacionEvento in the database
        List<InvitacionEvento> invitacionEventoList = invitacionEventoRepository.findAll();
        assertThat(invitacionEventoList).hasSize(databaseSizeBeforeCreate + 1);
        InvitacionEvento testInvitacionEvento = invitacionEventoList.get(invitacionEventoList.size() - 1);
        assertThat(testInvitacionEvento.getHoraResolucion()).isEqualTo(DEFAULT_HORA_RESOLUCION);
        assertThat(testInvitacionEvento.getHoraInvitacion()).isEqualTo(DEFAULT_HORA_INVITACION);
        assertThat(testInvitacionEvento.isResolucion()).isEqualTo(DEFAULT_RESOLUCION);
    }

    @Test
    @Transactional
    public void createInvitacionEventoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invitacionEventoRepository.findAll().size();

        // Create the InvitacionEvento with an existing ID
        InvitacionEvento existingInvitacionEvento = new InvitacionEvento();
        existingInvitacionEvento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvitacionEventoMockMvc.perform(post("/api/invitacion-eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingInvitacionEvento)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<InvitacionEvento> invitacionEventoList = invitacionEventoRepository.findAll();
        assertThat(invitacionEventoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInvitacionEventos() throws Exception {
        // Initialize the database
        invitacionEventoRepository.saveAndFlush(invitacionEvento);

        // Get all the invitacionEventoList
        restInvitacionEventoMockMvc.perform(get("/api/invitacion-eventos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invitacionEvento.getId().intValue())))
            .andExpect(jsonPath("$.[*].horaResolucion").value(hasItem(sameInstant(DEFAULT_HORA_RESOLUCION))))
            .andExpect(jsonPath("$.[*].horaInvitacion").value(hasItem(sameInstant(DEFAULT_HORA_INVITACION))))
            .andExpect(jsonPath("$.[*].resolucion").value(hasItem(DEFAULT_RESOLUCION.booleanValue())));
    }

    @Test
    @Transactional
    public void getInvitacionEvento() throws Exception {
        // Initialize the database
        invitacionEventoRepository.saveAndFlush(invitacionEvento);

        // Get the invitacionEvento
        restInvitacionEventoMockMvc.perform(get("/api/invitacion-eventos/{id}", invitacionEvento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(invitacionEvento.getId().intValue()))
            .andExpect(jsonPath("$.horaResolucion").value(sameInstant(DEFAULT_HORA_RESOLUCION)))
            .andExpect(jsonPath("$.horaInvitacion").value(sameInstant(DEFAULT_HORA_INVITACION)))
            .andExpect(jsonPath("$.resolucion").value(DEFAULT_RESOLUCION.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInvitacionEvento() throws Exception {
        // Get the invitacionEvento
        restInvitacionEventoMockMvc.perform(get("/api/invitacion-eventos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvitacionEvento() throws Exception {
        // Initialize the database
        invitacionEventoRepository.saveAndFlush(invitacionEvento);
        int databaseSizeBeforeUpdate = invitacionEventoRepository.findAll().size();

        // Update the invitacionEvento
        InvitacionEvento updatedInvitacionEvento = invitacionEventoRepository.findOne(invitacionEvento.getId());
        updatedInvitacionEvento
                .horaResolucion(UPDATED_HORA_RESOLUCION)
                .horaInvitacion(UPDATED_HORA_INVITACION)
                .resolucion(UPDATED_RESOLUCION);

        restInvitacionEventoMockMvc.perform(put("/api/invitacion-eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInvitacionEvento)))
            .andExpect(status().isOk());

        // Validate the InvitacionEvento in the database
        List<InvitacionEvento> invitacionEventoList = invitacionEventoRepository.findAll();
        assertThat(invitacionEventoList).hasSize(databaseSizeBeforeUpdate);
        InvitacionEvento testInvitacionEvento = invitacionEventoList.get(invitacionEventoList.size() - 1);
        assertThat(testInvitacionEvento.getHoraResolucion()).isEqualTo(UPDATED_HORA_RESOLUCION);
        assertThat(testInvitacionEvento.getHoraInvitacion()).isEqualTo(UPDATED_HORA_INVITACION);
        assertThat(testInvitacionEvento.isResolucion()).isEqualTo(UPDATED_RESOLUCION);
    }

    @Test
    @Transactional
    public void updateNonExistingInvitacionEvento() throws Exception {
        int databaseSizeBeforeUpdate = invitacionEventoRepository.findAll().size();

        // Create the InvitacionEvento

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInvitacionEventoMockMvc.perform(put("/api/invitacion-eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invitacionEvento)))
            .andExpect(status().isCreated());

        // Validate the InvitacionEvento in the database
        List<InvitacionEvento> invitacionEventoList = invitacionEventoRepository.findAll();
        assertThat(invitacionEventoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInvitacionEvento() throws Exception {
        // Initialize the database
        invitacionEventoRepository.saveAndFlush(invitacionEvento);
        int databaseSizeBeforeDelete = invitacionEventoRepository.findAll().size();

        // Get the invitacionEvento
        restInvitacionEventoMockMvc.perform(delete("/api/invitacion-eventos/{id}", invitacionEvento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InvitacionEvento> invitacionEventoList = invitacionEventoRepository.findAll();
        assertThat(invitacionEventoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvitacionEvento.class);
    }
}
