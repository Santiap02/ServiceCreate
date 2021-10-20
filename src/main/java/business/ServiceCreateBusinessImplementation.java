package business;

import Domain.ResponseDto;
import Model.Cliente;
import Model.Photo;
import Repository.ClientRepository;
import Repository.PhotoRepository;
import Util.ServiceConstants;
import lombok.AllArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


@Service
@AllArgsConstructor

public class ServiceCreateBusinessImplementation implements ServiceCreateBusiness {
    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceCreateBusinessImplementation.class);
    /** Objeto para acceder a la capa de datos de clientes */
    private final ClientRepository clientRepository;
    /** Objeto para acceder a la capa de datos de fotos */
    private final PhotoRepository photoRepository;


    @Override
    public ResponseDto<String> agregarCliente(Cliente cliente) {
        LOGGER.debug("Se inicia agregarCliente");
        var response = new ResponseDto<String>();
        try {
            if(clientRepository.findById(cliente.getIdCliente()).isEmpty()){
                clientRepository.save(cliente);
                response = new ResponseDto<String>(HttpStatus.OK.value(), ServiceConstants.SA002, ServiceConstants.SA002M);
            }
            else {
                response = new ResponseDto<String>(HttpStatus.BAD_REQUEST.value(), ServiceConstants.SA003, ServiceConstants.SA003M);
            }
        }catch (Exception e){
            LOGGER.error("Error in getClients", e);
            response = new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ServiceConstants.SA100, ServiceConstants.SA100M);
        }
        LOGGER.debug("AgregarCliente retorna: "+response);
        return  response;
    }



    @Override
    public ResponseDto<String> addPhoto(int ClientId, MultipartFile file) throws IOException {
        LOGGER.debug("Se inicia addPhoto");
        Photo photo = new Photo(ClientId);
        photo.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        ResponseDto<String> response = new ResponseDto<>();

        try {
            if(photoRepository.findByClientId(ClientId)==null){
                photo = photoRepository.insert(photo);
                response = new ResponseDto<String>(HttpStatus.OK.value(), ServiceConstants.SA002, ServiceConstants.SA002M, "MongoId de la imagen: "+photo.getId());

            }
            else{
                response = new ResponseDto<String>(HttpStatus.BAD_REQUEST.value(), ServiceConstants.SA003, ServiceConstants.SA003M);
            }
        } catch (Exception e) {
            response = new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ServiceConstants.SA100, ServiceConstants.SA100M);
            LOGGER.error("Error in getClients", e);
        }
        LOGGER.debug("addPhoto retorna: "+response);
        return  response;

    }

    @Override
    public String addPhoto2(int title, String image) {
        Photo photo = new Photo(title);
        photo.setImage(new Binary(BsonBinarySubType.BINARY, image.getBytes()));
        if(photoRepository.findByClientId(title)==null){
            photo = photoRepository.insert(photo);
            return photo.getId();

        }
        else{
            return "La imagen ya existe";
        }
    }
}
