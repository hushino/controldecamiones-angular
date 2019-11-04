package com.control.camiones.repository;
import com.control.camiones.domain.Inspector;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Inspector entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InspectorRepository extends JpaRepository<Inspector, Long> {

}
