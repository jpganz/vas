package com.sigetel.web.web.rest;

import com.sigetel.web.OvasApp;

import com.sigetel.web.domain.ProviderCommand;
import com.sigetel.web.repository.ProviderCommandRepository;
import com.sigetel.web.service.ProviderCommandService;
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
 * Test class for the ProviderCommandResource REST controller.
 *
 * @see ProviderCommandResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OvasApp.class)
public class ProviderCommandResourceIntTest {

    private static final String DEFAULT_ENDPOINT_URL = "AAAAAAAAAA";
    private static final String UPDATED_ENDPOINT_URL = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_RETRY_LIMIT = 1L;
    private static final Long UPDATED_RETRY_LIMIT = 2L;

    private static final Long DEFAULT_RETRY_INTERVAL = 1L;
    private static final Long UPDATED_RETRY_INTERVAL = 2L;

    private static final String DEFAULT_EMAIL_ADDRESS_TO_NOTIFY = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ADDRESS_TO_NOTIFY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ADD_TO_RETRY = false;
    private static final Boolean UPDATED_ADD_TO_RETRY = true;

    private static final Integer DEFAULT_EMAIL_NOTIFY = 1;
    private static final Integer UPDATED_EMAIL_NOTIFY = 2;

    @Autowired
    private ProviderCommandRepository providerCommandRepository;

    @Autowired
    private ProviderCommandService providerCommandService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProviderCommandMockMvc;

    private ProviderCommand providerCommand;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProviderCommandResource providerCommandResource = new ProviderCommandResource(providerCommandService);
        this.restProviderCommandMockMvc = MockMvcBuilders.standaloneSetup(providerCommandResource)
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
    public static ProviderCommand createEntity(EntityManager em) {
        ProviderCommand providerCommand = new ProviderCommand()
            .endpointUrl(DEFAULT_ENDPOINT_URL)
            .code(DEFAULT_CODE)
            .retryLimit(DEFAULT_RETRY_LIMIT)
            .retryInterval(DEFAULT_RETRY_INTERVAL)
            .emailAddressToNotify(DEFAULT_EMAIL_ADDRESS_TO_NOTIFY)
            .addToRetry(DEFAULT_ADD_TO_RETRY)
            .emailNotify(DEFAULT_EMAIL_NOTIFY);
        return providerCommand;
    }

    @Before
    public void initTest() {
        providerCommand = createEntity(em);
    }

