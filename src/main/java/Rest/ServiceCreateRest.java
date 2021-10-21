package Rest;

import Domain.ResponseDto;
import business.ServiceCreateBusiness;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import Model.Cliente;
import java.io.IOException;

@CrossOrigin(origins = "*")
@AllArgsConstructor
@RestController
public class ServiceCreateRest {
    private final ServiceCreateBusiness serviceGetBusiness;




    //Save client
    @PostMapping(value="/clientes",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto<String> guardarCliente(@RequestBody Cliente cliente){
        return this.serviceGetBusiness.agregarCliente(cliente);
    }


    @PostMapping("/photos/add")
    public ResponseDto<String> addPhoto(@RequestParam("title") int clientId, @RequestParam("image") MultipartFile image, Model model) throws IOException {
        var response = serviceGetBusiness.addPhoto(clientId, image);
        return response;
    }

}
