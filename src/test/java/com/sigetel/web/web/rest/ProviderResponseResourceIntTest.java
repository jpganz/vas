package com.sigetel.web.web.rest;

import com.sigetel.web.OvasApp;

import com.sigetel.web.domain.ProviderResponse;
import com.sigetel.web.repository.ProviderResponseRepository;
import com.sigetel.web.service.ProviderResponseService;
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
 * Test class for the ProviderResponseResource REST controller.
 *
 * @see ProviderResponseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OvasApp.class)
public class ProviderResponseResourceIntTest {

    private static final String DEFAULT_EMAIL_NOTIFY = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_NOTIFY = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ADDRESS_TO_NOTIFY = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ADDRESS_TO_NOTIFY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ADD_TO_RETRY = false;
    private static final Boolean UPDATED_ADD_TO_RETRY = true;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_PROVIDER_COMMAND_ID = 1L;
    private static final Long UPDATED_PROVIDER_COMMAND_ID = 2L;

    @Autowired
    private ProviderResponseRepository providerResponseRepository;

    @Autowired
    private ProviderResponseService providerResponseService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProviderResponseMockMvc;

    private ProviderResponse providerResponse;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProviderResponseResource providerResponseResource = new ProviderResponseResource(providerResponseService);
        this.restProviderResponseMockMvc = MockMvcBuilders.standaloneSetup(providerResponseResource)
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
    public static ProviderResponse createEntity(EntityManager em) {
        ProviderResponse providerResponse = new ProviderResponse()
            .emailNotify(DEFAULT_EMAIL_NOTIFY)
            .emailAddressToNotify(DEFAULT_EMAIL_ADDRESS_TO_NOTIFY)
            .addToRetry(DEFAULT_ADD_TO_RETRY)
            .type(DEFAULT_TYPE)
            .providerCommandId(DEFAULT_PROVIDER_COMMAND_ID);
        return providerResponse;
    }

    @Before
    public void initTest() {
        providerResponse = createEntity(em);
    }

