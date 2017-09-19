package com.sigetel.web.web.rest;

import com.sigetel.web.OvasApp;

import com.sigetel.web.domain.TryResponseParameter;
import com.sigetel.web.repository.TryResponseParameterRepository;
import com.sigetel.web.service.TryResponseParameterService;
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
 * Test class for the TryResponseParameterResource REST controller.
 *
 * @see TryResponseParameterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OvasApp.class)
public class TryResponseParameterResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final Long DEFAULT_REQUEST_TRY_ID = 1L;
    private static final Long UPDATED_REQUEST_TRY_ID = 2L;

    private static final Long DEFAULT_RESPONSE_PARAMETER_ID = 1L;
    private static final Long UPDATED_RESPONSE_PARAMETER_ID = 2L;

    @Autowired
    private TryResponseParameterRepository tryResponseParameterRepository;

    @Autowired
    private TryResponseParameterService tryResponseParameterService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTryResponseParameterMockMvc;

    private TryResponseParameter tryResponseParameter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TryResponseParameterResource tryResponseParameterResource = new TryResponseParameterResource(tryResponseParameterService);
        this.restTryResponseParameterMockMvc = MockMvcBuilders.standaloneSetup(tryResponseParameterResource)
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
    public static TryResponseParameter createEntity(EntityManager em) {
        TryResponseParameter tryResponseParameter = new TryResponseParameter()
            .value(DEFAULT_VALUE)
            .requestTryId(DEFAULT_REQUEST_TRY_ID)
            .responseParameterId(DEFAULT_RESPONSE_PARAMETER_ID);
        return tryResponseParameter;
    }

    @Before
    public void initTest() {
        tryResponseParameter = createEntity(em);
    }

    @Test
    @Transactional
    public void createTryResponseParameter() throws Exception {
        int databaseSizeBeforeCreate = tryResponseParameterRepository.findAll().size();

        // Create the TryResponseParameter
        restTryResponseParameterMockMvc.perform(post("/api/try-response-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tryResponseParameter)))
            .andExpect(status().isCreated());

        // Validate the TryResponseParameter in the database
        List<TryResponseParameter> tryResponseParameterList = tryResponseParameterRepository.findAll();
        assertThat(tryResponseParameterList).hasSize(databaseSizeBeforeCreate + 1);
        TryResponseParameter testTryResponseParameter = tryResponseParameterList.get(tryResponseParameterList.size() - 1);
        assertThat(testTryResponseParameter.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testTryResponseParameter.getRequestTryId()).isEqualTo(DEFAULT_REQUEST_TRY_ID);
        assertThat(testTryResponseParameter.getResponseParameterId()).isEqualTo(DEFAULT_RESPONSE_PARAMETER_ID);
    }

    @Test
    @Transactional
    public void createTryResponseParameterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tryResponseParameterRepository.findAll().size();

        // Create the TryResponseParameter with an existing ID
        tryResponseParameter.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTryResponseParameterMockMvc.perform(post("/api/try-response-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tryResponseParameter)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TryResponseParameter> tryResponseParameterList = tryResponseParameterRepository.findAll();
        assertThat(tryResponseParameterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRequestTryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tryResponseParameterRepository.findAll().size();
        // set the field null
        tryResponseParameter.setRequestTryId(null);

        // Create the TryResponseParameter, which fails.

        restTryResponseParameterMockMvc.perform(post("/api/try-response-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tryResponseParameter)))
            .andExpect(status().isBadRequest());

        List<TryResponseParameter> tryResponseParameterList = tryResponseParameterRepository.findAll();
        assertThat(tryResponseParameterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResponseParameterIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tryResponseParameterRepository.findAll().size();
        // set the field null
        tryResponseParameter.setResponseParameterId(null);

        // Create the TryResponseParameter, which fails.

        restTryResponseParameterMockMvc.perform(post("/api/try-response-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tryResponseParameter)))
            .andExpect(status().isBadRequest());

        List<TryResponseParameter> tryResponseParameterList = tryResponseParameterRepository.findAll();
        assertThat(tryResponseParameterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTryResponseParameters() throws Exception {
        // Initialize the database
        tryResponseParameterRepository.saveAndFlush(tryResponseParameter);

        // Get all the tryResponseParameterList
        restTryResponseParameterMockMvc.perform(get("/api/try-response-parameters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tryResponseParameter.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].requestTryId").value(hasItem(DEFAULT_REQUEST_TRY_ID.intValue())))
            .andExpect(jsonPath("$.[*].responseParameterId").value(hasItem(DEFAULT_RESPONSE_PARAMETER_ID.intValue())));
    }

    @Test
    @Transactional
    public void getTryResponseParameter() throws Exception {
        // Initialize the database
        tryResponseParameterRepository.saveAndFlush(tryResponseParameter);

        // Get the tryResponseParameter
        restTryResponseParameterMockMvc.perform(get("/api/try-response-parameters/{id}", tryResponseParameter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tryResponseParameter.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.requestTryId").value(DEFAULT_REQUEST_TRY_ID.intValue()))
            .andExpect(jsonPath("$.responseParameterId").value(DEFAULT_RESPONSE_PARAMETER_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTryResponseParameter() throws Exception {
        // Get the tryResponseParameter
        restTryResponseParameterMockMvc.perform(get("/api/try-response-parameters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTryResponseParameter() throws Exception {
        // Initialize the database
        tryResponseParameterService.save(tryResponseParameter);

        int databaseSizeBeforeUpdate = tryResponseParameterRepository.findAll().size();

        // Update the tryResponseParameter
        TryResponseParameter updatedTryResponseParameter = tryResponseParameterRepository.findOne(tryResponseParameter.getId());
        updatedTryResponseParameter
            .value(UPDATED_VALUE)
            .requestTryId(UPDATED_REQUEST_TRY_ID)
            .responseParameterId(UPDATED_RESPONSE_PARAMETER_ID);

        restTryResponseParameterMockMvc.perform(put("/api/try-response-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTryResponseParameter)))
            .andExpect(status().isOk());

        // Validate the TryResponseParameter in the database
        List<TryResponseParameter> tryResponseParameterList = tryResponseParameterRepository.findAll();
        assertThat(tryResponseParameterList).hasSize(databaseSizeBeforeUpdate);
        TryResponseParameter testTryResponseParameter = tryResponseParameterList.get(tryResponseParameterList.size() - 1);
        assertThat(testTryResponseParameter.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testTryResponseParameter.getRequestTryId()).isEqualTo(UPDATED_REQUEST_TRY_ID);
        assertThat(testTryResponseParameter.getResponseParameterId()).isEqualTo(UPDATED_RESPONSE_PARAMETER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingTryResponseParameter() throws Exception {
        int databaseSizeBeforeUpdate = tryResponseParameterRepository.findAll().size();

        // Create the TryResponseParameter

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTryResponseParameterMockMvc.perform(put("/api/try-response-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tryResponseParameter)))
            .andExpect(status().isCreated());

        // Validate the TryResponseParameter in the database
        List<TryResponseParameter> tryResponseParameterList = tryResponseParameterRepository.findAll();
        assertThat(tryResponseParameterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTryResponseParameter() throws Exception {
        // Initialize the database
        tryResponseParameterService.save(tryResponseParameter);

        int databaseSizeBeforeDelete = tryResponseParameterRepository.findAll().size();

        // Get the tryResponseParameter
        restTryResponseParameterMockMvc.perform(delete("/api/try-response-parameters/{id}", tryResponseParameter.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TryResponseParameter> tryResponseParameterList = tryResponseParameterRepository.findAll();
        assertThat(tryResponseParameterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TryResponseParameter.class);
        TryResponseParameter tryResponseParameter1 = new TryResponseParameter();
        tryResponseParameter1.setId(1L);
        TryResponseParameter tryResponseParameter2 = new TryResponseParameter();
        tryResponseParameter2.setId(tryResponseParameter1.getId());
        assertThat(tryResponseParameter1).isEqualTo(tryResponseParameter2);
        tryResponseParameter2.setId(2L);
        assertThat(tryResponseParameter1).isNotEqualTo(tryResponseParameter2);
        tryResponseParameter1.setId(null);
        assertThat(tryResponseParameter1).isNotEqualTo(tryResponseParameter2);
    }
}
