package business;

import domain.ClienteDto;
import domain.ResponseDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * Interfaz donde se definen las operaciones a implementar para el proceso creación de información de clientes
 *
 * @author santiago.alvarezp@udea.edu.co
 * @version 1.0
 */

public interface ServiceCreateBusiness {

    /**
     * Permite crear un cliente nuevo
     * @return Respuesta lista de objetos Cliente
     * @param cliente Objeto de informacion del cliente
     */
    ResponseDto<String> newClient(ClienteDto cliente);

    /**
     * Permite crear una foto asignada a un cliente a partir de su Id
     *
     * @param clientId Identificacion del usuario
     * @param file     Archivo de imagen
     * @return Objeto de respuesta ResponseDto con
     */
    ResponseDto<String> addPhoto(int clientId, MultipartFile file);

}
