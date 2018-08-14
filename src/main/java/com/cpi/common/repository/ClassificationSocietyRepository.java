package com.cpi.common.repository;

import com.cpi.common.domain.ClassificationSociety;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ClassificationSociety entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassificationSocietyRepository extends JpaRepository<ClassificationSociety, Long>, JpaSpecificationExecutor<ClassificationSociety> {

}
