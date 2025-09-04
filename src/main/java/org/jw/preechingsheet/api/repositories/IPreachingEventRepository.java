package org.jw.preechingsheet.api.repositories;

import org.jw.preechingsheet.api.entities.PreachingEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPreachingEventRepository extends JpaRepository<PreachingEvent, Long> {

}
