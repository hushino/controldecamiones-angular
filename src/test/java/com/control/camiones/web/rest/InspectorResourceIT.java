package com.control.camiones.web.rest;

import com.control.camiones.ControldecamionesApp;
import com.control.camiones.domain.Inspector;
import com.control.camiones.repository.InspectorRepository;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.control.camiones.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link InspectorResource} REST controller.
 */
@SpringBootTest(classes = ControldecamionesApp.class)
public class InspectorResourceIT {

    private static final byte[] DEFAULT_FOTOPATENTE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTOPATENTE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FOTOPATENTE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTOPATENTE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_FOTOCAMION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTOCAMION = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FOTOCAMION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTOCAMION_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_VEHICULOMODELO = "AAAAAAAAAA";
    private static final String UPDATED_VEHICULOMODELO = "BBBBBBBBBB";

    private static final String DEFAULT_INFOADICIONAL = "AAAAAAAAAA";
    private static final String UPDATED_INFOADICIONAL = "BBBBBBBBBB";

    private static final Integer DEFAULT_CELULAR = 1;
    private static final Integer UPDATED_CELULAR = 2;

    private static final String DEFAULT_CUIT = "AAAAAAAAAA";
    private static final String UPDATED_CUIT = "BBBBBBBBBB";

    private static final String DEFAULT_PATENTE = "AAAAAAAAAA";
    private static final String UPDATED_PATENTE = "BBBBBBBBBB";

    @Autowired
    private InspectorRepository inspectorRepository;

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

    private MockMvc restInspectorMockMvc;

