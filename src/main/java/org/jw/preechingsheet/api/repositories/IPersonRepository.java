package org.jw.preechingsheet.api.repositories;

import java.util.Optional;

import org.jw.preechingsheet.api.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonRepository extends JpaRepository<Person, String> {
	
	public Optional<Person> findByFullName(String fullname);
}