    @Test
    @Transactional
    public void createProviderCommand() throws Exception {
        int databaseSizeBeforeCreate = providerCommandRepository.findAll().size();

        // Create the ProviderCommand
        restProviderCommandMockMvc.perform(post("/api/provider-commands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerCommand)))
            .andExpect(status().isCreated());

        // Validate the ProviderCommand in the database
        List<ProviderCommand> providerCommandList = providerCommandRepository.findAll();
        assertThat(providerCommandList).hasSize(databaseSizeBeforeCreate + 1);
        ProviderCommand testProviderCommand = providerCommandList.get(providerCommandList.size() - 1);
        assertThat(testProviderCommand.getEndpointUrl()).isEqualTo(DEFAULT_ENDPOINT_URL);
        assertThat(testProviderCommand.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProviderCommand.getRetryLimit()).isEqualTo(DEFAULT_RETRY_LIMIT);
        assertThat(testProviderCommand.getRetryInterval()).isEqualTo(DEFAULT_RETRY_INTERVAL);
        assertThat(testProviderCommand.getEmailAddressToNotify()).isEqualTo(DEFAULT_EMAIL_ADDRESS_TO_NOTIFY);
        assertThat(testProviderCommand.isAddToRetry()).isEqualTo(DEFAULT_ADD_TO_RETRY);
        assertThat(testProviderCommand.getEmailNotify()).isEqualTo(DEFAULT_EMAIL_NOTIFY);
    }

    @Test
    @Transactional
    public void createProviderCommandWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = providerCommandRepository.findAll().size();

        // Create the ProviderCommand with an existing ID
        providerCommand.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProviderCommandMockMvc.perform(post("/api/provider-commands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerCommand)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProviderCommand> providerCommandList = providerCommandRepository.findAll();
        assertThat(providerCommandList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEndpointUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = providerCommandRepository.findAll().size();
        // set the field null
        providerCommand.setEndpointUrl(null);

        // Create the ProviderCommand, which fails.

        restProviderCommandMockMvc.perform(post("/api/provider-commands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerCommand)))
            .andExpect(status().isBadRequest());

        List<ProviderCommand> providerCommandList = providerCommandRepository.findAll();
        assertThat(providerCommandList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRetryLimitIsRequired() throws Exception {
        int databaseSizeBeforeTest = providerCommandRepository.findAll().size();
        // set the field null
        providerCommand.setRetryLimit(null);

        // Create the ProviderCommand, which fails.

        restProviderCommandMockMvc.perform(post("/api/provider-commands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerCommand)))
            .andExpect(status().isBadRequest());

        List<ProviderCommand> providerCommandList = providerCommandRepository.findAll();
        assertThat(providerCommandList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRetryIntervalIsRequired() throws Exception {
        int databaseSizeBeforeTest = providerCommandRepository.findAll().size();
        // set the field null
        providerCommand.setRetryInterval(null);

        // Create the ProviderCommand, which fails.

        restProviderCommandMockMvc.perform(post("/api/provider-commands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerCommand)))
            .andExpect(status().isBadRequest());

        List<ProviderCommand> providerCommandList = providerCommandRepository.findAll();
        assertThat(providerCommandList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProviderCommands() throws Exception {
        // Initialize the database
        providerCommandRepository.saveAndFlush(providerCommand);

        // Get all the providerCommandList
        restProviderCommandMockMvc.perform(get("/api/provider-commands?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(providerCommand.getId().intValue())))
            .andExpect(jsonPath("$.[*].endpointUrl").value(hasItem(DEFAULT_ENDPOINT_URL.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].retryLimit").value(hasItem(DEFAULT_RETRY_LIMIT.intValue())))
            .andExpect(jsonPath("$.[*].retryInterval").value(hasItem(DEFAULT_RETRY_INTERVAL.intValue())))
            .andExpect(jsonPath("$.[*].emailAddressToNotify").value(hasItem(DEFAULT_EMAIL_ADDRESS_TO_NOTIFY.toString())))
            .andExpect(jsonPath("$.[*].addToRetry").value(hasItem(DEFAULT_ADD_TO_RETRY.booleanValue())))
            .andExpect(jsonPath("$.[*].emailNotify").value(hasItem(DEFAULT_EMAIL_NOTIFY)));
    }

    @Test
    @Transactional
    public void getProviderCommand() throws Exception {
        // Initialize the database
        providerCommandRepository.saveAndFlush(providerCommand);

        // Get the providerCommand
        restProviderCommandMockMvc.perform(get("/api/provider-commands/{id}", providerCommand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(providerCommand.getId().intValue()))
            .andExpect(jsonPath("$.endpointUrl").value(DEFAULT_ENDPOINT_URL.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.retryLimit").value(DEFAULT_RETRY_LIMIT.intValue()))
            .andExpect(jsonPath("$.retryInterval").value(DEFAULT_RETRY_INTERVAL.intValue()))
            .andExpect(jsonPath("$.emailAddressToNotify").value(DEFAULT_EMAIL_ADDRESS_TO_NOTIFY.toString()))
            .andExpect(jsonPath("$.addToRetry").value(DEFAULT_ADD_TO_RETRY.booleanValue()))
            .andExpect(jsonPath("$.emailNotify").value(DEFAULT_EMAIL_NOTIFY));
    }

    @Test
    @Transactional
    public void getNonExistingProviderCommand() throws Exception {
        // Get the providerCommand
        restProviderCommandMockMvc.perform(get("/api/provider-commands/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProviderCommand() throws Exception {
        // Initialize the database
        providerCommandService.save(providerCommand);

        int databaseSizeBeforeUpdate = providerCommandRepository.findAll().size();

        // Update the providerCommand
        ProviderCommand updatedProviderCommand = providerCommandRepository.findOne(providerCommand.getId());
        updatedProviderCommand
            .endpointUrl(UPDATED_ENDPOINT_URL)
            .code(UPDATED_CODE)
            .retryLimit(UPDATED_RETRY_LIMIT)
            .retryInterval(UPDATED_RETRY_INTERVAL)
            .emailAddressToNotify(UPDATED_EMAIL_ADDRESS_TO_NOTIFY)
            .addToRetry(UPDATED_ADD_TO_RETRY)
            .emailNotify(UPDATED_EMAIL_NOTIFY);

        restProviderCommandMockMvc.perform(put("/api/provider-commands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProviderCommand)))
            .andExpect(status().isOk());

        // Validate the ProviderCommand in the database
        List<ProviderCommand> providerCommandList = providerCommandRepository.findAll();
        assertThat(providerCommandList).hasSize(databaseSizeBeforeUpdate);
        ProviderCommand testProviderCommand = providerCommandList.get(providerCommandList.size() - 1);
        assertThat(testProviderCommand.getEndpointUrl()).isEqualTo(UPDATED_ENDPOINT_URL);
        assertThat(testProviderCommand.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProviderCommand.getRetryLimit()).isEqualTo(UPDATED_RETRY_LIMIT);
        assertThat(testProviderCommand.getRetryInterval()).isEqualTo(UPDATED_RETRY_INTERVAL);
        assertThat(testProviderCommand.getEmailAddressToNotify()).isEqualTo(UPDATED_EMAIL_ADDRESS_TO_NOTIFY);
        assertThat(testProviderCommand.isAddToRetry()).isEqualTo(UPDATED_ADD_TO_RETRY);
        assertThat(testProviderCommand.getEmailNotify()).isEqualTo(UPDATED_EMAIL_NOTIFY);
    }

    @Test
    @Transactional
    public void updateNonExistingProviderCommand() throws Exception {
        int databaseSizeBeforeUpdate = providerCommandRepository.findAll().size();

        // Create the ProviderCommand

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProviderCommandMockMvc.perform(put("/api/provider-commands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerCommand)))
            .andExpect(status().isCreated());

        // Validate the ProviderCommand in the database
        List<ProviderCommand> providerCommandList = providerCommandRepository.findAll();
        assertThat(providerCommandList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProviderCommand() throws Exception {
        // Initialize the database
        providerCommandService.save(providerCommand);

        int databaseSizeBeforeDelete = providerCommandRepository.findAll().size();

        // Get the providerCommand
        restProviderCommandMockMvc.perform(delete("/api/provider-commands/{id}", providerCommand.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProviderCommand> providerCommandList = providerCommandRepository.findAll();
        assertThat(providerCommandList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProviderCommand.class);
        ProviderCommand providerCommand1 = new ProviderCommand();
        providerCommand1.setId(1L);
        ProviderCommand providerCommand2 = new ProviderCommand();
        providerCommand2.setId(providerCommand1.getId());
        assertThat(providerCommand1).isEqualTo(providerCommand2);
        providerCommand2.setId(2L);
        assertThat(providerCommand1).isNotEqualTo(providerCommand2);
        providerCommand1.setId(null);
        assertThat(providerCommand1).isNotEqualTo(providerCommand2);
    }
}
