package com.zhangkem.coopcycle.service;

import com.zhangkem.coopcycle.domain.OrderContent;
import com.zhangkem.coopcycle.repository.OrderContentRepository;
import com.zhangkem.coopcycle.service.dto.OrderContentDTO;
import com.zhangkem.coopcycle.service.mapper.OrderContentMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrderContent}.
 */
@Service
@Transactional
public class OrderContentService {

    private final Logger log = LoggerFactory.getLogger(OrderContentService.class);

    private final OrderContentRepository orderContentRepository;

    private final OrderContentMapper orderContentMapper;

    public OrderContentService(OrderContentRepository orderContentRepository, OrderContentMapper orderContentMapper) {
        this.orderContentRepository = orderContentRepository;
        this.orderContentMapper = orderContentMapper;
    }

    /**
     * Save a orderContent.
     *
     * @param orderContentDTO the entity to save.
     * @return the persisted entity.
     */
    public OrderContentDTO save(OrderContentDTO orderContentDTO) {
        log.debug("Request to save OrderContent : {}", orderContentDTO);
        OrderContent orderContent = orderContentMapper.toEntity(orderContentDTO);
        orderContent = orderContentRepository.save(orderContent);
        return orderContentMapper.toDto(orderContent);
    }

    /**
     * Update a orderContent.
     *
     * @param orderContentDTO the entity to save.
     * @return the persisted entity.
     */
    public OrderContentDTO update(OrderContentDTO orderContentDTO) {
        log.debug("Request to save OrderContent : {}", orderContentDTO);
        OrderContent orderContent = orderContentMapper.toEntity(orderContentDTO);
        orderContent = orderContentRepository.save(orderContent);
        return orderContentMapper.toDto(orderContent);
    }

    /**
     * Partially update a orderContent.
     *
     * @param orderContentDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OrderContentDTO> partialUpdate(OrderContentDTO orderContentDTO) {
        log.debug("Request to partially update OrderContent : {}", orderContentDTO);

        return orderContentRepository
            .findById(orderContentDTO.getId())
            .map(existingOrderContent -> {
                orderContentMapper.partialUpdate(existingOrderContent, orderContentDTO);

                return existingOrderContent;
            })
            .map(orderContentRepository::save)
            .map(orderContentMapper::toDto);
    }

    /**
     * Get all the orderContents.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OrderContentDTO> findAll() {
        log.debug("Request to get all OrderContents");
        return orderContentRepository
            .findAllWithEagerRelationships()
            .stream()
            .map(orderContentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the orderContents with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<OrderContentDTO> findAllWithEagerRelationships(Pageable pageable) {
        return orderContentRepository.findAllWithEagerRelationships(pageable).map(orderContentMapper::toDto);
    }

    /**
     * Get one orderContent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrderContentDTO> findOne(Long id) {
        log.debug("Request to get OrderContent : {}", id);
        return orderContentRepository.findOneWithEagerRelationships(id).map(orderContentMapper::toDto);
    }

    /**
     * Delete the orderContent by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OrderContent : {}", id);
        orderContentRepository.deleteById(id);
    }
}
