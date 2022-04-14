package com.zhangkem.coopcycle.service;

import com.zhangkem.coopcycle.domain.Deliverer;
import com.zhangkem.coopcycle.repository.DelivererRepository;
import com.zhangkem.coopcycle.service.dto.DelivererDTO;
import com.zhangkem.coopcycle.service.mapper.DelivererMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Deliverer}.
 */
@Service
@Transactional
public class DelivererService {

    private final Logger log = LoggerFactory.getLogger(DelivererService.class);

    private final DelivererRepository delivererRepository;

    private final DelivererMapper delivererMapper;

    public DelivererService(DelivererRepository delivererRepository, DelivererMapper delivererMapper) {
        this.delivererRepository = delivererRepository;
        this.delivererMapper = delivererMapper;
    }

    /**
     * Save a deliverer.
     *
     * @param delivererDTO the entity to save.
     * @return the persisted entity.
     */
    public DelivererDTO save(DelivererDTO delivererDTO) {
        log.debug("Request to save Deliverer : {}", delivererDTO);
        Deliverer deliverer = delivererMapper.toEntity(delivererDTO);
        deliverer = delivererRepository.save(deliverer);
        return delivererMapper.toDto(deliverer);
    }

    /**
     * Update a deliverer.
     *
     * @param delivererDTO the entity to save.
     * @return the persisted entity.
     */
    public DelivererDTO update(DelivererDTO delivererDTO) {
        log.debug("Request to save Deliverer : {}", delivererDTO);
        Deliverer deliverer = delivererMapper.toEntity(delivererDTO);
        deliverer = delivererRepository.save(deliverer);
        return delivererMapper.toDto(deliverer);
    }

    /**
     * Partially update a deliverer.
     *
     * @param delivererDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DelivererDTO> partialUpdate(DelivererDTO delivererDTO) {
        log.debug("Request to partially update Deliverer : {}", delivererDTO);

        return delivererRepository
            .findById(delivererDTO.getId())
            .map(existingDeliverer -> {
                delivererMapper.partialUpdate(existingDeliverer, delivererDTO);

                return existingDeliverer;
            })
            .map(delivererRepository::save)
            .map(delivererMapper::toDto);
    }

    /**
     * Get all the deliverers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DelivererDTO> findAll() {
        log.debug("Request to get all Deliverers");
        return delivererRepository.findAll().stream().map(delivererMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one deliverer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DelivererDTO> findOne(Long id) {
        log.debug("Request to get Deliverer : {}", id);
        return delivererRepository.findById(id).map(delivererMapper::toDto);
    }

    /**
     * Delete the deliverer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Deliverer : {}", id);
        delivererRepository.deleteById(id);
    }
}
