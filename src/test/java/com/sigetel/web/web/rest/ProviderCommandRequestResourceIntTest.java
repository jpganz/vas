package com.sigetel.web.web.rest;

import com.sigetel.web.OvasApp;

import com.sigetel.web.domain.ProviderCommandRequest;
import com.sigetel.web.repository.ProviderCommandRequestRepository;
import com.sigetel.web.service.ProviderCommandRequestService;
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
 * Test class for the ProviderCommandRequestResource REST controller.
 *
 * @see ProviderCommandRequestResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OvasApp.class)
public class ProviderCommandRequestResourceIntTest {

    private static final Long DEFAULT_REQUEST_ID = 1L;
    private static final Long UPDATED_REQUEST_ID = 2L;

    private static final Long DEFAULT_PROVIDER_COMMAND_ID = 1L;
    private static final Long UPDATED_PROVIDER_COMMAND_ID = 2L;

    @Autowired
    private ProviderCommandRequestRepository providerCommandRequestRepository;

    @Autowired
    private ProviderCommandRequestService providerCommandRequestService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProviderCommandRequestMockMvc;

    private ProviderCommandRequest providerCommandRequest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProviderCommandRequestResource providerCommandRequestResource = new ProviderCommandRequestResource(providerCommandRequestService);
        this.restProviderCommandRequestMockMvc = MockMvcBuilders.standaloneSetup(providerCommandRequestResource)
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
    public static ProviderCommandRequest createEntity(EntityManager em) {
        ProviderCommandRequest providerCommandRequest = new ProviderCommandRequest()
            .requestId(DEFAULT_REQUEST_ID)
            .providerCommandId(DEFAULT_PROVIDER_COMMAND_ID);
        return providerCommandRequest;
    }

    @Before
    public void initTest() {
        providerCommandRequest = createEntity(em);
    }

