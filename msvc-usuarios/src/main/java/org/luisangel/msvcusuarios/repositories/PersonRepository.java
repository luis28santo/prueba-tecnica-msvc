package org.luisangel.msvcusuarios.repositories;

import org.luisangel.msvcusuarios.models.entity.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {



}
