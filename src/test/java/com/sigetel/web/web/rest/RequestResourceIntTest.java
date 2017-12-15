package com.sigetel.web.web.rest;

import com.sigetel.web.OvasApp;

import com.sigetel.web.domain.Request;
import com.sigetel.web.repository.RequestRepository;
import com.sigetel.web.service.RequestService;
import com.sigetel.web.web.rest.errors.ExceptionTranslator;

import org.joda.time.DateTime;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RequestResource REST controller.
 *
 * @see RequestResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OvasApp.class)
public class RequestResourceIntTest {

    private static final Long DEFAULT_REQUEST_AMOUNT = 1L;
    private static final Long UPDATED_REQUEST_AMOUNT = 2L;

    private static final Long DEFAULT_CLIENT_NUMBER = 1L;
    private static final Long UPDATED_CLIENT_NUMBER = 2L;

    private static final Long DEFAULT_ANNEXED_NUMBER = 1L;
    private static final Long UPDATED_ANNEXED_NUMBER = 2L;

    private static final String DEFAULT_CLIENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_REQUEST_STATUS = 1L;
    private static final Long UPDATED_REQUEST_STATUS = 2L;

    private static final String DEFAULT_STATUS_REQUESTED = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_REQUESTED = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT_CONFIRMATION = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_CONFIRMATION = "BBBBBBBBBB";

    private static final DateTime DEFAULT_NEXT_TRY_BY = DateTime.now();
    private static final DateTime UPDATED_NEXT_TRY_BY = DateTime.now();

    private static final LocalDate DEFAULT_DATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_TIME = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RequestService requestService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRequestMockMvc;

    private Request request;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RequestResource requestResource = new RequestResource(requestService);
        this.restRequestMockMvc = MockMvcBuilders.standaloneSetup(requestResource)
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
    public static Request createEntity(EntityManager em) {
        Request request = new Request()
            .requestAmount(DEFAULT_REQUEST_AMOUNT)
            .clientNumber(DEFAULT_CLIENT_NUMBER)
            .annexedNumber(DEFAULT_ANNEXED_NUMBER)
            .clientName(DEFAULT_CLIENT_NAME)
            .requestStatus(DEFAULT_REQUEST_STATUS)
            .statusRequested(DEFAULT_STATUS_REQUESTED)
            .serviceCode(DEFAULT_SERVICE_CODE)
            .eventConfirmation(DEFAULT_EVENT_CONFIRMATION)
            .nextTryBy(DEFAULT_NEXT_TRY_BY.toDate())
            .dateTime(DEFAULT_DATE_TIME);
        return request;
    }

    @Before
    public void initTest() {
        request = createEntity(em);
    }

