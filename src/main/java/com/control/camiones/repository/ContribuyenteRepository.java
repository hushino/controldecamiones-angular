package com.control.camiones.repository;
import com.control.camiones.domain.Contribuyente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Contribuyente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContribuyenteRepository extends JpaRepository<Contribuyente, Long> {

}
