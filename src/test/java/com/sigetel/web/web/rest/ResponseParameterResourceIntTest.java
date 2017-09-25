package com.sigetel.web.web.rest;

import com.sigetel.web.OvasApp;

import com.sigetel.web.domain.ResponseParameter;
import com.sigetel.web.repository.ResponseParameterRepository;
import com.sigetel.web.service.ResponseParameterService;
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
 * Test class for the ResponseParameterResource REST controller.
 *
 * @see ResponseParameterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OvasApp.class)
public class ResponseParameterResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_SECTION = "AAAAAAAAAA";
    private static final String UPDATED_SECTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_MANDATORY = false;
    private static final Boolean UPDATED_IS_MANDATORY = true;

    @Autowired
    private ResponseParameterRepository responseParameterRepository;

    @Autowired
    private ResponseParameterService responseParameterService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restResponseParameterMockMvc;

    private ResponseParameter responseParameter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ResponseParameterResource responseParameterResource = new ResponseParameterResource(responseParameterService);
        this.restResponseParameterMockMvc = MockMvcBuilders.standaloneSetup(responseParameterResource)
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
    public static ResponseParameter createEntity(EntityManager em) {
        ResponseParameter responseParameter = new ResponseParameter()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .default_value(DEFAULT_DEFAULT_VALUE)
            .section(DEFAULT_SECTION)
            .isMandatory(DEFAULT_IS_MANDATORY);
        return responseParameter;
    }

    @Before
    public void initTest() {
        responseParameter = createEntity(em);
    }

    @Test
    @Transactional
    public void createResponseParameter() throws Exception {
        int databaseSizeBeforeCreate = responseParameterRepository.findAll().size();

        // Create the ResponseParameter
        restResponseParameterMockMvc.perform(post("/api/response-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responseParameter)))
            .andExpect(status().isCreated());

        // Validate the ResponseParameter in the database
        List<ResponseParameter> responseParameterList = responseParameterRepository.findAll();
        assertThat(responseParameterList).hasSize(databaseSizeBeforeCreate + 1);
        ResponseParameter testResponseParameter = responseParameterList.get(responseParameterList.size() - 1);
        assertThat(testResponseParameter.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testResponseParameter.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testResponseParameter.getDefault_value()).isEqualTo(DEFAULT_DEFAULT_VALUE);
        assertThat(testResponseParameter.getSection()).isEqualTo(DEFAULT_SECTION);
        assertThat(testResponseParameter.isIsMandatory()).isEqualTo(DEFAULT_IS_MANDATORY);
    }

    @Test
    @Transactional
    public void createResponseParameterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = responseParameterRepository.findAll().size();

        // Create the ResponseParameter with an existing ID
        responseParameter.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResponseParameterMockMvc.perform(post("/api/response-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responseParameter)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ResponseParameter> responseParameterList = responseParameterRepository.findAll();
        assertThat(responseParameterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = responseParameterRepository.findAll().size();
        // set the field null
        responseParameter.setName(null);

        // Create the ResponseParameter, which fails.

        restResponseParameterMockMvc.perform(post("/api/response-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responseParameter)))
            .andExpect(status().isBadRequest());

        List<ResponseParameter> responseParameterList = responseParameterRepository.findAll();
        assertThat(responseParameterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResponseParameters() throws Exception {
        // Initialize the database
        responseParameterRepository.saveAndFlush(responseParameter);

        // Get all the responseParameterList
        restResponseParameterMockMvc.perform(get("/api/response-parameters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responseParameter.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].default_value").value(hasItem(DEFAULT_DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].section").value(hasItem(DEFAULT_SECTION.toString())))
            .andExpect(jsonPath("$.[*].isMandatory").value(hasItem(DEFAULT_IS_MANDATORY.booleanValue())));
    }

    @Test
    @Transactional
    public void getResponseParameter() throws Exception {
        // Initialize the database
        responseParameterRepository.saveAndFlush(responseParameter);

        // Get the responseParameter
        restResponseParameterMockMvc.perform(get("/api/response-parameters/{id}", responseParameter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(responseParameter.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.default_value").value(DEFAULT_DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.section").value(DEFAULT_SECTION.toString()))
            .andExpect(jsonPath("$.isMandatory").value(DEFAULT_IS_MANDATORY.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingResponseParameter() throws Exception {
        // Get the responseParameter
        restResponseParameterMockMvc.perform(get("/api/response-parameters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResponseParameter() throws Exception {
        // Initialize the database
        responseParameterService.save(responseParameter);

        int databaseSizeBeforeUpdate = responseParameterRepository.findAll().size();

        // Update the responseParameter
        ResponseParameter updatedResponseParameter = responseParameterRepository.findOne(responseParameter.getId());
        updatedResponseParameter
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .default_value(UPDATED_DEFAULT_VALUE)
            .section(UPDATED_SECTION)
            .isMandatory(UPDATED_IS_MANDATORY);

        restResponseParameterMockMvc.perform(put("/api/response-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResponseParameter)))
            .andExpect(status().isOk());

        // Validate the ResponseParameter in the database
        List<ResponseParameter> responseParameterList = responseParameterRepository.findAll();
        assertThat(responseParameterList).hasSize(databaseSizeBeforeUpdate);
        ResponseParameter testResponseParameter = responseParameterList.get(responseParameterList.size() - 1);
        assertThat(testResponseParameter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testResponseParameter.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testResponseParameter.getDefault_value()).isEqualTo(UPDATED_DEFAULT_VALUE);
        assertThat(testResponseParameter.getSection()).isEqualTo(UPDATED_SECTION);
        assertThat(testResponseParameter.isIsMandatory()).isEqualTo(UPDATED_IS_MANDATORY);
    }

    @Test
    @Transactional
    public void updateNonExistingResponseParameter() throws Exception {
        int databaseSizeBeforeUpdate = responseParameterRepository.findAll().size();

        // Create the ResponseParameter

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restResponseParameterMockMvc.perform(put("/api/response-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responseParameter)))
            .andExpect(status().isCreated());

        // Validate the ResponseParameter in the database
        List<ResponseParameter> responseParameterList = responseParameterRepository.findAll();
        assertThat(responseParameterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteResponseParameter() throws Exception {
        // Initialize the database
        responseParameterService.save(responseParameter);

        int databaseSizeBeforeDelete = responseParameterRepository.findAll().size();

        // Get the responseParameter
        restResponseParameterMockMvc.perform(delete("/api/response-parameters/{id}", responseParameter.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ResponseParameter> responseParameterList = responseParameterRepository.findAll();
        assertThat(responseParameterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResponseParameter.class);
        ResponseParameter responseParameter1 = new ResponseParameter();
        responseParameter1.setId(1L);
        ResponseParameter responseParameter2 = new ResponseParameter();
        responseParameter2.setId(responseParameter1.getId());
        assertThat(responseParameter1).isEqualTo(responseParameter2);
        responseParameter2.setId(2L);
        assertThat(responseParameter1).isNotEqualTo(responseParameter2);
        responseParameter1.setId(null);
        assertThat(responseParameter1).isNotEqualTo(responseParameter2);
    }
}
