package com.sigetel.web.web.rest;

import com.sigetel.web.OvasApp;

import com.sigetel.web.domain.RequestTry;
import com.sigetel.web.repository.RequestTryRepository;
import com.sigetel.web.service.RequestTryService;
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
 * Test class for the RequestTryResource REST controller.
 *
 * @see RequestTryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OvasApp.class)
public class RequestTryResourceIntTest {

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final Long DEFAULT_REQUEST_ID = 1L;
    private static final Long UPDATED_REQUEST_ID = 2L;

    @Autowired
    private RequestTryRepository requestTryRepository;

    @Autowired
    private RequestTryService requestTryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRequestTryMockMvc;

    private RequestTry requestTry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RequestTryResource requestTryResource = new RequestTryResource(requestTryService);
        this.restRequestTryMockMvc = MockMvcBuilders.standaloneSetup(requestTryResource)
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
    public static RequestTry createEntity(EntityManager em) {
        RequestTry requestTry = new RequestTry()
            .status(DEFAULT_STATUS)
            .requestId(DEFAULT_REQUEST_ID);
        return requestTry;
    }

    @Before
    public void initTest() {
        requestTry = createEntity(em);
    }

    @Test
    @Transactional
    public void createRequestTry() throws Exception {
        int databaseSizeBeforeCreate = requestTryRepository.findAll().size();

        // Create the RequestTry
        restRequestTryMockMvc.perform(post("/api/request-tries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestTry)))
            .andExpect(status().isCreated());

        // Validate the RequestTry in the database
        List<RequestTry> requestTryList = requestTryRepository.findAll();
        assertThat(requestTryList).hasSize(databaseSizeBeforeCreate + 1);
        RequestTry testRequestTry = requestTryList.get(requestTryList.size() - 1);
        assertThat(testRequestTry.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testRequestTry.getRequestId()).isEqualTo(DEFAULT_REQUEST_ID);
    }

    @Test
    @Transactional
    public void createRequestTryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = requestTryRepository.findAll().size();

        // Create the RequestTry with an existing ID
        requestTry.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestTryMockMvc.perform(post("/api/request-tries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestTry)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RequestTry> requestTryList = requestTryRepository.findAll();
        assertThat(requestTryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRequestIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestTryRepository.findAll().size();
        // set the field null
        requestTry.setRequestId(null);

        // Create the RequestTry, which fails.

        restRequestTryMockMvc.perform(post("/api/request-tries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestTry)))
            .andExpect(status().isBadRequest());

        List<RequestTry> requestTryList = requestTryRepository.findAll();
        assertThat(requestTryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRequestTries() throws Exception {
        // Initialize the database
        requestTryRepository.saveAndFlush(requestTry);

        // Get all the requestTryList
        restRequestTryMockMvc.perform(get("/api/request-tries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requestTry.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].requestId").value(hasItem(DEFAULT_REQUEST_ID.intValue())));
    }

    @Test
    @Transactional
    public void getRequestTry() throws Exception {
        // Initialize the database
        requestTryRepository.saveAndFlush(requestTry);

        // Get the requestTry
        restRequestTryMockMvc.perform(get("/api/request-tries/{id}", requestTry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(requestTry.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.requestId").value(DEFAULT_REQUEST_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRequestTry() throws Exception {
        // Get the requestTry
        restRequestTryMockMvc.perform(get("/api/request-tries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRequestTry() throws Exception {
        // Initialize the database
        requestTryService.save(requestTry);

        int databaseSizeBeforeUpdate = requestTryRepository.findAll().size();

        // Update the requestTry
        RequestTry updatedRequestTry = requestTryRepository.findOne(requestTry.getId());
        updatedRequestTry
            .status(UPDATED_STATUS)
            .requestId(UPDATED_REQUEST_ID);

        restRequestTryMockMvc.perform(put("/api/request-tries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRequestTry)))
            .andExpect(status().isOk());

        // Validate the RequestTry in the database
        List<RequestTry> requestTryList = requestTryRepository.findAll();
        assertThat(requestTryList).hasSize(databaseSizeBeforeUpdate);
        RequestTry testRequestTry = requestTryList.get(requestTryList.size() - 1);
        assertThat(testRequestTry.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRequestTry.getRequestId()).isEqualTo(UPDATED_REQUEST_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingRequestTry() throws Exception {
        int databaseSizeBeforeUpdate = requestTryRepository.findAll().size();

        // Create the RequestTry

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRequestTryMockMvc.perform(put("/api/request-tries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestTry)))
            .andExpect(status().isCreated());

        // Validate the RequestTry in the database
        List<RequestTry> requestTryList = requestTryRepository.findAll();
        assertThat(requestTryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRequestTry() throws Exception {
        // Initialize the database
        requestTryService.save(requestTry);

        int databaseSizeBeforeDelete = requestTryRepository.findAll().size();

        // Get the requestTry
        restRequestTryMockMvc.perform(delete("/api/request-tries/{id}", requestTry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RequestTry> requestTryList = requestTryRepository.findAll();
        assertThat(requestTryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestTry.class);
        RequestTry requestTry1 = new RequestTry();
        requestTry1.setId(1L);
        RequestTry requestTry2 = new RequestTry();
        requestTry2.setId(requestTry1.getId());
        assertThat(requestTry1).isEqualTo(requestTry2);
        requestTry2.setId(2L);
        assertThat(requestTry1).isNotEqualTo(requestTry2);
        requestTry1.setId(null);
        assertThat(requestTry1).isNotEqualTo(requestTry2);
    }
}
