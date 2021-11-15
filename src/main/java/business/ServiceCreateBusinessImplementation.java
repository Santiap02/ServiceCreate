package business;

import Util.ValidationUtils;
import domain.ResponseDto;
import Model.Cliente;
import Model.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import repository.ClientRepository;
import repository.PhotoRepository;
import Util.ServiceConstants;
import lombok.AllArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@AllArgsConstructor
public class ServiceCreateBusinessImplementation implements ServiceCreateBusiness {
    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceCreateBusinessImplementation.class);
    /** Objeto para acceder a la capa de datos de clientes */
    private final ClientRepository clientRepository;
    /** Objeto para acceder a la capa de datos de fotos */
    private final PhotoRepository photoRepository;
    @Autowired
    ValidationUtils validationUtils;
    /**
     *
     * @see ServiceCreateBusiness#newClient(Cliente)
     */
    @Override
    public ResponseDto<String> newClient(Cliente cliente) {
        LOGGER.debug("Se inicia agregarCliente");
        ResponseDto<String> response;
        try {
            validationUtils.validate(cliente);
            if(clientRepository.findById(cliente.getIdCliente()).isEmpty()){
                clientRepository.save(cliente);
                response = new ResponseDto<>(HttpStatus.CREATED.value(), ServiceConstants.SA002, ServiceConstants.SA002M);
            }
            else {
                response = new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), ServiceConstants.SA003, ServiceConstants.SA003M);
            }
        }catch (Exception e){
            LOGGER.error("Error in getClients", e);
            response = new ResponseDto<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ServiceConstants.SA100, ServiceConstants.SA100M);
        }
        LOGGER.debug("AgregarCliente retorna: {}",response);
        return  response;
    }

    /**
     *
     * @see ServiceCreateBusiness#addPhoto(int, MultipartFile)
     */
    @Override
    public ResponseDto<String> addPhoto(int clientId, MultipartFile file) {
        LOGGER.debug("Se inicia addPhoto");
        ResponseDto<String> response;
        try {
            Photo photo = new Photo(clientId);
            photo.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
            if(photoRepository.findByClientId(clientId)==null){
                photo = photoRepository.insert(photo);
                response = new ResponseDto<>(HttpStatus.CREATED.value(), ServiceConstants.SA002, ServiceConstants.SA002M, "MongoId de la imagen: "+photo.getId());
            }
            else{
                response = new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), ServiceConstants.SA003, ServiceConstants.SA003M);
            }
        } catch (Exception e) {
            response = new ResponseDto<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ServiceConstants.SA100, ServiceConstants.SA100M);
            LOGGER.error("Error in getClients", e);
        }
        LOGGER.debug("addPhoto retorna: "+ response);
        return  response;
    }

}
