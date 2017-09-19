package com.sigetel.web.web.rest;

import com.sigetel.web.OvasApp;

import com.sigetel.web.domain.ServiceSecurity;
import com.sigetel.web.repository.ServiceSecurityRepository;
import com.sigetel.web.service.ServiceSecurityService;
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
 * Test class for the ServiceSecurityResource REST controller.
 *
 * @see ServiceSecurityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OvasApp.class)
public class ServiceSecurityResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_COMMUNICATION_STANDARD_ID = 1L;
    private static final Long UPDATED_COMMUNICATION_STANDARD_ID = 2L;

    @Autowired
    private ServiceSecurityRepository serviceSecurityRepository;

    @Autowired
    private ServiceSecurityService serviceSecurityService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restServiceSecurityMockMvc;

    private ServiceSecurity serviceSecurity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ServiceSecurityResource serviceSecurityResource = new ServiceSecurityResource(serviceSecurityService);
        this.restServiceSecurityMockMvc = MockMvcBuilders.standaloneSetup(serviceSecurityResource)
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
    public static ServiceSecurity createEntity(EntityManager em) {
        ServiceSecurity serviceSecurity = new ServiceSecurity()
            .name(DEFAULT_NAME)
            .communicationStandardId(DEFAULT_COMMUNICATION_STANDARD_ID);
        return serviceSecurity;
    }

    @Before
    public void initTest() {
        serviceSecurity = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceSecurity() throws Exception {
        int databaseSizeBeforeCreate = serviceSecurityRepository.findAll().size();

        // Create the ServiceSecurity
        restServiceSecurityMockMvc.perform(post("/api/service-securities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceSecurity)))
            .andExpect(status().isCreated());

        // Validate the ServiceSecurity in the database
        List<ServiceSecurity> serviceSecurityList = serviceSecurityRepository.findAll();
        assertThat(serviceSecurityList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceSecurity testServiceSecurity = serviceSecurityList.get(serviceSecurityList.size() - 1);
        assertThat(testServiceSecurity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testServiceSecurity.getCommunicationStandardId()).isEqualTo(DEFAULT_COMMUNICATION_STANDARD_ID);
    }

    @Test
    @Transactional
    public void createServiceSecurityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceSecurityRepository.findAll().size();

        // Create the ServiceSecurity with an existing ID
        serviceSecurity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceSecurityMockMvc.perform(post("/api/service-securities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceSecurity)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ServiceSecurity> serviceSecurityList = serviceSecurityRepository.findAll();
        assertThat(serviceSecurityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceSecurityRepository.findAll().size();
        // set the field null
        serviceSecurity.setName(null);

        // Create the ServiceSecurity, which fails.

        restServiceSecurityMockMvc.perform(post("/api/service-securities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceSecurity)))
            .andExpect(status().isBadRequest());

        List<ServiceSecurity> serviceSecurityList = serviceSecurityRepository.findAll();
        assertThat(serviceSecurityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCommunicationStandardIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceSecurityRepository.findAll().size();
        // set the field null
        serviceSecurity.setCommunicationStandardId(null);

        // Create the ServiceSecurity, which fails.

        restServiceSecurityMockMvc.perform(post("/api/service-securities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceSecurity)))
            .andExpect(status().isBadRequest());

        List<ServiceSecurity> serviceSecurityList = serviceSecurityRepository.findAll();
        assertThat(serviceSecurityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceSecurities() throws Exception {
        // Initialize the database
        serviceSecurityRepository.saveAndFlush(serviceSecurity);

        // Get all the serviceSecurityList
        restServiceSecurityMockMvc.perform(get("/api/service-securities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceSecurity.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].communicationStandardId").value(hasItem(DEFAULT_COMMUNICATION_STANDARD_ID.intValue())));
    }

    @Test
    @Transactional
    public void getServiceSecurity() throws Exception {
        // Initialize the database
        serviceSecurityRepository.saveAndFlush(serviceSecurity);

        // Get the serviceSecurity
        restServiceSecurityMockMvc.perform(get("/api/service-securities/{id}", serviceSecurity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceSecurity.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.communicationStandardId").value(DEFAULT_COMMUNICATION_STANDARD_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingServiceSecurity() throws Exception {
        // Get the serviceSecurity
        restServiceSecurityMockMvc.perform(get("/api/service-securities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceSecurity() throws Exception {
        // Initialize the database
        serviceSecurityService.save(serviceSecurity);

        int databaseSizeBeforeUpdate = serviceSecurityRepository.findAll().size();

        // Update the serviceSecurity
        ServiceSecurity updatedServiceSecurity = serviceSecurityRepository.findOne(serviceSecurity.getId());
        updatedServiceSecurity
            .name(UPDATED_NAME)
            .communicationStandardId(UPDATED_COMMUNICATION_STANDARD_ID);

        restServiceSecurityMockMvc.perform(put("/api/service-securities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedServiceSecurity)))
            .andExpect(status().isOk());

        // Validate the ServiceSecurity in the database
        List<ServiceSecurity> serviceSecurityList = serviceSecurityRepository.findAll();
        assertThat(serviceSecurityList).hasSize(databaseSizeBeforeUpdate);
        ServiceSecurity testServiceSecurity = serviceSecurityList.get(serviceSecurityList.size() - 1);
        assertThat(testServiceSecurity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testServiceSecurity.getCommunicationStandardId()).isEqualTo(UPDATED_COMMUNICATION_STANDARD_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceSecurity() throws Exception {
        int databaseSizeBeforeUpdate = serviceSecurityRepository.findAll().size();

        // Create the ServiceSecurity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restServiceSecurityMockMvc.perform(put("/api/service-securities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceSecurity)))
            .andExpect(status().isCreated());

        // Validate the ServiceSecurity in the database
        List<ServiceSecurity> serviceSecurityList = serviceSecurityRepository.findAll();
        assertThat(serviceSecurityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteServiceSecurity() throws Exception {
        // Initialize the database
        serviceSecurityService.save(serviceSecurity);

        int databaseSizeBeforeDelete = serviceSecurityRepository.findAll().size();

        // Get the serviceSecurity
        restServiceSecurityMockMvc.perform(delete("/api/service-securities/{id}", serviceSecurity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ServiceSecurity> serviceSecurityList = serviceSecurityRepository.findAll();
        assertThat(serviceSecurityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceSecurity.class);
        ServiceSecurity serviceSecurity1 = new ServiceSecurity();
        serviceSecurity1.setId(1L);
        ServiceSecurity serviceSecurity2 = new ServiceSecurity();
        serviceSecurity2.setId(serviceSecurity1.getId());
        assertThat(serviceSecurity1).isEqualTo(serviceSecurity2);
        serviceSecurity2.setId(2L);
        assertThat(serviceSecurity1).isNotEqualTo(serviceSecurity2);
        serviceSecurity1.setId(null);
        assertThat(serviceSecurity1).isNotEqualTo(serviceSecurity2);
    }
}
