package com.zhangkem.coopcycle.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.zhangkem.coopcycle.IntegrationTest;
import com.zhangkem.coopcycle.domain.Deliverer;
import com.zhangkem.coopcycle.repository.DelivererRepository;
import com.zhangkem.coopcycle.service.dto.DelivererDTO;
import com.zhangkem.coopcycle.service.mapper.DelivererMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DelivererResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DelivererResourceIT {

    private static final Integer DEFAULT_ID_DELIVRER = 1;
    private static final Integer UPDATED_ID_DELIVRER = 2;

    private static final Integer DEFAULT_ID_COOPERATIVE = 1;
    private static final Integer UPDATED_ID_COOPERATIVE = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final Float DEFAULT_LATITUDE = 1F;
    private static final Float UPDATED_LATITUDE = 2F;

    private static final Float DEFAULT_LONGITUDE = 1F;
    private static final Float UPDATED_LONGITUDE = 2F;

    private static final String ENTITY_API_URL = "/api/deliverers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DelivererRepository delivererRepository;

    @Autowired
    private DelivererMapper delivererMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDelivererMockMvc;

    private Deliverer deliverer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deliverer createEntity(EntityManager em) {
        Deliverer deliverer = new Deliverer()
            .idDelivrer(DEFAULT_ID_DELIVRER)
            .idCooperative(DEFAULT_ID_COOPERATIVE)
            .name(DEFAULT_NAME)
            .surname(DEFAULT_SURNAME)
            .telephone(DEFAULT_TELEPHONE)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE);
        return deliverer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deliverer createUpdatedEntity(EntityManager em) {
        Deliverer deliverer = new Deliverer()
            .idDelivrer(UPDATED_ID_DELIVRER)
            .idCooperative(UPDATED_ID_COOPERATIVE)
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME)
            .telephone(UPDATED_TELEPHONE)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);
        return deliverer;
    }

    @BeforeEach
    public void initTest() {
        deliverer = createEntity(em);
    }

    @Test
    @Transactional
    void createDeliverer() throws Exception {
        int databaseSizeBeforeCreate = delivererRepository.findAll().size();
        // Create the Deliverer
        DelivererDTO delivererDTO = delivererMapper.toDto(deliverer);
        restDelivererMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(delivererDTO)))
            .andExpect(status().isCreated());

        // Validate the Deliverer in the database
        List<Deliverer> delivererList = delivererRepository.findAll();
        assertThat(delivererList).hasSize(databaseSizeBeforeCreate + 1);
        Deliverer testDeliverer = delivererList.get(delivererList.size() - 1);
        assertThat(testDeliverer.getIdDelivrer()).isEqualTo(DEFAULT_ID_DELIVRER);
        assertThat(testDeliverer.getIdCooperative()).isEqualTo(DEFAULT_ID_COOPERATIVE);
        assertThat(testDeliverer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDeliverer.getSurname()).isEqualTo(DEFAULT_SURNAME);
        assertThat(testDeliverer.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testDeliverer.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testDeliverer.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
    }

    @Test
    @Transactional
    void createDelivererWithExistingId() throws Exception {
        // Create the Deliverer with an existing ID
        deliverer.setId(1L);
        DelivererDTO delivererDTO = delivererMapper.toDto(deliverer);

        int databaseSizeBeforeCreate = delivererRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDelivererMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(delivererDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Deliverer in the database
        List<Deliverer> delivererList = delivererRepository.findAll();
        assertThat(delivererList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdDelivrerIsRequired() throws Exception {
        int databaseSizeBeforeTest = delivererRepository.findAll().size();
        // set the field null
        deliverer.setIdDelivrer(null);

        // Create the Deliverer, which fails.
        DelivererDTO delivererDTO = delivererMapper.toDto(deliverer);

        restDelivererMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(delivererDTO)))
            .andExpect(status().isBadRequest());

        List<Deliverer> delivererList = delivererRepository.findAll();
        assertThat(delivererList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = delivererRepository.findAll().size();
        // set the field null
        deliverer.setTelephone(null);

        // Create the Deliverer, which fails.
        DelivererDTO delivererDTO = delivererMapper.toDto(deliverer);

        restDelivererMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(delivererDTO)))
            .andExpect(status().isBadRequest());

        List<Deliverer> delivererList = delivererRepository.findAll();
        assertThat(delivererList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = delivererRepository.findAll().size();
        // set the field null
        deliverer.setLatitude(null);

        // Create the Deliverer, which fails.
        DelivererDTO delivererDTO = delivererMapper.toDto(deliverer);

        restDelivererMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(delivererDTO)))
            .andExpect(status().isBadRequest());

        List<Deliverer> delivererList = delivererRepository.findAll();
        assertThat(delivererList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = delivererRepository.findAll().size();
        // set the field null
        deliverer.setLongitude(null);

        // Create the Deliverer, which fails.
        DelivererDTO delivererDTO = delivererMapper.toDto(deliverer);

        restDelivererMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(delivererDTO)))
            .andExpect(status().isBadRequest());

        List<Deliverer> delivererList = delivererRepository.findAll();
        assertThat(delivererList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDeliverers() throws Exception {
        // Initialize the database
        delivererRepository.saveAndFlush(deliverer);

        // Get all the delivererList
        restDelivererMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deliverer.getId().intValue())))
            .andExpect(jsonPath("$.[*].idDelivrer").value(hasItem(DEFAULT_ID_DELIVRER)))
            .andExpect(jsonPath("$.[*].idCooperative").value(hasItem(DEFAULT_ID_COOPERATIVE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())));
    }

    @Test
    @Transactional
    void getDeliverer() throws Exception {
        // Initialize the database
        delivererRepository.saveAndFlush(deliverer);

        // Get the deliverer
        restDelivererMockMvc
            .perform(get(ENTITY_API_URL_ID, deliverer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deliverer.getId().intValue()))
            .andExpect(jsonPath("$.idDelivrer").value(DEFAULT_ID_DELIVRER))
            .andExpect(jsonPath("$.idCooperative").value(DEFAULT_ID_COOPERATIVE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingDeliverer() throws Exception {
        // Get the deliverer
        restDelivererMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDeliverer() throws Exception {
        // Initialize the database
        delivererRepository.saveAndFlush(deliverer);

        int databaseSizeBeforeUpdate = delivererRepository.findAll().size();

        // Update the deliverer
        Deliverer updatedDeliverer = delivererRepository.findById(deliverer.getId()).get();
        // Disconnect from session so that the updates on updatedDeliverer are not directly saved in db
        em.detach(updatedDeliverer);
        updatedDeliverer
            .idDelivrer(UPDATED_ID_DELIVRER)
            .idCooperative(UPDATED_ID_COOPERATIVE)
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME)
            .telephone(UPDATED_TELEPHONE)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);
        DelivererDTO delivererDTO = delivererMapper.toDto(updatedDeliverer);

        restDelivererMockMvc
            .perform(
                put(ENTITY_API_URL_ID, delivererDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(delivererDTO))
            )
            .andExpect(status().isOk());

        // Validate the Deliverer in the database
        List<Deliverer> delivererList = delivererRepository.findAll();
        assertThat(delivererList).hasSize(databaseSizeBeforeUpdate);
        Deliverer testDeliverer = delivererList.get(delivererList.size() - 1);
        assertThat(testDeliverer.getIdDelivrer()).isEqualTo(UPDATED_ID_DELIVRER);
        assertThat(testDeliverer.getIdCooperative()).isEqualTo(UPDATED_ID_COOPERATIVE);
        assertThat(testDeliverer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDeliverer.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testDeliverer.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testDeliverer.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testDeliverer.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void putNonExistingDeliverer() throws Exception {
        int databaseSizeBeforeUpdate = delivererRepository.findAll().size();
        deliverer.setId(count.incrementAndGet());

        // Create the Deliverer
        DelivererDTO delivererDTO = delivererMapper.toDto(deliverer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDelivererMockMvc
            .perform(
                put(ENTITY_API_URL_ID, delivererDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(delivererDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deliverer in the database
        List<Deliverer> delivererList = delivererRepository.findAll();
        assertThat(delivererList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDeliverer() throws Exception {
        int databaseSizeBeforeUpdate = delivererRepository.findAll().size();
        deliverer.setId(count.incrementAndGet());

        // Create the Deliverer
        DelivererDTO delivererDTO = delivererMapper.toDto(deliverer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDelivererMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(delivererDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deliverer in the database
        List<Deliverer> delivererList = delivererRepository.findAll();
        assertThat(delivererList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDeliverer() throws Exception {
        int databaseSizeBeforeUpdate = delivererRepository.findAll().size();
        deliverer.setId(count.incrementAndGet());

        // Create the Deliverer
        DelivererDTO delivererDTO = delivererMapper.toDto(deliverer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDelivererMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(delivererDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Deliverer in the database
        List<Deliverer> delivererList = delivererRepository.findAll();
        assertThat(delivererList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDelivererWithPatch() throws Exception {
        // Initialize the database
        delivererRepository.saveAndFlush(deliverer);

        int databaseSizeBeforeUpdate = delivererRepository.findAll().size();

        // Update the deliverer using partial update
        Deliverer partialUpdatedDeliverer = new Deliverer();
        partialUpdatedDeliverer.setId(deliverer.getId());

        partialUpdatedDeliverer
            .idCooperative(UPDATED_ID_COOPERATIVE)
            .surname(UPDATED_SURNAME)
            .telephone(UPDATED_TELEPHONE)
            .latitude(UPDATED_LATITUDE);

        restDelivererMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeliverer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeliverer))
            )
            .andExpect(status().isOk());

        // Validate the Deliverer in the database
        List<Deliverer> delivererList = delivererRepository.findAll();
        assertThat(delivererList).hasSize(databaseSizeBeforeUpdate);
        Deliverer testDeliverer = delivererList.get(delivererList.size() - 1);
        assertThat(testDeliverer.getIdDelivrer()).isEqualTo(DEFAULT_ID_DELIVRER);
        assertThat(testDeliverer.getIdCooperative()).isEqualTo(UPDATED_ID_COOPERATIVE);
        assertThat(testDeliverer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDeliverer.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testDeliverer.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testDeliverer.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testDeliverer.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
    }

    @Test
    @Transactional
    void fullUpdateDelivererWithPatch() throws Exception {
        // Initialize the database
        delivererRepository.saveAndFlush(deliverer);

        int databaseSizeBeforeUpdate = delivererRepository.findAll().size();

        // Update the deliverer using partial update
        Deliverer partialUpdatedDeliverer = new Deliverer();
        partialUpdatedDeliverer.setId(deliverer.getId());

        partialUpdatedDeliverer
            .idDelivrer(UPDATED_ID_DELIVRER)
            .idCooperative(UPDATED_ID_COOPERATIVE)
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME)
            .telephone(UPDATED_TELEPHONE)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);

        restDelivererMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeliverer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeliverer))
            )
            .andExpect(status().isOk());

        // Validate the Deliverer in the database
        List<Deliverer> delivererList = delivererRepository.findAll();
        assertThat(delivererList).hasSize(databaseSizeBeforeUpdate);
        Deliverer testDeliverer = delivererList.get(delivererList.size() - 1);
        assertThat(testDeliverer.getIdDelivrer()).isEqualTo(UPDATED_ID_DELIVRER);
        assertThat(testDeliverer.getIdCooperative()).isEqualTo(UPDATED_ID_COOPERATIVE);
        assertThat(testDeliverer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDeliverer.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testDeliverer.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testDeliverer.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testDeliverer.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void patchNonExistingDeliverer() throws Exception {
        int databaseSizeBeforeUpdate = delivererRepository.findAll().size();
        deliverer.setId(count.incrementAndGet());

        // Create the Deliverer
        DelivererDTO delivererDTO = delivererMapper.toDto(deliverer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDelivererMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, delivererDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(delivererDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deliverer in the database
        List<Deliverer> delivererList = delivererRepository.findAll();
        assertThat(delivererList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDeliverer() throws Exception {
        int databaseSizeBeforeUpdate = delivererRepository.findAll().size();
        deliverer.setId(count.incrementAndGet());

        // Create the Deliverer
        DelivererDTO delivererDTO = delivererMapper.toDto(deliverer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDelivererMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(delivererDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deliverer in the database
        List<Deliverer> delivererList = delivererRepository.findAll();
        assertThat(delivererList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDeliverer() throws Exception {
        int databaseSizeBeforeUpdate = delivererRepository.findAll().size();
        deliverer.setId(count.incrementAndGet());

        // Create the Deliverer
        DelivererDTO delivererDTO = delivererMapper.toDto(deliverer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDelivererMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(delivererDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Deliverer in the database
        List<Deliverer> delivererList = delivererRepository.findAll();
        assertThat(delivererList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDeliverer() throws Exception {
        // Initialize the database
        delivererRepository.saveAndFlush(deliverer);

        int databaseSizeBeforeDelete = delivererRepository.findAll().size();

        // Delete the deliverer
        restDelivererMockMvc
            .perform(delete(ENTITY_API_URL_ID, deliverer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Deliverer> delivererList = delivererRepository.findAll();
        assertThat(delivererList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
