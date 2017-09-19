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

    private static final Long DEFAULT_PROVIDER_ID = 1L;
    private static final Long UPDATED_PROVIDER_ID = 2L;

    private static final Long DEFAULT_COMMAND_ID = 1L;
    private static final Long UPDATED_COMMAND_ID = 2L;

    private static final Long DEFAULT_COMMUNICATION_STANDARD_ID = 1L;
    private static final Long UPDATED_COMMUNICATION_STANDARD_ID = 2L;

    private static final Long DEFAULT_SERVICE_SECURITY_ID = 1L;
    private static final Long UPDATED_SERVICE_SECURITY_ID = 2L;

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
            .providerId(DEFAULT_PROVIDER_ID)
            .commandId(DEFAULT_COMMAND_ID)
            .communicationStandardId(DEFAULT_COMMUNICATION_STANDARD_ID)
            .serviceSecurityId(DEFAULT_SERVICE_SECURITY_ID);
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
        assertThat(testProviderCommand.getProviderId()).isEqualTo(DEFAULT_PROVIDER_ID);
        assertThat(testProviderCommand.getCommandId()).isEqualTo(DEFAULT_COMMAND_ID);
        assertThat(testProviderCommand.getCommunicationStandardId()).isEqualTo(DEFAULT_COMMUNICATION_STANDARD_ID);
        assertThat(testProviderCommand.getServiceSecurityId()).isEqualTo(DEFAULT_SERVICE_SECURITY_ID);
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
    public void checkProviderIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = providerCommandRepository.findAll().size();
        // set the field null
        providerCommand.setProviderId(null);

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
    public void checkCommandIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = providerCommandRepository.findAll().size();
        // set the field null
        providerCommand.setCommandId(null);

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
    public void checkCommunicationStandardIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = providerCommandRepository.findAll().size();
        // set the field null
        providerCommand.setCommunicationStandardId(null);

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
    public void checkServiceSecurityIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = providerCommandRepository.findAll().size();
        // set the field null
        providerCommand.setServiceSecurityId(null);

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
            .andExpect(jsonPath("$.[*].providerId").value(hasItem(DEFAULT_PROVIDER_ID.intValue())))
            .andExpect(jsonPath("$.[*].commandId").value(hasItem(DEFAULT_COMMAND_ID.intValue())))
            .andExpect(jsonPath("$.[*].communicationStandardId").value(hasItem(DEFAULT_COMMUNICATION_STANDARD_ID.intValue())))
            .andExpect(jsonPath("$.[*].serviceSecurityId").value(hasItem(DEFAULT_SERVICE_SECURITY_ID.intValue())));
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
            .andExpect(jsonPath("$.providerId").value(DEFAULT_PROVIDER_ID.intValue()))
            .andExpect(jsonPath("$.commandId").value(DEFAULT_COMMAND_ID.intValue()))
            .andExpect(jsonPath("$.communicationStandardId").value(DEFAULT_COMMUNICATION_STANDARD_ID.intValue()))
            .andExpect(jsonPath("$.serviceSecurityId").value(DEFAULT_SERVICE_SECURITY_ID.intValue()));
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
            .providerId(UPDATED_PROVIDER_ID)
            .commandId(UPDATED_COMMAND_ID)
            .communicationStandardId(UPDATED_COMMUNICATION_STANDARD_ID)
            .serviceSecurityId(UPDATED_SERVICE_SECURITY_ID);

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
        assertThat(testProviderCommand.getProviderId()).isEqualTo(UPDATED_PROVIDER_ID);
        assertThat(testProviderCommand.getCommandId()).isEqualTo(UPDATED_COMMAND_ID);
        assertThat(testProviderCommand.getCommunicationStandardId()).isEqualTo(UPDATED_COMMUNICATION_STANDARD_ID);
        assertThat(testProviderCommand.getServiceSecurityId()).isEqualTo(UPDATED_SERVICE_SECURITY_ID);
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
