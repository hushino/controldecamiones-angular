package com.control.camiones.web.rest;

import com.control.camiones.domain.Inspector;
import com.control.camiones.repository.InspectorRepository;
import com.control.camiones.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.control.camiones.domain.Inspector}.
 */
@RestController
@RequestMapping("/api")
public class InspectorResource {

    private final Logger log = LoggerFactory.getLogger(InspectorResource.class);

    private static final String ENTITY_NAME = "inspector";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InspectorRepository inspectorRepository;

    public InspectorResource(InspectorRepository inspectorRepository) {
        this.inspectorRepository = inspectorRepository;
    }

    /**
     * {@code POST  /inspectors} : Create a new inspector.
     *
     * @param inspector the inspector to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inspector, or with status {@code 400 (Bad Request)} if the inspector has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/inspectors")
    public ResponseEntity<Inspector> createInspector(@RequestBody Inspector inspector) throws URISyntaxException {
        log.debug("REST request to save Inspector : {}", inspector);
        if (inspector.getId() != null) {
            throw new BadRequestAlertException("A new inspector cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Inspector result = inspectorRepository.save(inspector);
        return ResponseEntity.created(new URI("/api/inspectors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /inspectors} : Updates an existing inspector.
     *
     * @param inspector the inspector to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inspector,
     * or with status {@code 400 (Bad Request)} if the inspector is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inspector couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/inspectors")
    public ResponseEntity<Inspector> updateInspector(@RequestBody Inspector inspector) throws URISyntaxException {
        log.debug("REST request to update Inspector : {}", inspector);
        if (inspector.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Inspector result = inspectorRepository.save(inspector);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inspector.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /inspectors} : get all the inspectors.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inspectors in body.
     */
    @GetMapping("/inspectors")
    public ResponseEntity<List<Inspector>> getAllInspectors(Pageable pageable) {
        log.debug("REST request to get a page of Inspectors");
        Page<Inspector> page = inspectorRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /inspectors/:id} : get the "id" inspector.
     *
     * @param id the id of the inspector to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inspector, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/inspectors/{id}")
    public ResponseEntity<Inspector> getInspector(@PathVariable Long id) {
        log.debug("REST request to get Inspector : {}", id);
        Optional<Inspector> inspector = inspectorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(inspector);
    }

    /**
     * {@code DELETE  /inspectors/:id} : delete the "id" inspector.
     *
     * @param id the id of the inspector to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/inspectors/{id}")
    public ResponseEntity<Void> deleteInspector(@PathVariable Long id) {
        log.debug("REST request to delete Inspector : {}", id);
        inspectorRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
