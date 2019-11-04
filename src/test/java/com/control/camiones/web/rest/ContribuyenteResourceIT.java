package com.control.camiones.web.rest;

import com.control.camiones.ControldecamionesApp;
import com.control.camiones.domain.Contribuyente;
import com.control.camiones.repository.ContribuyenteRepository;
import com.control.camiones.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.control.camiones.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ContribuyenteResource} REST controller.
 */
@SpringBootTest(classes = ControldecamionesApp.class)
public class ContribuyenteResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_CUIT = "AAAAAAAAAA";
    private static final String UPDATED_CUIT = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private ContribuyenteRepository contribuyenteRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restContribuyenteMockMvc;

    private Contribuyente contribuyente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContribuyenteResource contribuyenteResource = new ContribuyenteResource(contribuyenteRepository);
        this.restContribuyenteMockMvc = MockMvcBuilders.standaloneSetup(contribuyenteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contribuyente createEntity(EntityManager em) {
        Contribuyente contribuyente = new Contribuyente()
            .nombre(DEFAULT_NOMBRE)
            .apellido(DEFAULT_APELLIDO)
            .cuit(DEFAULT_CUIT)
            .email(DEFAULT_EMAIL);
        return contribuyente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contribuyente createUpdatedEntity(EntityManager em) {
        Contribuyente contribuyente = new Contribuyente()
            .nombre(UPDATED_NOMBRE)
            .apellido(UPDATED_APELLIDO)
            .cuit(UPDATED_CUIT)
            .email(UPDATED_EMAIL);
        return contribuyente;
    }

    @BeforeEach
    public void initTest() {
        contribuyente = createEntity(em);
    }

    @Test
    @Transactional
    public void createContribuyente() throws Exception {
        int databaseSizeBeforeCreate = contribuyenteRepository.findAll().size();

        // Create the Contribuyente
        restContribuyenteMockMvc.perform(post("/api/contribuyentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contribuyente)))
            .andExpect(status().isCreated());

        // Validate the Contribuyente in the database
        List<Contribuyente> contribuyenteList = contribuyenteRepository.findAll();
        assertThat(contribuyenteList).hasSize(databaseSizeBeforeCreate + 1);
        Contribuyente testContribuyente = contribuyenteList.get(contribuyenteList.size() - 1);
        assertThat(testContribuyente.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testContribuyente.getApellido()).isEqualTo(DEFAULT_APELLIDO);
        assertThat(testContribuyente.getCuit()).isEqualTo(DEFAULT_CUIT);
        assertThat(testContribuyente.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createContribuyenteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contribuyenteRepository.findAll().size();

        // Create the Contribuyente with an existing ID
        contribuyente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContribuyenteMockMvc.perform(post("/api/contribuyentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contribuyente)))
            .andExpect(status().isBadRequest());

        // Validate the Contribuyente in the database
        List<Contribuyente> contribuyenteList = contribuyenteRepository.findAll();
        assertThat(contribuyenteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllContribuyentes() throws Exception {
        // Initialize the database
        contribuyenteRepository.saveAndFlush(contribuyente);

        // Get all the contribuyenteList
        restContribuyenteMockMvc.perform(get("/api/contribuyentes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contribuyente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO)))
            .andExpect(jsonPath("$.[*].cuit").value(hasItem(DEFAULT_CUIT)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }
    
    @Test
    @Transactional
    public void getContribuyente() throws Exception {
        // Initialize the database
        contribuyenteRepository.saveAndFlush(contribuyente);

        // Get the contribuyente
        restContribuyenteMockMvc.perform(get("/api/contribuyentes/{id}", contribuyente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contribuyente.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.apellido").value(DEFAULT_APELLIDO))
            .andExpect(jsonPath("$.cuit").value(DEFAULT_CUIT))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    @Transactional
    public void getNonExistingContribuyente() throws Exception {
        // Get the contribuyente
        restContribuyenteMockMvc.perform(get("/api/contribuyentes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContribuyente() throws Exception {
        // Initialize the database
        contribuyenteRepository.saveAndFlush(contribuyente);

        int databaseSizeBeforeUpdate = contribuyenteRepository.findAll().size();

        // Update the contribuyente
        Contribuyente updatedContribuyente = contribuyenteRepository.findById(contribuyente.getId()).get();
        // Disconnect from session so that the updates on updatedContribuyente are not directly saved in db
        em.detach(updatedContribuyente);
        updatedContribuyente
            .nombre(UPDATED_NOMBRE)
            .apellido(UPDATED_APELLIDO)
            .cuit(UPDATED_CUIT)
            .email(UPDATED_EMAIL);

        restContribuyenteMockMvc.perform(put("/api/contribuyentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContribuyente)))
            .andExpect(status().isOk());

        // Validate the Contribuyente in the database
        List<Contribuyente> contribuyenteList = contribuyenteRepository.findAll();
        assertThat(contribuyenteList).hasSize(databaseSizeBeforeUpdate);
        Contribuyente testContribuyente = contribuyenteList.get(contribuyenteList.size() - 1);
        assertThat(testContribuyente.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testContribuyente.getApellido()).isEqualTo(UPDATED_APELLIDO);
        assertThat(testContribuyente.getCuit()).isEqualTo(UPDATED_CUIT);
        assertThat(testContribuyente.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingContribuyente() throws Exception {
        int databaseSizeBeforeUpdate = contribuyenteRepository.findAll().size();

        // Create the Contribuyente

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContribuyenteMockMvc.perform(put("/api/contribuyentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contribuyente)))
            .andExpect(status().isBadRequest());

        // Validate the Contribuyente in the database
        List<Contribuyente> contribuyenteList = contribuyenteRepository.findAll();
        assertThat(contribuyenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContribuyente() throws Exception {
        // Initialize the database
        contribuyenteRepository.saveAndFlush(contribuyente);

        int databaseSizeBeforeDelete = contribuyenteRepository.findAll().size();

        // Delete the contribuyente
        restContribuyenteMockMvc.perform(delete("/api/contribuyentes/{id}", contribuyente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contribuyente> contribuyenteList = contribuyenteRepository.findAll();
        assertThat(contribuyenteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contribuyente.class);
        Contribuyente contribuyente1 = new Contribuyente();
        contribuyente1.setId(1L);
        Contribuyente contribuyente2 = new Contribuyente();
        contribuyente2.setId(contribuyente1.getId());
        assertThat(contribuyente1).isEqualTo(contribuyente2);
        contribuyente2.setId(2L);
        assertThat(contribuyente1).isNotEqualTo(contribuyente2);
        contribuyente1.setId(null);
        assertThat(contribuyente1).isNotEqualTo(contribuyente2);
    }
}