    @Test
    @Transactional
    public void createProviderResponse() throws Exception {
        int databaseSizeBeforeCreate = providerResponseRepository.findAll().size();

        // Create the ProviderResponse
        restProviderResponseMockMvc.perform(post("/api/provider-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerResponse)))
            .andExpect(status().isCreated());

        // Validate the ProviderResponse in the database
        List<ProviderResponse> providerResponseList = providerResponseRepository.findAll();
        assertThat(providerResponseList).hasSize(databaseSizeBeforeCreate + 1);
        ProviderResponse testProviderResponse = providerResponseList.get(providerResponseList.size() - 1);
        assertThat(testProviderResponse.getEmailNotify()).isEqualTo(DEFAULT_EMAIL_NOTIFY);
        assertThat(testProviderResponse.getEmailAddressToNotify()).isEqualTo(DEFAULT_EMAIL_ADDRESS_TO_NOTIFY);
        assertThat(testProviderResponse.isAddToRetry()).isEqualTo(DEFAULT_ADD_TO_RETRY);
        assertThat(testProviderResponse.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProviderResponse.getProviderCommandId()).isEqualTo(DEFAULT_PROVIDER_COMMAND_ID);
    }

    @Test
    @Transactional
    public void createProviderResponseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = providerResponseRepository.findAll().size();

        // Create the ProviderResponse with an existing ID
        providerResponse.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProviderResponseMockMvc.perform(post("/api/provider-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerResponse)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProviderResponse> providerResponseList = providerResponseRepository.findAll();
        assertThat(providerResponseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = providerResponseRepository.findAll().size();
        // set the field null
        providerResponse.setType(null);

        // Create the ProviderResponse, which fails.

        restProviderResponseMockMvc.perform(post("/api/provider-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerResponse)))
            .andExpect(status().isBadRequest());

        List<ProviderResponse> providerResponseList = providerResponseRepository.findAll();
        assertThat(providerResponseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProviderCommandIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = providerResponseRepository.findAll().size();
        // set the field null
        providerResponse.setProviderCommandId(null);

        // Create the ProviderResponse, which fails.

        restProviderResponseMockMvc.perform(post("/api/provider-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerResponse)))
            .andExpect(status().isBadRequest());

        List<ProviderResponse> providerResponseList = providerResponseRepository.findAll();
        assertThat(providerResponseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProviderResponses() throws Exception {
        // Initialize the database
        providerResponseRepository.saveAndFlush(providerResponse);

        // Get all the providerResponseList
        restProviderResponseMockMvc.perform(get("/api/provider-responses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(providerResponse.getId().intValue())))
            .andExpect(jsonPath("$.[*].emailNotify").value(hasItem(DEFAULT_EMAIL_NOTIFY.toString())))
            .andExpect(jsonPath("$.[*].emailAddressToNotify").value(hasItem(DEFAULT_EMAIL_ADDRESS_TO_NOTIFY.toString())))
            .andExpect(jsonPath("$.[*].addToRetry").value(hasItem(DEFAULT_ADD_TO_RETRY.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].providerCommandId").value(hasItem(DEFAULT_PROVIDER_COMMAND_ID.intValue())));
    }

    @Test
    @Transactional
    public void getProviderResponse() throws Exception {
        // Initialize the database
        providerResponseRepository.saveAndFlush(providerResponse);

        // Get the providerResponse
        restProviderResponseMockMvc.perform(get("/api/provider-responses/{id}", providerResponse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(providerResponse.getId().intValue()))
            .andExpect(jsonPath("$.emailNotify").value(DEFAULT_EMAIL_NOTIFY.toString()))
            .andExpect(jsonPath("$.emailAddressToNotify").value(DEFAULT_EMAIL_ADDRESS_TO_NOTIFY.toString()))
            .andExpect(jsonPath("$.addToRetry").value(DEFAULT_ADD_TO_RETRY.booleanValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.providerCommandId").value(DEFAULT_PROVIDER_COMMAND_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProviderResponse() throws Exception {
        // Get the providerResponse
        restProviderResponseMockMvc.perform(get("/api/provider-responses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProviderResponse() throws Exception {
        // Initialize the database
        providerResponseService.save(providerResponse);

        int databaseSizeBeforeUpdate = providerResponseRepository.findAll().size();

        // Update the providerResponse
        ProviderResponse updatedProviderResponse = providerResponseRepository.findOne(providerResponse.getId());
        updatedProviderResponse
            .emailNotify(UPDATED_EMAIL_NOTIFY)
            .emailAddressToNotify(UPDATED_EMAIL_ADDRESS_TO_NOTIFY)
            .addToRetry(UPDATED_ADD_TO_RETRY)
            .type(UPDATED_TYPE)
            .providerCommandId(UPDATED_PROVIDER_COMMAND_ID);

        restProviderResponseMockMvc.perform(put("/api/provider-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProviderResponse)))
            .andExpect(status().isOk());

        // Validate the ProviderResponse in the database
        List<ProviderResponse> providerResponseList = providerResponseRepository.findAll();
        assertThat(providerResponseList).hasSize(databaseSizeBeforeUpdate);
        ProviderResponse testProviderResponse = providerResponseList.get(providerResponseList.size() - 1);
        assertThat(testProviderResponse.getEmailNotify()).isEqualTo(UPDATED_EMAIL_NOTIFY);
        assertThat(testProviderResponse.getEmailAddressToNotify()).isEqualTo(UPDATED_EMAIL_ADDRESS_TO_NOTIFY);
        assertThat(testProviderResponse.isAddToRetry()).isEqualTo(UPDATED_ADD_TO_RETRY);
        assertThat(testProviderResponse.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProviderResponse.getProviderCommandId()).isEqualTo(UPDATED_PROVIDER_COMMAND_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingProviderResponse() throws Exception {
        int databaseSizeBeforeUpdate = providerResponseRepository.findAll().size();

        // Create the ProviderResponse

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProviderResponseMockMvc.perform(put("/api/provider-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerResponse)))
            .andExpect(status().isCreated());

        // Validate the ProviderResponse in the database
        List<ProviderResponse> providerResponseList = providerResponseRepository.findAll();
        assertThat(providerResponseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProviderResponse() throws Exception {
        // Initialize the database
        providerResponseService.save(providerResponse);

        int databaseSizeBeforeDelete = providerResponseRepository.findAll().size();

        // Get the providerResponse
        restProviderResponseMockMvc.perform(delete("/api/provider-responses/{id}", providerResponse.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProviderResponse> providerResponseList = providerResponseRepository.findAll();
        assertThat(providerResponseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProviderResponse.class);
        ProviderResponse providerResponse1 = new ProviderResponse();
        providerResponse1.setId(1L);
        ProviderResponse providerResponse2 = new ProviderResponse();
        providerResponse2.setId(providerResponse1.getId());
        assertThat(providerResponse1).isEqualTo(providerResponse2);
        providerResponse2.setId(2L);
        assertThat(providerResponse1).isNotEqualTo(providerResponse2);
        providerResponse1.setId(null);
        assertThat(providerResponse1).isNotEqualTo(providerResponse2);
    }
}
