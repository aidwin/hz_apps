package com.hz.hz_apps.repo;

import com.hz.hz_apps.entity.HealthReport;
import com.hz.hz_apps.entity.UserAttr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAttrRepo extends JpaRepository<UserAttr,Integer>,JpaSpecificationExecutor {

    List<UserAttr> getUserAttrsByImportTime(String importTime );
}