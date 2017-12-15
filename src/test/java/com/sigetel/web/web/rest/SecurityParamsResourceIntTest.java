package com.sigetel.web.web.rest;

import com.sigetel.web.OvasApp;

import com.sigetel.web.domain.SecurityParams;
import com.sigetel.web.repository.SecurityParamsRepository;
import com.sigetel.web.service.SecurityParamsService;
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
 * Test class for the SecurityParamsResource REST controller.
 *
 * @see SecurityParamsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OvasApp.class)
public class SecurityParamsResourceIntTest {

    private static final String DEFAULT_FIELD = "AAAAAAAAAA";
    private static final String UPDATED_FIELD = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_SECTION = "AAAAAAAAAA";
    private static final String UPDATED_SECTION = "BBBBBBBBBB";

    @Autowired
    private SecurityParamsRepository securityParamsRepository;

    @Autowired
    private SecurityParamsService securityParamsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSecurityParamsMockMvc;

    private SecurityParams securityParams;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SecurityParamsResource securityParamsResource = new SecurityParamsResource(securityParamsService);
        this.restSecurityParamsMockMvc = MockMvcBuilders.standaloneSetup(securityParamsResource)
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
    public static SecurityParams createEntity(EntityManager em) {
        SecurityParams securityParams = new SecurityParams()
            .field(DEFAULT_FIELD)
            .value(DEFAULT_VALUE)
            .section(DEFAULT_SECTION);
        return securityParams;
    }

    @Before
    public void initTest() {
        securityParams = createEntity(em);
    }

    @Test
    @Transactional
    public void createSecurityParams() throws Exception {
        int databaseSizeBeforeCreate = securityParamsRepository.findAll().size();

        // Create the SecurityParams
        restSecurityParamsMockMvc.perform(post("/api/security-params")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(securityParams)))
            .andExpect(status().isCreated());

        // Validate the SecurityParams in the database
        List<SecurityParams> securityParamsList = securityParamsRepository.findAll();
        assertThat(securityParamsList).hasSize(databaseSizeBeforeCreate + 1);
        SecurityParams testSecurityParams = securityParamsList.get(securityParamsList.size() - 1);
        assertThat(testSecurityParams.getField()).isEqualTo(DEFAULT_FIELD);
        assertThat(testSecurityParams.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testSecurityParams.getSection()).isEqualTo(DEFAULT_SECTION);
    }

    @Test
    @Transactional
    public void createSecurityParamsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = securityParamsRepository.findAll().size();

        // Create the SecurityParams with an existing ID
        securityParams.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSecurityParamsMockMvc.perform(post("/api/security-params")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(securityParams)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SecurityParams> securityParamsList = securityParamsRepository.findAll();
        assertThat(securityParamsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFieldIsRequired() throws Exception {
        int databaseSizeBeforeTest = securityParamsRepository.findAll().size();
        // set the field null
        securityParams.setField(null);

        // Create the SecurityParams, which fails.

        restSecurityParamsMockMvc.perform(post("/api/security-params")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(securityParams)))
            .andExpect(status().isBadRequest());

        List<SecurityParams> securityParamsList = securityParamsRepository.findAll();
        assertThat(securityParamsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSecurityParams() throws Exception {
        // Initialize the database
        securityParamsRepository.saveAndFlush(securityParams);

        // Get all the securityParamsList
        restSecurityParamsMockMvc.perform(get("/api/security-params?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(securityParams.getId().intValue())))
            .andExpect(jsonPath("$.[*].field").value(hasItem(DEFAULT_FIELD.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].section").value(hasItem(DEFAULT_SECTION.toString())));
    }

    @Test
    @Transactional
    public void getSecurityParams() throws Exception {
        // Initialize the database
        securityParamsRepository.saveAndFlush(securityParams);

        // Get the securityParams
        restSecurityParamsMockMvc.perform(get("/api/security-params/{id}", securityParams.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(securityParams.getId().intValue()))
            .andExpect(jsonPath("$.field").value(DEFAULT_FIELD.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.section").value(DEFAULT_SECTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSecurityParams() throws Exception {
        // Get the securityParams
        restSecurityParamsMockMvc.perform(get("/api/security-params/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSecurityParams() throws Exception {
        // Initialize the database
        securityParamsService.save(securityParams);

        int databaseSizeBeforeUpdate = securityParamsRepository.findAll().size();

        // Update the securityParams
        SecurityParams updatedSecurityParams = securityParamsRepository.findOne(securityParams.getId());
        updatedSecurityParams
            .field(UPDATED_FIELD)
            .value(UPDATED_VALUE)
            .section(UPDATED_SECTION);

        restSecurityParamsMockMvc.perform(put("/api/security-params")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSecurityParams)))
            .andExpect(status().isOk());

        // Validate the SecurityParams in the database
        List<SecurityParams> securityParamsList = securityParamsRepository.findAll();
        assertThat(securityParamsList).hasSize(databaseSizeBeforeUpdate);
        SecurityParams testSecurityParams = securityParamsList.get(securityParamsList.size() - 1);
        assertThat(testSecurityParams.getField()).isEqualTo(UPDATED_FIELD);
        assertThat(testSecurityParams.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testSecurityParams.getSection()).isEqualTo(UPDATED_SECTION);
    }

    @Test
    @Transactional
    public void updateNonExistingSecurityParams() throws Exception {
        int databaseSizeBeforeUpdate = securityParamsRepository.findAll().size();

        // Create the SecurityParams

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSecurityParamsMockMvc.perform(put("/api/security-params")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(securityParams)))
            .andExpect(status().isCreated());

        // Validate the SecurityParams in the database
        List<SecurityParams> securityParamsList = securityParamsRepository.findAll();
        assertThat(securityParamsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSecurityParams() throws Exception {
        // Initialize the database
        securityParamsService.save(securityParams);

        int databaseSizeBeforeDelete = securityParamsRepository.findAll().size();

        // Get the securityParams
        restSecurityParamsMockMvc.perform(delete("/api/security-params/{id}", securityParams.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SecurityParams> securityParamsList = securityParamsRepository.findAll();
        assertThat(securityParamsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SecurityParams.class);
        SecurityParams securityParams1 = new SecurityParams();
        securityParams1.setId(1L);
        SecurityParams securityParams2 = new SecurityParams();
        securityParams2.setId(securityParams1.getId());
        assertThat(securityParams1).isEqualTo(securityParams2);
        securityParams2.setId(2L);
        assertThat(securityParams1).isNotEqualTo(securityParams2);
        securityParams1.setId(null);
        assertThat(securityParams1).isNotEqualTo(securityParams2);
    }
}
