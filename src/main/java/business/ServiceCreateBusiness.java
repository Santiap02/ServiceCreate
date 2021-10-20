package business;

import Model.Cliente;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Interface donde se definen las operaciones a implementar para el proceso consulta de informacion de clientes
 *
 * @author santiago.alvarezp@udea.edu.co
 * @version 1.0
 */

public interface ServiceGetBusiness {
    /**
     * Permite consultar una lista con todos los clientes
     * @return Objeto de respuesta lista de objetos Cliente
     */

    boolean agregarCliente(Cliente cliente);
    void actualizarCliente(Cliente cliente);
    String addPhoto(int ClientId, MultipartFile file) throws IOException;
    String addPhoto2(int title, String image);


}
