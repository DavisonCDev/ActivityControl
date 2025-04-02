package com.oksmart.activitycontrol.repository;

import com.oksmart.activitycontrol.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, String> {

}
