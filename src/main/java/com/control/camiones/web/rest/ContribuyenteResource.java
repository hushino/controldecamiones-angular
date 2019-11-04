package com.control.camiones.web.rest;

import com.control.camiones.domain.Contribuyente;
import com.control.camiones.repository.ContribuyenteRepository;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.control.camiones.domain.Contribuyente}.
 */
@RestController
@RequestMapping("/api")
public class ContribuyenteResource {

    private final Logger log = LoggerFactory.getLogger(ContribuyenteResource.class);

    private static final String ENTITY_NAME = "contribuyente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContribuyenteRepository contribuyenteRepository;

    public ContribuyenteResource(ContribuyenteRepository contribuyenteRepository) {
        this.contribuyenteRepository = contribuyenteRepository;
    }

    /**
     * {@code POST  /contribuyentes} : Create a new contribuyente.
     *
     * @param contribuyente the contribuyente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contribuyente, or with status {@code 400 (Bad Request)} if the contribuyente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contribuyentes")
    public ResponseEntity<Contribuyente> createContribuyente(@Valid @RequestBody Contribuyente contribuyente) throws URISyntaxException {
        log.debug("REST request to save Contribuyente : {}", contribuyente);
        if (contribuyente.getId() != null) {
            throw new BadRequestAlertException("A new contribuyente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Contribuyente result = contribuyenteRepository.save(contribuyente);
        return ResponseEntity.created(new URI("/api/contribuyentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contribuyentes} : Updates an existing contribuyente.
     *
     * @param contribuyente the contribuyente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contribuyente,
     * or with status {@code 400 (Bad Request)} if the contribuyente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contribuyente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contribuyentes")
    public ResponseEntity<Contribuyente> updateContribuyente(@Valid @RequestBody Contribuyente contribuyente) throws URISyntaxException {
        log.debug("REST request to update Contribuyente : {}", contribuyente);
        if (contribuyente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Contribuyente result = contribuyenteRepository.save(contribuyente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, contribuyente.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contribuyentes} : get all the contribuyentes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contribuyentes in body.
     */
    @GetMapping("/contribuyentes")
    public ResponseEntity<List<Contribuyente>> getAllContribuyentes(Pageable pageable) {
        log.debug("REST request to get a page of Contribuyentes");
        Page<Contribuyente> page = contribuyenteRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contribuyentes/:id} : get the "id" contribuyente.
     *
     * @param id the id of the contribuyente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contribuyente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contribuyentes/{id}")
    public ResponseEntity<Contribuyente> getContribuyente(@PathVariable Long id) {
        log.debug("REST request to get Contribuyente : {}", id);
        Optional<Contribuyente> contribuyente = contribuyenteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(contribuyente);
    }

    /**
     * {@code DELETE  /contribuyentes/:id} : delete the "id" contribuyente.
     *
     * @param id the id of the contribuyente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contribuyentes/{id}")
    public ResponseEntity<Void> deleteContribuyente(@PathVariable Long id) {
        log.debug("REST request to delete Contribuyente : {}", id);
        contribuyenteRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
