package Controller;

import Model.Cliente;
import Model.Photo;
import Service.DataService;
import Service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "*")
@RestController
public class controlador {
    @Autowired
    DataService servicio;
    @Autowired
    PhotoService photoService;

    //Save client
    @PostMapping(value="/clientes",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.TEXT_PLAIN_VALUE)
    public String guardarCliente(@RequestBody Cliente cliente) {
        return String.valueOf(servicio.agregarCliente(cliente));
    }

    //Images MongoDB

    @PostMapping("/photos/add")
    public String addPhoto(@RequestParam("title") int title, @RequestParam("image") MultipartFile image, Model model) throws IOException {
        String id = photoService.addPhoto(title, image);
        return "redirect:/photos/" + id;
    }
    /*
    @PostMapping("/photos/add")
    public String addPhoto(@RequestParam("title") int title, @RequestParam("image") String image) {
        String id = photoService.addPhoto2(title, image);
        return "redirect:/photos/" + id;
    }
    */
}
