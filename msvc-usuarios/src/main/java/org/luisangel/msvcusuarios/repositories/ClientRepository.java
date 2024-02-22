package org.luisangel.msvcusuarios.repositories;

import org.luisangel.msvcusuarios.models.entity.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

    @Query(value = "SELECT c.* FROM clients c" +
            " INNER JOIN persons p ON c.person_id = p.id" +
            " WHERE p.num_document = (:numDocument) ",
    nativeQuery = true)
    Optional<Client> findByNumDocument(@Param("numDocument") String numDocument);

}
