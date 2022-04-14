package com.zhangkem.coopcycle.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.zhangkem.coopcycle.IntegrationTest;
import com.zhangkem.coopcycle.domain.OrderContent;
import com.zhangkem.coopcycle.domain.Product;
import com.zhangkem.coopcycle.repository.OrderContentRepository;
import com.zhangkem.coopcycle.service.OrderContentService;
import com.zhangkem.coopcycle.service.dto.OrderContentDTO;
import com.zhangkem.coopcycle.service.mapper.OrderContentMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OrderContentResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class OrderContentResourceIT {

    private static final Integer DEFAULT_ID_PRODUCT = 1;
    private static final Integer UPDATED_ID_PRODUCT = 2;

    private static final Integer DEFAULT_ID_ORDER = 1;
    private static final Integer UPDATED_ID_ORDER = 2;

    private static final Integer DEFAULT_QUANTITY_ASKED = 1;
    private static final Integer UPDATED_QUANTITY_ASKED = 2;

    private static final Boolean DEFAULT_PRODUCT_AVAILABLE = false;
    private static final Boolean UPDATED_PRODUCT_AVAILABLE = true;

    private static final String ENTITY_API_URL = "/api/order-contents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrderContentRepository orderContentRepository;

    @Mock
    private OrderContentRepository orderContentRepositoryMock;

    @Autowired
    private OrderContentMapper orderContentMapper;

    @Mock
    private OrderContentService orderContentServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderContentMockMvc;

    private OrderContent orderContent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderContent createEntity(EntityManager em) {
        OrderContent orderContent = new OrderContent()
            .idProduct(DEFAULT_ID_PRODUCT)
            .idOrder(DEFAULT_ID_ORDER)
            .quantityAsked(DEFAULT_QUANTITY_ASKED)
            .productAvailable(DEFAULT_PRODUCT_AVAILABLE);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        orderContent.getProducts().add(product);
        return orderContent;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderContent createUpdatedEntity(EntityManager em) {
        OrderContent orderContent = new OrderContent()
            .idProduct(UPDATED_ID_PRODUCT)
            .idOrder(UPDATED_ID_ORDER)
            .quantityAsked(UPDATED_QUANTITY_ASKED)
            .productAvailable(UPDATED_PRODUCT_AVAILABLE);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createUpdatedEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        orderContent.getProducts().add(product);
        return orderContent;
    }

    @BeforeEach
    public void initTest() {
        orderContent = createEntity(em);
    }

    @Test
    @Transactional
    void createOrderContent() throws Exception {
        int databaseSizeBeforeCreate = orderContentRepository.findAll().size();
        // Create the OrderContent
        OrderContentDTO orderContentDTO = orderContentMapper.toDto(orderContent);
        restOrderContentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderContentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OrderContent in the database
        List<OrderContent> orderContentList = orderContentRepository.findAll();
        assertThat(orderContentList).hasSize(databaseSizeBeforeCreate + 1);
        OrderContent testOrderContent = orderContentList.get(orderContentList.size() - 1);
        assertThat(testOrderContent.getIdProduct()).isEqualTo(DEFAULT_ID_PRODUCT);
        assertThat(testOrderContent.getIdOrder()).isEqualTo(DEFAULT_ID_ORDER);
        assertThat(testOrderContent.getQuantityAsked()).isEqualTo(DEFAULT_QUANTITY_ASKED);
        assertThat(testOrderContent.getProductAvailable()).isEqualTo(DEFAULT_PRODUCT_AVAILABLE);
    }

    @Test
    @Transactional
    void createOrderContentWithExistingId() throws Exception {
        // Create the OrderContent with an existing ID
        orderContent.setId(1L);
        OrderContentDTO orderContentDTO = orderContentMapper.toDto(orderContent);

        int databaseSizeBeforeCreate = orderContentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderContentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderContentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderContent in the database
        List<OrderContent> orderContentList = orderContentRepository.findAll();
        assertThat(orderContentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdProductIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderContentRepository.findAll().size();
        // set the field null
        orderContent.setIdProduct(null);

        // Create the OrderContent, which fails.
        OrderContentDTO orderContentDTO = orderContentMapper.toDto(orderContent);

        restOrderContentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderContentDTO))
            )
            .andExpect(status().isBadRequest());

        List<OrderContent> orderContentList = orderContentRepository.findAll();
        assertThat(orderContentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderContentRepository.findAll().size();
        // set the field null
        orderContent.setIdOrder(null);

        // Create the OrderContent, which fails.
        OrderContentDTO orderContentDTO = orderContentMapper.toDto(orderContent);

        restOrderContentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderContentDTO))
            )
            .andExpect(status().isBadRequest());

        List<OrderContent> orderContentList = orderContentRepository.findAll();
        assertThat(orderContentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrderContents() throws Exception {
        // Initialize the database
        orderContentRepository.saveAndFlush(orderContent);

        // Get all the orderContentList
        restOrderContentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderContent.getId().intValue())))
            .andExpect(jsonPath("$.[*].idProduct").value(hasItem(DEFAULT_ID_PRODUCT)))
            .andExpect(jsonPath("$.[*].idOrder").value(hasItem(DEFAULT_ID_ORDER)))
            .andExpect(jsonPath("$.[*].quantityAsked").value(hasItem(DEFAULT_QUANTITY_ASKED)))
            .andExpect(jsonPath("$.[*].productAvailable").value(hasItem(DEFAULT_PRODUCT_AVAILABLE.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllOrderContentsWithEagerRelationshipsIsEnabled() throws Exception {
        when(orderContentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOrderContentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(orderContentServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllOrderContentsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(orderContentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOrderContentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(orderContentServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getOrderContent() throws Exception {
        // Initialize the database
        orderContentRepository.saveAndFlush(orderContent);

        // Get the orderContent
        restOrderContentMockMvc
            .perform(get(ENTITY_API_URL_ID, orderContent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderContent.getId().intValue()))
            .andExpect(jsonPath("$.idProduct").value(DEFAULT_ID_PRODUCT))
            .andExpect(jsonPath("$.idOrder").value(DEFAULT_ID_ORDER))
            .andExpect(jsonPath("$.quantityAsked").value(DEFAULT_QUANTITY_ASKED))
            .andExpect(jsonPath("$.productAvailable").value(DEFAULT_PRODUCT_AVAILABLE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingOrderContent() throws Exception {
        // Get the orderContent
        restOrderContentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrderContent() throws Exception {
        // Initialize the database
        orderContentRepository.saveAndFlush(orderContent);

        int databaseSizeBeforeUpdate = orderContentRepository.findAll().size();

        // Update the orderContent
        OrderContent updatedOrderContent = orderContentRepository.findById(orderContent.getId()).get();
        // Disconnect from session so that the updates on updatedOrderContent are not directly saved in db
        em.detach(updatedOrderContent);
        updatedOrderContent
            .idProduct(UPDATED_ID_PRODUCT)
            .idOrder(UPDATED_ID_ORDER)
            .quantityAsked(UPDATED_QUANTITY_ASKED)
            .productAvailable(UPDATED_PRODUCT_AVAILABLE);
        OrderContentDTO orderContentDTO = orderContentMapper.toDto(updatedOrderContent);

        restOrderContentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderContentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderContentDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrderContent in the database
        List<OrderContent> orderContentList = orderContentRepository.findAll();
        assertThat(orderContentList).hasSize(databaseSizeBeforeUpdate);
        OrderContent testOrderContent = orderContentList.get(orderContentList.size() - 1);
        assertThat(testOrderContent.getIdProduct()).isEqualTo(UPDATED_ID_PRODUCT);
        assertThat(testOrderContent.getIdOrder()).isEqualTo(UPDATED_ID_ORDER);
        assertThat(testOrderContent.getQuantityAsked()).isEqualTo(UPDATED_QUANTITY_ASKED);
        assertThat(testOrderContent.getProductAvailable()).isEqualTo(UPDATED_PRODUCT_AVAILABLE);
    }

    @Test
    @Transactional
    void putNonExistingOrderContent() throws Exception {
        int databaseSizeBeforeUpdate = orderContentRepository.findAll().size();
        orderContent.setId(count.incrementAndGet());

        // Create the OrderContent
        OrderContentDTO orderContentDTO = orderContentMapper.toDto(orderContent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderContentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderContentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderContentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderContent in the database
        List<OrderContent> orderContentList = orderContentRepository.findAll();
        assertThat(orderContentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrderContent() throws Exception {
        int databaseSizeBeforeUpdate = orderContentRepository.findAll().size();
        orderContent.setId(count.incrementAndGet());

        // Create the OrderContent
        OrderContentDTO orderContentDTO = orderContentMapper.toDto(orderContent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderContentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderContentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderContent in the database
        List<OrderContent> orderContentList = orderContentRepository.findAll();
        assertThat(orderContentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrderContent() throws Exception {
        int databaseSizeBeforeUpdate = orderContentRepository.findAll().size();
        orderContent.setId(count.incrementAndGet());

        // Create the OrderContent
        OrderContentDTO orderContentDTO = orderContentMapper.toDto(orderContent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderContentMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderContentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderContent in the database
        List<OrderContent> orderContentList = orderContentRepository.findAll();
        assertThat(orderContentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrderContentWithPatch() throws Exception {
        // Initialize the database
        orderContentRepository.saveAndFlush(orderContent);

        int databaseSizeBeforeUpdate = orderContentRepository.findAll().size();

        // Update the orderContent using partial update
        OrderContent partialUpdatedOrderContent = new OrderContent();
        partialUpdatedOrderContent.setId(orderContent.getId());

        partialUpdatedOrderContent.idProduct(UPDATED_ID_PRODUCT).idOrder(UPDATED_ID_ORDER).productAvailable(UPDATED_PRODUCT_AVAILABLE);

        restOrderContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderContent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderContent))
            )
            .andExpect(status().isOk());

        // Validate the OrderContent in the database
        List<OrderContent> orderContentList = orderContentRepository.findAll();
        assertThat(orderContentList).hasSize(databaseSizeBeforeUpdate);
        OrderContent testOrderContent = orderContentList.get(orderContentList.size() - 1);
        assertThat(testOrderContent.getIdProduct()).isEqualTo(UPDATED_ID_PRODUCT);
        assertThat(testOrderContent.getIdOrder()).isEqualTo(UPDATED_ID_ORDER);
        assertThat(testOrderContent.getQuantityAsked()).isEqualTo(DEFAULT_QUANTITY_ASKED);
        assertThat(testOrderContent.getProductAvailable()).isEqualTo(UPDATED_PRODUCT_AVAILABLE);
    }

    @Test
    @Transactional
    void fullUpdateOrderContentWithPatch() throws Exception {
        // Initialize the database
        orderContentRepository.saveAndFlush(orderContent);

        int databaseSizeBeforeUpdate = orderContentRepository.findAll().size();

        // Update the orderContent using partial update
        OrderContent partialUpdatedOrderContent = new OrderContent();
        partialUpdatedOrderContent.setId(orderContent.getId());

        partialUpdatedOrderContent
            .idProduct(UPDATED_ID_PRODUCT)
            .idOrder(UPDATED_ID_ORDER)
            .quantityAsked(UPDATED_QUANTITY_ASKED)
            .productAvailable(UPDATED_PRODUCT_AVAILABLE);

        restOrderContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderContent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderContent))
            )
            .andExpect(status().isOk());

        // Validate the OrderContent in the database
        List<OrderContent> orderContentList = orderContentRepository.findAll();
        assertThat(orderContentList).hasSize(databaseSizeBeforeUpdate);
        OrderContent testOrderContent = orderContentList.get(orderContentList.size() - 1);
        assertThat(testOrderContent.getIdProduct()).isEqualTo(UPDATED_ID_PRODUCT);
        assertThat(testOrderContent.getIdOrder()).isEqualTo(UPDATED_ID_ORDER);
        assertThat(testOrderContent.getQuantityAsked()).isEqualTo(UPDATED_QUANTITY_ASKED);
        assertThat(testOrderContent.getProductAvailable()).isEqualTo(UPDATED_PRODUCT_AVAILABLE);
    }

    @Test
    @Transactional
    void patchNonExistingOrderContent() throws Exception {
        int databaseSizeBeforeUpdate = orderContentRepository.findAll().size();
        orderContent.setId(count.incrementAndGet());

        // Create the OrderContent
        OrderContentDTO orderContentDTO = orderContentMapper.toDto(orderContent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, orderContentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderContentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderContent in the database
        List<OrderContent> orderContentList = orderContentRepository.findAll();
        assertThat(orderContentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrderContent() throws Exception {
        int databaseSizeBeforeUpdate = orderContentRepository.findAll().size();
        orderContent.setId(count.incrementAndGet());

        // Create the OrderContent
        OrderContentDTO orderContentDTO = orderContentMapper.toDto(orderContent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderContentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderContent in the database
        List<OrderContent> orderContentList = orderContentRepository.findAll();
        assertThat(orderContentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrderContent() throws Exception {
        int databaseSizeBeforeUpdate = orderContentRepository.findAll().size();
        orderContent.setId(count.incrementAndGet());

        // Create the OrderContent
        OrderContentDTO orderContentDTO = orderContentMapper.toDto(orderContent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderContentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderContentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderContent in the database
        List<OrderContent> orderContentList = orderContentRepository.findAll();
        assertThat(orderContentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrderContent() throws Exception {
        // Initialize the database
        orderContentRepository.saveAndFlush(orderContent);

        int databaseSizeBeforeDelete = orderContentRepository.findAll().size();

        // Delete the orderContent
        restOrderContentMockMvc
            .perform(delete(ENTITY_API_URL_ID, orderContent.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderContent> orderContentList = orderContentRepository.findAll();
        assertThat(orderContentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
