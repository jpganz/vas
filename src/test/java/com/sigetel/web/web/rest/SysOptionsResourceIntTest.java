package com.sigetel.web.web.rest;

import com.sigetel.web.OvasApp;

import com.sigetel.web.domain.SysOptions;
import com.sigetel.web.repository.SysOptionsRepository;
import com.sigetel.web.service.SysOptionsService;
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
 * Test class for the SysOptionsResource REST controller.
 *
 * @see SysOptionsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OvasApp.class)
public class SysOptionsResourceIntTest {

    private static final String DEFAULT_OPTION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OPTION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_OPTION_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_OPTION_VALUE = "BBBBBBBBBB";

    @Autowired
    private SysOptionsRepository sysOptionsRepository;

    @Autowired
    private SysOptionsService sysOptionsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSysOptionsMockMvc;

    private SysOptions sysOptions;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SysOptionsResource sysOptionsResource = new SysOptionsResource(sysOptionsService);
        this.restSysOptionsMockMvc = MockMvcBuilders.standaloneSetup(sysOptionsResource)
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
    public static SysOptions createEntity(EntityManager em) {
        SysOptions sysOptions = new SysOptions()
            .option_name(DEFAULT_OPTION_NAME)
            .option_value(DEFAULT_OPTION_VALUE);
        return sysOptions;
    }

    @Before
    public void initTest() {
        sysOptions = createEntity(em);
    }

    @Test
    @Transactional
    public void createSysOptions() throws Exception {
        int databaseSizeBeforeCreate = sysOptionsRepository.findAll().size();

        // Create the SysOptions
        restSysOptionsMockMvc.perform(post("/api/sys-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysOptions)))
            .andExpect(status().isCreated());

        // Validate the SysOptions in the database
        List<SysOptions> sysOptionsList = sysOptionsRepository.findAll();
        assertThat(sysOptionsList).hasSize(databaseSizeBeforeCreate + 1);
        SysOptions testSysOptions = sysOptionsList.get(sysOptionsList.size() - 1);
        assertThat(testSysOptions.getOption_name()).isEqualTo(DEFAULT_OPTION_NAME);
        assertThat(testSysOptions.getOption_value()).isEqualTo(DEFAULT_OPTION_VALUE);
    }

    @Test
    @Transactional
    public void createSysOptionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sysOptionsRepository.findAll().size();

        // Create the SysOptions with an existing ID
        sysOptions.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSysOptionsMockMvc.perform(post("/api/sys-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysOptions)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SysOptions> sysOptionsList = sysOptionsRepository.findAll();
        assertThat(sysOptionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkOption_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sysOptionsRepository.findAll().size();
        // set the field null
        sysOptions.setOption_name(null);

        // Create the SysOptions, which fails.

        restSysOptionsMockMvc.perform(post("/api/sys-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysOptions)))
            .andExpect(status().isBadRequest());

        List<SysOptions> sysOptionsList = sysOptionsRepository.findAll();
        assertThat(sysOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOption_valueIsRequired() throws Exception {
        int databaseSizeBeforeTest = sysOptionsRepository.findAll().size();
        // set the field null
        sysOptions.setOption_value(null);

        // Create the SysOptions, which fails.

        restSysOptionsMockMvc.perform(post("/api/sys-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysOptions)))
            .andExpect(status().isBadRequest());

        List<SysOptions> sysOptionsList = sysOptionsRepository.findAll();
        assertThat(sysOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSysOptions() throws Exception {
        // Initialize the database
        sysOptionsRepository.saveAndFlush(sysOptions);

        // Get all the sysOptionsList
        restSysOptionsMockMvc.perform(get("/api/sys-options?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysOptions.getId().intValue())))
            .andExpect(jsonPath("$.[*].option_name").value(hasItem(DEFAULT_OPTION_NAME.toString())))
            .andExpect(jsonPath("$.[*].option_value").value(hasItem(DEFAULT_OPTION_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getSysOptions() throws Exception {
        // Initialize the database
        sysOptionsRepository.saveAndFlush(sysOptions);

        // Get the sysOptions
        restSysOptionsMockMvc.perform(get("/api/sys-options/{id}", sysOptions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sysOptions.getId().intValue()))
            .andExpect(jsonPath("$.option_name").value(DEFAULT_OPTION_NAME.toString()))
            .andExpect(jsonPath("$.option_value").value(DEFAULT_OPTION_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSysOptions() throws Exception {
        // Get the sysOptions
        restSysOptionsMockMvc.perform(get("/api/sys-options/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSysOptions() throws Exception {
        // Initialize the database
        sysOptionsService.save(sysOptions);

        int databaseSizeBeforeUpdate = sysOptionsRepository.findAll().size();

        // Update the sysOptions
        SysOptions updatedSysOptions = sysOptionsRepository.findOne(sysOptions.getId());
        updatedSysOptions
            .option_name(UPDATED_OPTION_NAME)
            .option_value(UPDATED_OPTION_VALUE);

        restSysOptionsMockMvc.perform(put("/api/sys-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSysOptions)))
            .andExpect(status().isOk());

        // Validate the SysOptions in the database
        List<SysOptions> sysOptionsList = sysOptionsRepository.findAll();
        assertThat(sysOptionsList).hasSize(databaseSizeBeforeUpdate);
        SysOptions testSysOptions = sysOptionsList.get(sysOptionsList.size() - 1);
        assertThat(testSysOptions.getOption_name()).isEqualTo(UPDATED_OPTION_NAME);
        assertThat(testSysOptions.getOption_value()).isEqualTo(UPDATED_OPTION_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingSysOptions() throws Exception {
        int databaseSizeBeforeUpdate = sysOptionsRepository.findAll().size();

        // Create the SysOptions

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSysOptionsMockMvc.perform(put("/api/sys-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysOptions)))
            .andExpect(status().isCreated());

        // Validate the SysOptions in the database
        List<SysOptions> sysOptionsList = sysOptionsRepository.findAll();
        assertThat(sysOptionsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSysOptions() throws Exception {
        // Initialize the database
        sysOptionsService.save(sysOptions);

        int databaseSizeBeforeDelete = sysOptionsRepository.findAll().size();

        // Get the sysOptions
        restSysOptionsMockMvc.perform(delete("/api/sys-options/{id}", sysOptions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SysOptions> sysOptionsList = sysOptionsRepository.findAll();
        assertThat(sysOptionsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysOptions.class);
        SysOptions sysOptions1 = new SysOptions();
        sysOptions1.setId(1L);
        SysOptions sysOptions2 = new SysOptions();
        sysOptions2.setId(sysOptions1.getId());
        assertThat(sysOptions1).isEqualTo(sysOptions2);
        sysOptions2.setId(2L);
        assertThat(sysOptions1).isNotEqualTo(sysOptions2);
        sysOptions1.setId(null);
        assertThat(sysOptions1).isNotEqualTo(sysOptions2);
    }
}
