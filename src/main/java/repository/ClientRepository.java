package repository;

import Model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio base de datos "clientes".
 *
 * @author santiago.alvarezp@udea.edu.co
 *
 */
public interface ClientRepository extends JpaRepository<Cliente, Integer> {

}
