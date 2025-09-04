package org.jw.preechingsheet.api.repositories;

import java.util.List;
import java.util.Optional;

import org.jw.preechingsheet.api.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonRepository extends JpaRepository<Person, Long> {
	
	public Optional<Person> findByFullName(String fullname);
	
	@Query("SELECT COUNT(p) FROM Person p WHERE p.active = true")
	public Long countActive();
	
	@Query("SELECT p.role, COUNT(p) FROM Person p WHERE p.active = true GROUP BY p.role")
	List<Object[]> countActiveByRole();
}
