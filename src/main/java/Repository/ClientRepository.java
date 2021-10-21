package Repository;

import Model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repositorio base de datos "clientes".
 *
 * @author santiago.alvarezp@udea.edu.co
 *
 */
public interface ClientRepository extends JpaRepository<Cliente, Integer> {
    @Transactional
    @Modifying
    @Query("select c from Cliente c Where c.Edad>=?1")
    List<Cliente> GetByEdad(int edad);

}
