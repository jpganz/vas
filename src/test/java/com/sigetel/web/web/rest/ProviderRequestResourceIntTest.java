package com.sigetel.web.web.rest;

import com.sigetel.web.OvasApp;

import com.sigetel.web.domain.ProviderRequest;
import com.sigetel.web.repository.ProviderRequestRepository;
import com.sigetel.web.service.ProviderRequestService;
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
 * Test class for the ProviderRequestResource REST controller.
 *
 * @see ProviderRequestResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OvasApp.class)
public class ProviderRequestResourceIntTest {

    @Autowired
    private ProviderRequestRepository providerRequestRepository;

    @Autowired
    private ProviderRequestService providerRequestService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProviderRequestMockMvc;

    private ProviderRequest providerRequest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProviderRequestResource providerRequestResource = new ProviderRequestResource(providerRequestService);
        this.restProviderRequestMockMvc = MockMvcBuilders.standaloneSetup(providerRequestResource)
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
    public static ProviderRequest createEntity(EntityManager em) {
        ProviderRequest providerRequest = new ProviderRequest();
        return providerRequest;
    }

    @Before
    public void initTest() {
        providerRequest = createEntity(em);
    }

    @Test
    @Transactional
    public void createProviderRequest() throws Exception {
        int databaseSizeBeforeCreate = providerRequestRepository.findAll().size();

        // Create the ProviderRequest
        restProviderRequestMockMvc.perform(post("/api/provider-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerRequest)))
            .andExpect(status().isCreated());

        // Validate the ProviderRequest in the database
        List<ProviderRequest> providerRequestList = providerRequestRepository.findAll();
        assertThat(providerRequestList).hasSize(databaseSizeBeforeCreate + 1);
        ProviderRequest testProviderRequest = providerRequestList.get(providerRequestList.size() - 1);
    }

    @Test
    @Transactional
    public void createProviderRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = providerRequestRepository.findAll().size();

        // Create the ProviderRequest with an existing ID
        providerRequest.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProviderRequestMockMvc.perform(post("/api/provider-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerRequest)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProviderRequest> providerRequestList = providerRequestRepository.findAll();
        assertThat(providerRequestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProviderRequests() throws Exception {
        // Initialize the database
        providerRequestRepository.saveAndFlush(providerRequest);

        // Get all the providerRequestList
        restProviderRequestMockMvc.perform(get("/api/provider-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(providerRequest.getId().intValue())));
    }

    @Test
    @Transactional
    public void getProviderRequest() throws Exception {
        // Initialize the database
        providerRequestRepository.saveAndFlush(providerRequest);

        // Get the providerRequest
        restProviderRequestMockMvc.perform(get("/api/provider-requests/{id}", providerRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(providerRequest.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProviderRequest() throws Exception {
        // Get the providerRequest
        restProviderRequestMockMvc.perform(get("/api/provider-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProviderRequest() throws Exception {
        // Initialize the database
        providerRequestService.save(providerRequest);

        int databaseSizeBeforeUpdate = providerRequestRepository.findAll().size();

        // Update the providerRequest
        ProviderRequest updatedProviderRequest = providerRequestRepository.findOne(providerRequest.getId());

        restProviderRequestMockMvc.perform(put("/api/provider-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProviderRequest)))
            .andExpect(status().isOk());

        // Validate the ProviderRequest in the database
        List<ProviderRequest> providerRequestList = providerRequestRepository.findAll();
        assertThat(providerRequestList).hasSize(databaseSizeBeforeUpdate);
        ProviderRequest testProviderRequest = providerRequestList.get(providerRequestList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingProviderRequest() throws Exception {
        int databaseSizeBeforeUpdate = providerRequestRepository.findAll().size();

        // Create the ProviderRequest

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProviderRequestMockMvc.perform(put("/api/provider-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerRequest)))
            .andExpect(status().isCreated());

        // Validate the ProviderRequest in the database
        List<ProviderRequest> providerRequestList = providerRequestRepository.findAll();
        assertThat(providerRequestList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProviderRequest() throws Exception {
        // Initialize the database
        providerRequestService.save(providerRequest);

        int databaseSizeBeforeDelete = providerRequestRepository.findAll().size();

        // Get the providerRequest
        restProviderRequestMockMvc.perform(delete("/api/provider-requests/{id}", providerRequest.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProviderRequest> providerRequestList = providerRequestRepository.findAll();
        assertThat(providerRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProviderRequest.class);
        ProviderRequest providerRequest1 = new ProviderRequest();
        providerRequest1.setId(1L);
        ProviderRequest providerRequest2 = new ProviderRequest();
        providerRequest2.setId(providerRequest1.getId());
        assertThat(providerRequest1).isEqualTo(providerRequest2);
        providerRequest2.setId(2L);
        assertThat(providerRequest1).isNotEqualTo(providerRequest2);
        providerRequest1.setId(null);
        assertThat(providerRequest1).isNotEqualTo(providerRequest2);
    }
}
