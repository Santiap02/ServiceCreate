package business;

import Domain.ResponseDto;
import Model.Cliente;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Interface donde se definen las operaciones a implementar para el proceso consulta de informacion de clientes
 *
 * @author santiago.alvarezp@udea.edu.co
 * @version 1.0
 */

public interface ServiceCreateBusiness {
    /**
     * Permite crear un cliente nuevo
     * @return Respuesta lista de objetos Cliente
     */

    ResponseDto agregarCliente(Cliente cliente);
    /**
     * Permite crear una foto asignada a un cliente a partir de su Id
     *
     * @param clientId Identificacion del usuario
     * @param file     Archivo de imagen
     * @return Objeto de respuesta ResponseDto con
     */
    ResponseDto<String> addPhoto(int clientId, MultipartFile file) throws IOException;


}
