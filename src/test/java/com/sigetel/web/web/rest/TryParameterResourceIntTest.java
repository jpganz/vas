package com.sigetel.web.web.rest;

import com.sigetel.web.OvasApp;

import com.sigetel.web.domain.TryParameter;
import com.sigetel.web.repository.TryParameterRepository;
import com.sigetel.web.service.TryParameterService;
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
 * Test class for the TryParameterResource REST controller.
 *
 * @see TryParameterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OvasApp.class)
public class TryParameterResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final Long DEFAULT_REQUEST_ID = 1L;
    private static final Long UPDATED_REQUEST_ID = 2L;

    private static final Long DEFAULT_REQUEST_PARAMETER_ID = 1L;
    private static final Long UPDATED_REQUEST_PARAMETER_ID = 2L;

    @Autowired
    private TryParameterRepository tryParameterRepository;

    @Autowired
    private TryParameterService tryParameterService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTryParameterMockMvc;

    private TryParameter tryParameter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TryParameterResource tryParameterResource = new TryParameterResource(tryParameterService);
        this.restTryParameterMockMvc = MockMvcBuilders.standaloneSetup(tryParameterResource)
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
    public static TryParameter createEntity(EntityManager em) {
        TryParameter tryParameter = new TryParameter()
            .value(DEFAULT_VALUE)
            .requestId(DEFAULT_REQUEST_ID)
            .requestParameterId(DEFAULT_REQUEST_PARAMETER_ID);
        return tryParameter;
    }

    @Before
    public void initTest() {
        tryParameter = createEntity(em);
    }

    @Test
    @Transactional
    public void createTryParameter() throws Exception {
        int databaseSizeBeforeCreate = tryParameterRepository.findAll().size();

        // Create the TryParameter
        restTryParameterMockMvc.perform(post("/api/try-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tryParameter)))
            .andExpect(status().isCreated());

        // Validate the TryParameter in the database
        List<TryParameter> tryParameterList = tryParameterRepository.findAll();
        assertThat(tryParameterList).hasSize(databaseSizeBeforeCreate + 1);
        TryParameter testTryParameter = tryParameterList.get(tryParameterList.size() - 1);
        assertThat(testTryParameter.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testTryParameter.getRequestId()).isEqualTo(DEFAULT_REQUEST_ID);
        assertThat(testTryParameter.getRequestParameterId()).isEqualTo(DEFAULT_REQUEST_PARAMETER_ID);
    }

    @Test
    @Transactional
    public void createTryParameterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tryParameterRepository.findAll().size();

        // Create the TryParameter with an existing ID
        tryParameter.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTryParameterMockMvc.perform(post("/api/try-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tryParameter)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TryParameter> tryParameterList = tryParameterRepository.findAll();
        assertThat(tryParameterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = tryParameterRepository.findAll().size();
        // set the field null
        tryParameter.setValue(null);

        // Create the TryParameter, which fails.

        restTryParameterMockMvc.perform(post("/api/try-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tryParameter)))
            .andExpect(status().isBadRequest());

        List<TryParameter> tryParameterList = tryParameterRepository.findAll();
        assertThat(tryParameterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRequestIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tryParameterRepository.findAll().size();
        // set the field null
        tryParameter.setRequestId(null);

        // Create the TryParameter, which fails.

        restTryParameterMockMvc.perform(post("/api/try-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tryParameter)))
            .andExpect(status().isBadRequest());

        List<TryParameter> tryParameterList = tryParameterRepository.findAll();
        assertThat(tryParameterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRequestParameterIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tryParameterRepository.findAll().size();
        // set the field null
        tryParameter.setRequestParameterId(null);

        // Create the TryParameter, which fails.

        restTryParameterMockMvc.perform(post("/api/try-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tryParameter)))
            .andExpect(status().isBadRequest());

        List<TryParameter> tryParameterList = tryParameterRepository.findAll();
        assertThat(tryParameterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTryParameters() throws Exception {
        // Initialize the database
        tryParameterRepository.saveAndFlush(tryParameter);

        // Get all the tryParameterList
        restTryParameterMockMvc.perform(get("/api/try-parameters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tryParameter.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].requestId").value(hasItem(DEFAULT_REQUEST_ID.intValue())))
            .andExpect(jsonPath("$.[*].requestParameterId").value(hasItem(DEFAULT_REQUEST_PARAMETER_ID.intValue())));
    }

    @Test
    @Transactional
    public void getTryParameter() throws Exception {
        // Initialize the database
        tryParameterRepository.saveAndFlush(tryParameter);

        // Get the tryParameter
        restTryParameterMockMvc.perform(get("/api/try-parameters/{id}", tryParameter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tryParameter.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.requestId").value(DEFAULT_REQUEST_ID.intValue()))
            .andExpect(jsonPath("$.requestParameterId").value(DEFAULT_REQUEST_PARAMETER_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTryParameter() throws Exception {
        // Get the tryParameter
        restTryParameterMockMvc.perform(get("/api/try-parameters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTryParameter() throws Exception {
        // Initialize the database
        tryParameterService.save(tryParameter);

        int databaseSizeBeforeUpdate = tryParameterRepository.findAll().size();

        // Update the tryParameter
        TryParameter updatedTryParameter = tryParameterRepository.findOne(tryParameter.getId());
        updatedTryParameter
            .value(UPDATED_VALUE)
            .requestId(UPDATED_REQUEST_ID)
            .requestParameterId(UPDATED_REQUEST_PARAMETER_ID);

        restTryParameterMockMvc.perform(put("/api/try-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTryParameter)))
            .andExpect(status().isOk());

        // Validate the TryParameter in the database
        List<TryParameter> tryParameterList = tryParameterRepository.findAll();
        assertThat(tryParameterList).hasSize(databaseSizeBeforeUpdate);
        TryParameter testTryParameter = tryParameterList.get(tryParameterList.size() - 1);
        assertThat(testTryParameter.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testTryParameter.getRequestId()).isEqualTo(UPDATED_REQUEST_ID);
        assertThat(testTryParameter.getRequestParameterId()).isEqualTo(UPDATED_REQUEST_PARAMETER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingTryParameter() throws Exception {
        int databaseSizeBeforeUpdate = tryParameterRepository.findAll().size();

        // Create the TryParameter

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTryParameterMockMvc.perform(put("/api/try-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tryParameter)))
            .andExpect(status().isCreated());

        // Validate the TryParameter in the database
        List<TryParameter> tryParameterList = tryParameterRepository.findAll();
        assertThat(tryParameterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTryParameter() throws Exception {
        // Initialize the database
        tryParameterService.save(tryParameter);

        int databaseSizeBeforeDelete = tryParameterRepository.findAll().size();

        // Get the tryParameter
        restTryParameterMockMvc.perform(delete("/api/try-parameters/{id}", tryParameter.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TryParameter> tryParameterList = tryParameterRepository.findAll();
        assertThat(tryParameterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TryParameter.class);
        TryParameter tryParameter1 = new TryParameter();
        tryParameter1.setId(1L);
        TryParameter tryParameter2 = new TryParameter();
        tryParameter2.setId(tryParameter1.getId());
        assertThat(tryParameter1).isEqualTo(tryParameter2);
        tryParameter2.setId(2L);
        assertThat(tryParameter1).isNotEqualTo(tryParameter2);
        tryParameter1.setId(null);
        assertThat(tryParameter1).isNotEqualTo(tryParameter2);
    }
}
