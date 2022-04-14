package com.zhangkem.coopcycle.web.rest;

import com.zhangkem.coopcycle.repository.OrderContentRepository;
import com.zhangkem.coopcycle.service.OrderContentService;
import com.zhangkem.coopcycle.service.dto.OrderContentDTO;
import com.zhangkem.coopcycle.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.zhangkem.coopcycle.domain.OrderContent}.
 */
@RestController
@RequestMapping("/api")
public class OrderContentResource {

    private final Logger log = LoggerFactory.getLogger(OrderContentResource.class);

    private static final String ENTITY_NAME = "orderContent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderContentService orderContentService;

    private final OrderContentRepository orderContentRepository;

    public OrderContentResource(OrderContentService orderContentService, OrderContentRepository orderContentRepository) {
        this.orderContentService = orderContentService;
        this.orderContentRepository = orderContentRepository;
    }

    /**
     * {@code POST  /order-contents} : Create a new orderContent.
     *
     * @param orderContentDTO the orderContentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderContentDTO, or with status {@code 400 (Bad Request)} if the orderContent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-contents")
    public ResponseEntity<OrderContentDTO> createOrderContent(@Valid @RequestBody OrderContentDTO orderContentDTO)
        throws URISyntaxException {
        log.debug("REST request to save OrderContent : {}", orderContentDTO);
        if (orderContentDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderContent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderContentDTO result = orderContentService.save(orderContentDTO);
        return ResponseEntity
            .created(new URI("/api/order-contents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-contents/:id} : Updates an existing orderContent.
     *
     * @param id the id of the orderContentDTO to save.
     * @param orderContentDTO the orderContentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderContentDTO,
     * or with status {@code 400 (Bad Request)} if the orderContentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderContentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-contents/{id}")
    public ResponseEntity<OrderContentDTO> updateOrderContent(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OrderContentDTO orderContentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrderContent : {}, {}", id, orderContentDTO);
        if (orderContentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderContentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderContentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrderContentDTO result = orderContentService.update(orderContentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderContentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /order-contents/:id} : Partial updates given fields of an existing orderContent, field will ignore if it is null
     *
     * @param id the id of the orderContentDTO to save.
     * @param orderContentDTO the orderContentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderContentDTO,
     * or with status {@code 400 (Bad Request)} if the orderContentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the orderContentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the orderContentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/order-contents/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrderContentDTO> partialUpdateOrderContent(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OrderContentDTO orderContentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrderContent partially : {}, {}", id, orderContentDTO);
        if (orderContentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderContentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderContentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrderContentDTO> result = orderContentService.partialUpdate(orderContentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderContentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /order-contents} : get all the orderContents.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderContents in body.
     */
    @GetMapping("/order-contents")
    public List<OrderContentDTO> getAllOrderContents(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all OrderContents");
        return orderContentService.findAll();
    }

    /**
     * {@code GET  /order-contents/:id} : get the "id" orderContent.
     *
     * @param id the id of the orderContentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderContentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-contents/{id}")
    public ResponseEntity<OrderContentDTO> getOrderContent(@PathVariable Long id) {
        log.debug("REST request to get OrderContent : {}", id);
        Optional<OrderContentDTO> orderContentDTO = orderContentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderContentDTO);
    }

    /**
     * {@code DELETE  /order-contents/:id} : delete the "id" orderContent.
     *
     * @param id the id of the orderContentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-contents/{id}")
    public ResponseEntity<Void> deleteOrderContent(@PathVariable Long id) {
        log.debug("REST request to delete OrderContent : {}", id);
        orderContentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
