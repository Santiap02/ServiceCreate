package business;

import Model.Cliente;
import Model.Photo;
import Repository.ClientRepository;
import Repository.PhotoRepository;
import lombok.AllArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


@Service
@AllArgsConstructor

public class ServiceGetBusinessImplementation implements ServiceGetBusiness{
    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceGetBusinessImplementation.class);
    /** Objeto para acceder a la capa de datos de clientes */
    private final ClientRepository clientRepository;
    /** Objeto para acceder a la capa de datos de fotos */
    private final PhotoRepository photoRepository;


    @Override
    public boolean agregarCliente(Cliente cliente) {
        if(clientRepository.findById(cliente.getIdCliente()).isEmpty()){

            clientRepository.save(cliente);

            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void actualizarCliente(Cliente cliente) {
        clientRepository.save(cliente);
    }

    @Override
    public String addPhoto(int ClientId, MultipartFile file) throws IOException {
        Photo photo = new Photo();
        photo.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        if(photoRepository.findByClientId(ClientId)==null){
            photo = photoRepository.insert(photo);
            return photo.getId();

        }
        else{
            return "La imagen ya existe";
        }


    }

    @Override
    public String addPhoto2(int title, String image) {
        return null;
    }
}