    @Test
    @Transactional
    public void createProviderCommandRequest() throws Exception {
        int databaseSizeBeforeCreate = providerCommandRequestRepository.findAll().size();

        // Create the ProviderCommandRequest
        restProviderCommandRequestMockMvc.perform(post("/api/provider-command-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerCommandRequest)))
            .andExpect(status().isCreated());

        // Validate the ProviderCommandRequest in the database
        List<ProviderCommandRequest> providerCommandRequestList = providerCommandRequestRepository.findAll();
        assertThat(providerCommandRequestList).hasSize(databaseSizeBeforeCreate + 1);
        ProviderCommandRequest testProviderCommandRequest = providerCommandRequestList.get(providerCommandRequestList.size() - 1);
        assertThat(testProviderCommandRequest.getRequestId()).isEqualTo(DEFAULT_REQUEST_ID);
        assertThat(testProviderCommandRequest.getProviderCommandId()).isEqualTo(DEFAULT_PROVIDER_COMMAND_ID);
    }

    @Test
    @Transactional
    public void createProviderCommandRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = providerCommandRequestRepository.findAll().size();

        // Create the ProviderCommandRequest with an existing ID
        providerCommandRequest.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProviderCommandRequestMockMvc.perform(post("/api/provider-command-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerCommandRequest)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProviderCommandRequest> providerCommandRequestList = providerCommandRequestRepository.findAll();
        assertThat(providerCommandRequestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRequestIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = providerCommandRequestRepository.findAll().size();
        // set the field null
        providerCommandRequest.setRequestId(null);

        // Create the ProviderCommandRequest, which fails.

        restProviderCommandRequestMockMvc.perform(post("/api/provider-command-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerCommandRequest)))
            .andExpect(status().isBadRequest());

        List<ProviderCommandRequest> providerCommandRequestList = providerCommandRequestRepository.findAll();
        assertThat(providerCommandRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProviderCommandIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = providerCommandRequestRepository.findAll().size();
        // set the field null
        providerCommandRequest.setProviderCommandId(null);

        // Create the ProviderCommandRequest, which fails.

        restProviderCommandRequestMockMvc.perform(post("/api/provider-command-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerCommandRequest)))
            .andExpect(status().isBadRequest());

        List<ProviderCommandRequest> providerCommandRequestList = providerCommandRequestRepository.findAll();
        assertThat(providerCommandRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProviderCommandRequests() throws Exception {
        // Initialize the database
        providerCommandRequestRepository.saveAndFlush(providerCommandRequest);

        // Get all the providerCommandRequestList
        restProviderCommandRequestMockMvc.perform(get("/api/provider-command-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(providerCommandRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].requestId").value(hasItem(DEFAULT_REQUEST_ID.intValue())))
            .andExpect(jsonPath("$.[*].providerCommandId").value(hasItem(DEFAULT_PROVIDER_COMMAND_ID.intValue())));
    }

    @Test
    @Transactional
    public void getProviderCommandRequest() throws Exception {
        // Initialize the database
        providerCommandRequestRepository.saveAndFlush(providerCommandRequest);

        // Get the providerCommandRequest
        restProviderCommandRequestMockMvc.perform(get("/api/provider-command-requests/{id}", providerCommandRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(providerCommandRequest.getId().intValue()))
            .andExpect(jsonPath("$.requestId").value(DEFAULT_REQUEST_ID.intValue()))
            .andExpect(jsonPath("$.providerCommandId").value(DEFAULT_PROVIDER_COMMAND_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProviderCommandRequest() throws Exception {
        // Get the providerCommandRequest
        restProviderCommandRequestMockMvc.perform(get("/api/provider-command-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProviderCommandRequest() throws Exception {
        // Initialize the database
        providerCommandRequestService.save(providerCommandRequest);

        int databaseSizeBeforeUpdate = providerCommandRequestRepository.findAll().size();

        // Update the providerCommandRequest
        ProviderCommandRequest updatedProviderCommandRequest = providerCommandRequestRepository.findOne(providerCommandRequest.getId());
        updatedProviderCommandRequest
            .requestId(UPDATED_REQUEST_ID)
            .providerCommandId(UPDATED_PROVIDER_COMMAND_ID);

        restProviderCommandRequestMockMvc.perform(put("/api/provider-command-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProviderCommandRequest)))
            .andExpect(status().isOk());

        // Validate the ProviderCommandRequest in the database
        List<ProviderCommandRequest> providerCommandRequestList = providerCommandRequestRepository.findAll();
        assertThat(providerCommandRequestList).hasSize(databaseSizeBeforeUpdate);
        ProviderCommandRequest testProviderCommandRequest = providerCommandRequestList.get(providerCommandRequestList.size() - 1);
        assertThat(testProviderCommandRequest.getRequestId()).isEqualTo(UPDATED_REQUEST_ID);
        assertThat(testProviderCommandRequest.getProviderCommandId()).isEqualTo(UPDATED_PROVIDER_COMMAND_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingProviderCommandRequest() throws Exception {
        int databaseSizeBeforeUpdate = providerCommandRequestRepository.findAll().size();

        // Create the ProviderCommandRequest

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProviderCommandRequestMockMvc.perform(put("/api/provider-command-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerCommandRequest)))
            .andExpect(status().isCreated());

        // Validate the ProviderCommandRequest in the database
        List<ProviderCommandRequest> providerCommandRequestList = providerCommandRequestRepository.findAll();
        assertThat(providerCommandRequestList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProviderCommandRequest() throws Exception {
        // Initialize the database
        providerCommandRequestService.save(providerCommandRequest);

        int databaseSizeBeforeDelete = providerCommandRequestRepository.findAll().size();

        // Get the providerCommandRequest
        restProviderCommandRequestMockMvc.perform(delete("/api/provider-command-requests/{id}", providerCommandRequest.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProviderCommandRequest> providerCommandRequestList = providerCommandRequestRepository.findAll();
        assertThat(providerCommandRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProviderCommandRequest.class);
        ProviderCommandRequest providerCommandRequest1 = new ProviderCommandRequest();
        providerCommandRequest1.setId(1L);
        ProviderCommandRequest providerCommandRequest2 = new ProviderCommandRequest();
        providerCommandRequest2.setId(providerCommandRequest1.getId());
        assertThat(providerCommandRequest1).isEqualTo(providerCommandRequest2);
        providerCommandRequest2.setId(2L);
        assertThat(providerCommandRequest1).isNotEqualTo(providerCommandRequest2);
        providerCommandRequest1.setId(null);
        assertThat(providerCommandRequest1).isNotEqualTo(providerCommandRequest2);
    }
}
