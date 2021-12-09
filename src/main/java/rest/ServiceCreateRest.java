package rest;

import domain.ClienteDto;
import domain.ResponseDto;
import business.ServiceCreateBusiness;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import Model.Cliente;

@CrossOrigin(origins = "*")
@AllArgsConstructor
@RestController
public class ServiceCreateRest {

    private final ServiceCreateBusiness serviceGetBusiness;

    @Operation(summary = "Creación de nuevo cliente", description = "Permite crear un nuevo objeto con la informacion de un cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cliente guardado exitosamente", response = ResponseDto.class),
            @ApiResponse(code = 403, message = "El cliente ya existe", response = ResponseDto.class),
            @ApiResponse(code = 400, message = "Solicitud incorrecta. Por favor valide los datos enviados.", response = ResponseDto.class),
            @ApiResponse(code = 500, message = "Error inesperado durante el proceso", response = ResponseDto.class) })
    @PostMapping(value="/clientes",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto<String> saveClient(@RequestBody ClienteDto cliente){
        return this.serviceGetBusiness.newClient(cliente);
    }

    @Operation(summary = "Creación de imagen de un cliente", description = "Permite crear una nueva imagen de un cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cliente guardado exitosamente", response = ResponseDto.class),
            @ApiResponse(code = 403, message = "El cliente ya existe", response = ResponseDto.class),
            @ApiResponse(code = 500, message = "Error inesperado durante el proceso", response = ResponseDto.class) })
    @PostMapping("/photos/add")
    public ResponseDto<String> addPhoto(@Parameter(name = "clientId", required = true, description = "Id del cliente a actualizar", schema = @Schema(implementation = int.class), in = ParameterIn.QUERY)@RequestParam("clientId") int clientId,
                                        @Parameter(name = "image", required = true, description = "Imagen nueva", schema = @Schema(implementation = MultipartFile.class), in = ParameterIn.QUERY)@RequestParam("image") MultipartFile image){
        return serviceGetBusiness.addPhoto(clientId, image);
    }

}
