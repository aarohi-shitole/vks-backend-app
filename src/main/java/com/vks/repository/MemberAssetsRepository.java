package com.vks.repository;

import com.vks.domain.MemberAssets;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MemberAssets entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MemberAssetsRepository extends JpaRepository<MemberAssets, Long>, JpaSpecificationExecutor<MemberAssets> {}
