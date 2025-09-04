package org.jw.preechingsheet.api.repositories;

import org.jw.preechingsheet.api.entities.WeeklyPreaching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWeeklyPreachingRepository extends JpaRepository<WeeklyPreaching, Long> {

}
