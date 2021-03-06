package business;

import Model.Photo;
import Util.ClienteMapper;
import Util.ServiceConstants;
import Util.ValidationUtils;
import domain.ClienteDto;
import domain.ResponseDto;
import exception.ServiceCreateException;
import lombok.AllArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import repository.ClientRepository;
import repository.PhotoRepository;


@Service
@AllArgsConstructor
public class ServiceCreateBusinessImplementation implements ServiceCreateBusiness {

    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceCreateBusinessImplementation.class);
    /** Objeto para acceder a la capa de datos de clientes */
    private final ClientRepository clientRepository;
    /** Objeto para acceder a la capa de datos de fotos */
    private final PhotoRepository photoRepository;
    /** Validador*/
    private final ValidationUtils validationUtils;
    /** Mapper*/
    private final ClienteMapper clienteMapper;


    /**
     * @see ServiceCreateBusiness#newClient(ClienteDto)
     */
    @Override
    public ResponseDto<String> newClient(ClienteDto cliente) {
        LOGGER.debug("Se inicia newClient");
        ResponseDto<String> response;
        try {
            validationUtils.validate(cliente);
            if(clientRepository.findById(cliente.getIdCliente()).isEmpty()){
                clientRepository.save(clienteMapper.clienteDtoToCliente(cliente));
                response = new ResponseDto<>(HttpStatus.CREATED.value(), ServiceConstants.SA002, ServiceConstants.SA002M);
            }
            else {
                response = new ResponseDto<>(HttpStatus.FORBIDDEN.value(), ServiceConstants.SA003, ServiceConstants.SA003M);
            }
        }catch (ServiceCreateException e){
            LOGGER.error("Error in newClient", e);
            response = new ResponseDto<>(e.getStatus(), e.getCode(), e.getMessage());
        }catch (Exception e){
            LOGGER.error("Error in newClient", e);
            response = new ResponseDto<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ServiceConstants.SA100, ServiceConstants.SA100M);
        }
        LOGGER.debug("newClient retorna: {}",response);
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
            validationUtils.validate(photo);
            if(photoRepository.findByClientId(clientId)==null){
                photoRepository.insert(photo);
                response = new ResponseDto<>(HttpStatus.CREATED.value(), ServiceConstants.SA002, ServiceConstants.SA002M, "MongoId de la imagen: "+photo.getId());
            }
            else{
                response = new ResponseDto<>(HttpStatus.FORBIDDEN.value(), ServiceConstants.SA003, ServiceConstants.SA003M);
            }
        }catch (ServiceCreateException e){
            LOGGER.error("Error in addPhoto", e);
            response = new ResponseDto<>(e.getStatus(), e.getCode(), e.getMessage());
        }catch (Exception e) {
            response = new ResponseDto<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ServiceConstants.SA100, ServiceConstants.SA100M);
            LOGGER.error("Error in addPhoto", e);
        }
        LOGGER.debug("addPhoto retorna: {}", response);
        return  response;
    }

}
