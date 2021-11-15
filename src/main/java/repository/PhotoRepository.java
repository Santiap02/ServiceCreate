package repository;

import Model.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio base de datos "Test4".
 *
 * @author santiago.alvarezp@udea.edu.co
 *
 */
@Repository
public interface PhotoRepository extends MongoRepository<Photo, String> {

    Photo findByClientId(int title);

}

