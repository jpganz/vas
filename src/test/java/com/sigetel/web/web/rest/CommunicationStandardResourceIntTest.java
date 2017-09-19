package com.sigetel.web.web.rest;

import com.sigetel.web.OvasApp;

import com.sigetel.web.domain.CommunicationStandard;
import com.sigetel.web.repository.CommunicationStandardRepository;
import com.sigetel.web.service.CommunicationStandardService;
import com.sigetel.web.web.rest.errors.ExceptionTranslator;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CommunicationStandardResource REST controller.
 *
 * @see CommunicationStandardResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OvasApp.class)
public class CommunicationStandardResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CommunicationStandardRepository communicationStandardRepository;

    @Autowired
    private CommunicationStandardService communicationStandardService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommunicationStandardMockMvc;

    private CommunicationStandard communicationStandard;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CommunicationStandardResource communicationStandardResource = new CommunicationStandardResource(communicationStandardService);
        this.restCommunicationStandardMockMvc = MockMvcBuilders.standaloneSetup(communicationStandardResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommunicationStandard createEntity(EntityManager em) {
        CommunicationStandard communicationStandard = new CommunicationStandard()
            .name(DEFAULT_NAME);
        return communicationStandard;
    }

    @Before
    public void initTest() {
        communicationStandard = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommunicationStandard() throws Exception {
        int databaseSizeBeforeCreate = communicationStandardRepository.findAll().size();

        // Create the CommunicationStandard
        restCommunicationStandardMockMvc.perform(post("/api/communication-standards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communicationStandard)))
            .andExpect(status().isCreated());

        // Validate the CommunicationStandard in the database
        List<CommunicationStandard> communicationStandardList = communicationStandardRepository.findAll();
        assertThat(communicationStandardList).hasSize(databaseSizeBeforeCreate + 1);
        CommunicationStandard testCommunicationStandard = communicationStandardList.get(communicationStandardList.size() - 1);
        assertThat(testCommunicationStandard.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCommunicationStandardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = communicationStandardRepository.findAll().size();

        // Create the CommunicationStandard with an existing ID
        communicationStandard.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommunicationStandardMockMvc.perform(post("/api/communication-standards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communicationStandard)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CommunicationStandard> communicationStandardList = communicationStandardRepository.findAll();
        assertThat(communicationStandardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = communicationStandardRepository.findAll().size();
        // set the field null
        communicationStandard.setName(null);

        // Create the CommunicationStandard, which fails.

        restCommunicationStandardMockMvc.perform(post("/api/communication-standards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communicationStandard)))
            .andExpect(status().isBadRequest());

        List<CommunicationStandard> communicationStandardList = communicationStandardRepository.findAll();
        assertThat(communicationStandardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommunicationStandards() throws Exception {
        // Initialize the database
        communicationStandardRepository.saveAndFlush(communicationStandard);

        // Get all the communicationStandardList
        restCommunicationStandardMockMvc.perform(get("/api/communication-standards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(communicationStandard.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCommunicationStandard() throws Exception {
        // Initialize the database
        communicationStandardRepository.saveAndFlush(communicationStandard);

        // Get the communicationStandard
        restCommunicationStandardMockMvc.perform(get("/api/communication-standards/{id}", communicationStandard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(communicationStandard.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCommunicationStandard() throws Exception {
        // Get the communicationStandard
        restCommunicationStandardMockMvc.perform(get("/api/communication-standards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommunicationStandard() throws Exception {
        // Initialize the database
        communicationStandardService.save(communicationStandard);

        int databaseSizeBeforeUpdate = communicationStandardRepository.findAll().size();

        // Update the communicationStandard
        CommunicationStandard updatedCommunicationStandard = communicationStandardRepository.findOne(communicationStandard.getId());
        updatedCommunicationStandard
            .name(UPDATED_NAME);

        restCommunicationStandardMockMvc.perform(put("/api/communication-standards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommunicationStandard)))
            .andExpect(status().isOk());

        // Validate the CommunicationStandard in the database
        List<CommunicationStandard> communicationStandardList = communicationStandardRepository.findAll();
        assertThat(communicationStandardList).hasSize(databaseSizeBeforeUpdate);
        CommunicationStandard testCommunicationStandard = communicationStandardList.get(communicationStandardList.size() - 1);
        assertThat(testCommunicationStandard.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCommunicationStandard() throws Exception {
        int databaseSizeBeforeUpdate = communicationStandardRepository.findAll().size();

        // Create the CommunicationStandard

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCommunicationStandardMockMvc.perform(put("/api/communication-standards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communicationStandard)))
            .andExpect(status().isCreated());

        // Validate the CommunicationStandard in the database
        List<CommunicationStandard> communicationStandardList = communicationStandardRepository.findAll();
        assertThat(communicationStandardList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCommunicationStandard() throws Exception {
        // Initialize the database
        communicationStandardService.save(communicationStandard);

        int databaseSizeBeforeDelete = communicationStandardRepository.findAll().size();

        // Get the communicationStandard
        restCommunicationStandardMockMvc.perform(delete("/api/communication-standards/{id}", communicationStandard.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommunicationStandard> communicationStandardList = communicationStandardRepository.findAll();
        assertThat(communicationStandardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommunicationStandard.class);
        CommunicationStandard communicationStandard1 = new CommunicationStandard();
        communicationStandard1.setId(1L);
        CommunicationStandard communicationStandard2 = new CommunicationStandard();
        communicationStandard2.setId(communicationStandard1.getId());
        assertThat(communicationStandard1).isEqualTo(communicationStandard2);
        communicationStandard2.setId(2L);
        assertThat(communicationStandard1).isNotEqualTo(communicationStandard2);
        communicationStandard1.setId(null);
        assertThat(communicationStandard1).isNotEqualTo(communicationStandard2);
    }
}