    @Test
    @Transactional
    public void createRequest() throws Exception {
        int databaseSizeBeforeCreate = requestRepository.findAll().size();

        // Create the Request
        restRequestMockMvc.perform(post("/api/requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(request)))
            .andExpect(status().isCreated());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeCreate + 1);
        Request testRequest = requestList.get(requestList.size() - 1);
        assertThat(testRequest.getRequestAmount()).isEqualTo(DEFAULT_REQUEST_AMOUNT);
        assertThat(testRequest.getClientNumber()).isEqualTo(DEFAULT_CLIENT_NUMBER);
        assertThat(testRequest.getAnnexedNumber()).isEqualTo(DEFAULT_ANNEXED_NUMBER);
        assertThat(testRequest.getClientName()).isEqualTo(DEFAULT_CLIENT_NAME);
        assertThat(testRequest.getRequestStatus()).isEqualTo(DEFAULT_REQUEST_STATUS);
        assertThat(testRequest.getStatusRequested()).isEqualTo(DEFAULT_STATUS_REQUESTED);
        assertThat(testRequest.getServiceCode()).isEqualTo(DEFAULT_SERVICE_CODE);
        assertThat(testRequest.getEventConfirmation()).isEqualTo(DEFAULT_EVENT_CONFIRMATION);
        assertThat(testRequest.getNextTryBy()).isEqualTo(DEFAULT_NEXT_TRY_BY);
        assertThat(testRequest.getDateTime()).isEqualTo(DEFAULT_DATE_TIME);
    }

    @Test
    @Transactional
    public void createRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = requestRepository.findAll().size();

        // Create the Request with an existing ID
        request.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestMockMvc.perform(post("/api/requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(request)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRequestStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestRepository.findAll().size();
        // set the field null
        request.setRequestStatus(null);

        // Create the Request, which fails.

        restRequestMockMvc.perform(post("/api/requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(request)))
            .andExpect(status().isBadRequest());

        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusRequestedIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestRepository.findAll().size();
        // set the field null
        request.setStatusRequested(null);

        // Create the Request, which fails.

        restRequestMockMvc.perform(post("/api/requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(request)))
            .andExpect(status().isBadRequest());

        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkServiceCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestRepository.findAll().size();
        // set the field null
        request.setServiceCode(null);

        // Create the Request, which fails.

        restRequestMockMvc.perform(post("/api/requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(request)))
            .andExpect(status().isBadRequest());

        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRequests() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList
        restRequestMockMvc.perform(get("/api/requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(request.getId().intValue())))
            .andExpect(jsonPath("$.[*].requestAmount").value(hasItem(DEFAULT_REQUEST_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].clientNumber").value(hasItem(DEFAULT_CLIENT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].annexedNumber").value(hasItem(DEFAULT_ANNEXED_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].clientName").value(hasItem(DEFAULT_CLIENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].requestStatus").value(hasItem(DEFAULT_REQUEST_STATUS.intValue())))
            .andExpect(jsonPath("$.[*].statusRequested").value(hasItem(DEFAULT_STATUS_REQUESTED.toString())))
            .andExpect(jsonPath("$.[*].serviceCode").value(hasItem(DEFAULT_SERVICE_CODE.toString())))
            .andExpect(jsonPath("$.[*].eventConfirmation").value(hasItem(DEFAULT_EVENT_CONFIRMATION.toString())))
            .andExpect(jsonPath("$.[*].nextTryBy").value(hasItem(DEFAULT_NEXT_TRY_BY.toString())))
            .andExpect(jsonPath("$.[*].dateTime").value(hasItem(DEFAULT_DATE_TIME.toString())));
    }

    @Test
    @Transactional
    public void getRequest() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get the request
        restRequestMockMvc.perform(get("/api/requests/{id}", request.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(request.getId().intValue()))
            .andExpect(jsonPath("$.requestAmount").value(DEFAULT_REQUEST_AMOUNT.intValue()))
            .andExpect(jsonPath("$.clientNumber").value(DEFAULT_CLIENT_NUMBER.intValue()))
            .andExpect(jsonPath("$.annexedNumber").value(DEFAULT_ANNEXED_NUMBER.intValue()))
            .andExpect(jsonPath("$.clientName").value(DEFAULT_CLIENT_NAME.toString()))
            .andExpect(jsonPath("$.requestStatus").value(DEFAULT_REQUEST_STATUS.intValue()))
            .andExpect(jsonPath("$.statusRequested").value(DEFAULT_STATUS_REQUESTED.toString()))
            .andExpect(jsonPath("$.serviceCode").value(DEFAULT_SERVICE_CODE.toString()))
            .andExpect(jsonPath("$.eventConfirmation").value(DEFAULT_EVENT_CONFIRMATION.toString()))
            .andExpect(jsonPath("$.nextTryBy").value(DEFAULT_NEXT_TRY_BY.toString()))
            .andExpect(jsonPath("$.dateTime").value(DEFAULT_DATE_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRequest() throws Exception {
        // Get the request
        restRequestMockMvc.perform(get("/api/requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRequest() throws Exception {
        // Initialize the database
        requestService.save(request);

        int databaseSizeBeforeUpdate = requestRepository.findAll().size();

        // Update the request
        Request updatedRequest = requestRepository.findOne(request.getId());
        updatedRequest
            .requestAmount(UPDATED_REQUEST_AMOUNT)
            .clientNumber(UPDATED_CLIENT_NUMBER)
            .annexedNumber(UPDATED_ANNEXED_NUMBER)
            .clientName(UPDATED_CLIENT_NAME)
            .requestStatus(UPDATED_REQUEST_STATUS)
            .statusRequested(UPDATED_STATUS_REQUESTED)
            .serviceCode(UPDATED_SERVICE_CODE)
            .eventConfirmation(UPDATED_EVENT_CONFIRMATION)
            .nextTryBy(UPDATED_NEXT_TRY_BY.toDate())
            .dateTime(UPDATED_DATE_TIME);

        restRequestMockMvc.perform(put("/api/requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRequest)))
            .andExpect(status().isOk());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeUpdate);
        Request testRequest = requestList.get(requestList.size() - 1);
        assertThat(testRequest.getRequestAmount()).isEqualTo(UPDATED_REQUEST_AMOUNT);
        assertThat(testRequest.getClientNumber()).isEqualTo(UPDATED_CLIENT_NUMBER);
        assertThat(testRequest.getAnnexedNumber()).isEqualTo(UPDATED_ANNEXED_NUMBER);
        assertThat(testRequest.getClientName()).isEqualTo(UPDATED_CLIENT_NAME);
        assertThat(testRequest.getRequestStatus()).isEqualTo(UPDATED_REQUEST_STATUS);
        assertThat(testRequest.getStatusRequested()).isEqualTo(UPDATED_STATUS_REQUESTED);
        assertThat(testRequest.getServiceCode()).isEqualTo(UPDATED_SERVICE_CODE);
        assertThat(testRequest.getEventConfirmation()).isEqualTo(UPDATED_EVENT_CONFIRMATION);
        assertThat(testRequest.getNextTryBy()).isEqualTo(UPDATED_NEXT_TRY_BY);
        assertThat(testRequest.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingRequest() throws Exception {
        int databaseSizeBeforeUpdate = requestRepository.findAll().size();

        // Create the Request

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRequestMockMvc.perform(put("/api/requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(request)))
            .andExpect(status().isCreated());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRequest() throws Exception {
        // Initialize the database
        requestService.save(request);

        int databaseSizeBeforeDelete = requestRepository.findAll().size();

        // Get the request
        restRequestMockMvc.perform(delete("/api/requests/{id}", request.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Request.class);
        Request request1 = new Request();
        request1.setId(1L);
        Request request2 = new Request();
        request2.setId(request1.getId());
        assertThat(request1).isEqualTo(request2);
        request2.setId(2L);
        assertThat(request1).isNotEqualTo(request2);
        request1.setId(null);
        assertThat(request1).isNotEqualTo(request2);
    }
}