    private Inspector inspector;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InspectorResource inspectorResource = new InspectorResource(inspectorRepository);
        this.restInspectorMockMvc = MockMvcBuilders.standaloneSetup(inspectorResource)
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
    public static Inspector createEntity(EntityManager em) {
        Inspector inspector = new Inspector()
            .fotopatente(DEFAULT_FOTOPATENTE)
            .fotopatenteContentType(DEFAULT_FOTOPATENTE_CONTENT_TYPE)
            .fotocamion(DEFAULT_FOTOCAMION)
            .fotocamionContentType(DEFAULT_FOTOCAMION_CONTENT_TYPE)
            .vehiculomodelo(DEFAULT_VEHICULOMODELO)
            .infoadicional(DEFAULT_INFOADICIONAL)
            .celular(DEFAULT_CELULAR)
            .cuit(DEFAULT_CUIT)
            .patente(DEFAULT_PATENTE);
        return inspector;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inspector createUpdatedEntity(EntityManager em) {
        Inspector inspector = new Inspector()
            .fotopatente(UPDATED_FOTOPATENTE)
            .fotopatenteContentType(UPDATED_FOTOPATENTE_CONTENT_TYPE)
            .fotocamion(UPDATED_FOTOCAMION)
            .fotocamionContentType(UPDATED_FOTOCAMION_CONTENT_TYPE)
            .vehiculomodelo(UPDATED_VEHICULOMODELO)
            .infoadicional(UPDATED_INFOADICIONAL)
            .celular(UPDATED_CELULAR)
            .cuit(UPDATED_CUIT)
            .patente(UPDATED_PATENTE);
        return inspector;
    }

    @BeforeEach
    public void initTest() {
        inspector = createEntity(em);
    }

    @Test
    @Transactional
    public void createInspector() throws Exception {
        int databaseSizeBeforeCreate = inspectorRepository.findAll().size();

        // Create the Inspector
        restInspectorMockMvc.perform(post("/api/inspectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspector)))
            .andExpect(status().isCreated());

        // Validate the Inspector in the database
        List<Inspector> inspectorList = inspectorRepository.findAll();
        assertThat(inspectorList).hasSize(databaseSizeBeforeCreate + 1);
        Inspector testInspector = inspectorList.get(inspectorList.size() - 1);
        assertThat(testInspector.getFotopatente()).isEqualTo(DEFAULT_FOTOPATENTE);
        assertThat(testInspector.getFotopatenteContentType()).isEqualTo(DEFAULT_FOTOPATENTE_CONTENT_TYPE);
        assertThat(testInspector.getFotocamion()).isEqualTo(DEFAULT_FOTOCAMION);
        assertThat(testInspector.getFotocamionContentType()).isEqualTo(DEFAULT_FOTOCAMION_CONTENT_TYPE);
        assertThat(testInspector.getVehiculomodelo()).isEqualTo(DEFAULT_VEHICULOMODELO);
        assertThat(testInspector.getInfoadicional()).isEqualTo(DEFAULT_INFOADICIONAL);
        assertThat(testInspector.getCelular()).isEqualTo(DEFAULT_CELULAR);
        assertThat(testInspector.getCuit()).isEqualTo(DEFAULT_CUIT);
        assertThat(testInspector.getPatente()).isEqualTo(DEFAULT_PATENTE);
    }

    @Test
    @Transactional
    public void createInspectorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inspectorRepository.findAll().size();

        // Create the Inspector with an existing ID
        inspector.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInspectorMockMvc.perform(post("/api/inspectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspector)))
            .andExpect(status().isBadRequest());

        // Validate the Inspector in the database
        List<Inspector> inspectorList = inspectorRepository.findAll();
        assertThat(inspectorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInspectors() throws Exception {
        // Initialize the database
        inspectorRepository.saveAndFlush(inspector);

        // Get all the inspectorList
        restInspectorMockMvc.perform(get("/api/inspectors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inspector.getId().intValue())))
            .andExpect(jsonPath("$.[*].fotopatenteContentType").value(hasItem(DEFAULT_FOTOPATENTE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fotopatente").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTOPATENTE))))
            .andExpect(jsonPath("$.[*].fotocamionContentType").value(hasItem(DEFAULT_FOTOCAMION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fotocamion").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTOCAMION))))
            .andExpect(jsonPath("$.[*].vehiculomodelo").value(hasItem(DEFAULT_VEHICULOMODELO)))
            .andExpect(jsonPath("$.[*].infoadicional").value(hasItem(DEFAULT_INFOADICIONAL)))
            .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR)))
            .andExpect(jsonPath("$.[*].cuit").value(hasItem(DEFAULT_CUIT)))
            .andExpect(jsonPath("$.[*].patente").value(hasItem(DEFAULT_PATENTE)));
    }
    
    @Test
    @Transactional
    public void getInspector() throws Exception {
        // Initialize the database
        inspectorRepository.saveAndFlush(inspector);

        // Get the inspector
        restInspectorMockMvc.perform(get("/api/inspectors/{id}", inspector.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inspector.getId().intValue()))
            .andExpect(jsonPath("$.fotopatenteContentType").value(DEFAULT_FOTOPATENTE_CONTENT_TYPE))
            .andExpect(jsonPath("$.fotopatente").value(Base64Utils.encodeToString(DEFAULT_FOTOPATENTE)))
            .andExpect(jsonPath("$.fotocamionContentType").value(DEFAULT_FOTOCAMION_CONTENT_TYPE))
            .andExpect(jsonPath("$.fotocamion").value(Base64Utils.encodeToString(DEFAULT_FOTOCAMION)))
            .andExpect(jsonPath("$.vehiculomodelo").value(DEFAULT_VEHICULOMODELO))
            .andExpect(jsonPath("$.infoadicional").value(DEFAULT_INFOADICIONAL))
            .andExpect(jsonPath("$.celular").value(DEFAULT_CELULAR))
            .andExpect(jsonPath("$.cuit").value(DEFAULT_CUIT))
            .andExpect(jsonPath("$.patente").value(DEFAULT_PATENTE));
    }

    @Test
    @Transactional
    public void getNonExistingInspector() throws Exception {
        // Get the inspector
        restInspectorMockMvc.perform(get("/api/inspectors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInspector() throws Exception {
        // Initialize the database
        inspectorRepository.saveAndFlush(inspector);

        int databaseSizeBeforeUpdate = inspectorRepository.findAll().size();

        // Update the inspector
        Inspector updatedInspector = inspectorRepository.findById(inspector.getId()).get();
        // Disconnect from session so that the updates on updatedInspector are not directly saved in db
        em.detach(updatedInspector);
        updatedInspector
            .fotopatente(UPDATED_FOTOPATENTE)
            .fotopatenteContentType(UPDATED_FOTOPATENTE_CONTENT_TYPE)
            .fotocamion(UPDATED_FOTOCAMION)
            .fotocamionContentType(UPDATED_FOTOCAMION_CONTENT_TYPE)
            .vehiculomodelo(UPDATED_VEHICULOMODELO)
            .infoadicional(UPDATED_INFOADICIONAL)
            .celular(UPDATED_CELULAR)
            .cuit(UPDATED_CUIT)
            .patente(UPDATED_PATENTE);

        restInspectorMockMvc.perform(put("/api/inspectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInspector)))
            .andExpect(status().isOk());

        // Validate the Inspector in the database
        List<Inspector> inspectorList = inspectorRepository.findAll();
        assertThat(inspectorList).hasSize(databaseSizeBeforeUpdate);
        Inspector testInspector = inspectorList.get(inspectorList.size() - 1);
        assertThat(testInspector.getFotopatente()).isEqualTo(UPDATED_FOTOPATENTE);
        assertThat(testInspector.getFotopatenteContentType()).isEqualTo(UPDATED_FOTOPATENTE_CONTENT_TYPE);
        assertThat(testInspector.getFotocamion()).isEqualTo(UPDATED_FOTOCAMION);
        assertThat(testInspector.getFotocamionContentType()).isEqualTo(UPDATED_FOTOCAMION_CONTENT_TYPE);
        assertThat(testInspector.getVehiculomodelo()).isEqualTo(UPDATED_VEHICULOMODELO);
        assertThat(testInspector.getInfoadicional()).isEqualTo(UPDATED_INFOADICIONAL);
        assertThat(testInspector.getCelular()).isEqualTo(UPDATED_CELULAR);
        assertThat(testInspector.getCuit()).isEqualTo(UPDATED_CUIT);
        assertThat(testInspector.getPatente()).isEqualTo(UPDATED_PATENTE);
    }

    @Test
    @Transactional
    public void updateNonExistingInspector() throws Exception {
        int databaseSizeBeforeUpdate = inspectorRepository.findAll().size();

        // Create the Inspector

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInspectorMockMvc.perform(put("/api/inspectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspector)))
            .andExpect(status().isBadRequest());

        // Validate the Inspector in the database
        List<Inspector> inspectorList = inspectorRepository.findAll();
        assertThat(inspectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInspector() throws Exception {
        // Initialize the database
        inspectorRepository.saveAndFlush(inspector);

        int databaseSizeBeforeDelete = inspectorRepository.findAll().size();

        // Delete the inspector
        restInspectorMockMvc.perform(delete("/api/inspectors/{id}", inspector.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Inspector> inspectorList = inspectorRepository.findAll();
        assertThat(inspectorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inspector.class);
        Inspector inspector1 = new Inspector();
        inspector1.setId(1L);
        Inspector inspector2 = new Inspector();
        inspector2.setId(inspector1.getId());
        assertThat(inspector1).isEqualTo(inspector2);
        inspector2.setId(2L);
        assertThat(inspector1).isNotEqualTo(inspector2);
        inspector1.setId(null);
        assertThat(inspector1).isNotEqualTo(inspector2);
    }
}
