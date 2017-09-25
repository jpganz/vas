package com.sigetel.web.web.rest;

import com.sigetel.web.OvasApp;

import com.sigetel.web.domain.RequestParameter;
import com.sigetel.web.repository.RequestParameterRepository;
import com.sigetel.web.service.RequestParameterService;
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
 * Test class for the RequestParameterResource REST controller.
 *
 * @see RequestParameterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OvasApp.class)
public class RequestParameterResourceIntTest {

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
    private RequestParameterRepository requestParameterRepository;

    @Autowired
    private RequestParameterService requestParameterService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRequestParameterMockMvc;

    private RequestParameter requestParameter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RequestParameterResource requestParameterResource = new RequestParameterResource(requestParameterService);
        this.restRequestParameterMockMvc = MockMvcBuilders.standaloneSetup(requestParameterResource)
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
    public static RequestParameter createEntity(EntityManager em) {
        RequestParameter requestParameter = new RequestParameter()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .defaultValue(DEFAULT_DEFAULT_VALUE)
            .section(DEFAULT_SECTION)
            .isMandatory(DEFAULT_IS_MANDATORY);
        return requestParameter;
    }

    @Before
    public void initTest() {
        requestParameter = createEntity(em);
    }

    @Test
    @Transactional
    public void createRequestParameter() throws Exception {
        int databaseSizeBeforeCreate = requestParameterRepository.findAll().size();

        // Create the RequestParameter
        restRequestParameterMockMvc.perform(post("/api/request-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestParameter)))
            .andExpect(status().isCreated());

        // Validate the RequestParameter in the database
        List<RequestParameter> requestParameterList = requestParameterRepository.findAll();
        assertThat(requestParameterList).hasSize(databaseSizeBeforeCreate + 1);
        RequestParameter testRequestParameter = requestParameterList.get(requestParameterList.size() - 1);
        assertThat(testRequestParameter.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRequestParameter.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testRequestParameter.getDefaultValue()).isEqualTo(DEFAULT_DEFAULT_VALUE);
        assertThat(testRequestParameter.getSection()).isEqualTo(DEFAULT_SECTION);
        assertThat(testRequestParameter.isIsMandatory()).isEqualTo(DEFAULT_IS_MANDATORY);
    }

    @Test
    @Transactional
    public void createRequestParameterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = requestParameterRepository.findAll().size();

        // Create the RequestParameter with an existing ID
        requestParameter.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestParameterMockMvc.perform(post("/api/request-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestParameter)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RequestParameter> requestParameterList = requestParameterRepository.findAll();
        assertThat(requestParameterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestParameterRepository.findAll().size();
        // set the field null
        requestParameter.setName(null);

        // Create the RequestParameter, which fails.

        restRequestParameterMockMvc.perform(post("/api/request-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestParameter)))
            .andExpect(status().isBadRequest());

        List<RequestParameter> requestParameterList = requestParameterRepository.findAll();
        assertThat(requestParameterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestParameterRepository.findAll().size();
        // set the field null
        requestParameter.setType(null);

        // Create the RequestParameter, which fails.

        restRequestParameterMockMvc.perform(post("/api/request-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestParameter)))
            .andExpect(status().isBadRequest());

        List<RequestParameter> requestParameterList = requestParameterRepository.findAll();
        assertThat(requestParameterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSectionIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestParameterRepository.findAll().size();
        // set the field null
        requestParameter.setSection(null);

        // Create the RequestParameter, which fails.

        restRequestParameterMockMvc.perform(post("/api/request-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestParameter)))
            .andExpect(status().isBadRequest());

        List<RequestParameter> requestParameterList = requestParameterRepository.findAll();
        assertThat(requestParameterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRequestParameters() throws Exception {
        // Initialize the database
        requestParameterRepository.saveAndFlush(requestParameter);

        // Get all the requestParameterList
        restRequestParameterMockMvc.perform(get("/api/request-parameters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requestParameter.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].defaultValue").value(hasItem(DEFAULT_DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].section").value(hasItem(DEFAULT_SECTION.toString())))
            .andExpect(jsonPath("$.[*].isMandatory").value(hasItem(DEFAULT_IS_MANDATORY.booleanValue())));
    }

    @Test
    @Transactional
    public void getRequestParameter() throws Exception {
        // Initialize the database
        requestParameterRepository.saveAndFlush(requestParameter);

        // Get the requestParameter
        restRequestParameterMockMvc.perform(get("/api/request-parameters/{id}", requestParameter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(requestParameter.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.defaultValue").value(DEFAULT_DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.section").value(DEFAULT_SECTION.toString()))
            .andExpect(jsonPath("$.isMandatory").value(DEFAULT_IS_MANDATORY.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRequestParameter() throws Exception {
        // Get the requestParameter
        restRequestParameterMockMvc.perform(get("/api/request-parameters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRequestParameter() throws Exception {
        // Initialize the database
        requestParameterService.save(requestParameter);

        int databaseSizeBeforeUpdate = requestParameterRepository.findAll().size();

        // Update the requestParameter
        RequestParameter updatedRequestParameter = requestParameterRepository.findOne(requestParameter.getId());
        updatedRequestParameter
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .defaultValue(UPDATED_DEFAULT_VALUE)
            .section(UPDATED_SECTION)
            .isMandatory(UPDATED_IS_MANDATORY);

        restRequestParameterMockMvc.perform(put("/api/request-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRequestParameter)))
            .andExpect(status().isOk());

        // Validate the RequestParameter in the database
        List<RequestParameter> requestParameterList = requestParameterRepository.findAll();
        assertThat(requestParameterList).hasSize(databaseSizeBeforeUpdate);
        RequestParameter testRequestParameter = requestParameterList.get(requestParameterList.size() - 1);
        assertThat(testRequestParameter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRequestParameter.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testRequestParameter.getDefaultValue()).isEqualTo(UPDATED_DEFAULT_VALUE);
        assertThat(testRequestParameter.getSection()).isEqualTo(UPDATED_SECTION);
        assertThat(testRequestParameter.isIsMandatory()).isEqualTo(UPDATED_IS_MANDATORY);
    }

    @Test
    @Transactional
    public void updateNonExistingRequestParameter() throws Exception {
        int databaseSizeBeforeUpdate = requestParameterRepository.findAll().size();

        // Create the RequestParameter

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRequestParameterMockMvc.perform(put("/api/request-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestParameter)))
            .andExpect(status().isCreated());

        // Validate the RequestParameter in the database
        List<RequestParameter> requestParameterList = requestParameterRepository.findAll();
        assertThat(requestParameterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRequestParameter() throws Exception {
        // Initialize the database
        requestParameterService.save(requestParameter);

        int databaseSizeBeforeDelete = requestParameterRepository.findAll().size();

        // Get the requestParameter
        restRequestParameterMockMvc.perform(delete("/api/request-parameters/{id}", requestParameter.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RequestParameter> requestParameterList = requestParameterRepository.findAll();
        assertThat(requestParameterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestParameter.class);
        RequestParameter requestParameter1 = new RequestParameter();
        requestParameter1.setId(1L);
        RequestParameter requestParameter2 = new RequestParameter();
        requestParameter2.setId(requestParameter1.getId());
        assertThat(requestParameter1).isEqualTo(requestParameter2);
        requestParameter2.setId(2L);
        assertThat(requestParameter1).isNotEqualTo(requestParameter2);
        requestParameter1.setId(null);
        assertThat(requestParameter1).isNotEqualTo(requestParameter2);
    }
}
