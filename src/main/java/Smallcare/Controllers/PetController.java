package Smallcare.Controllers;


import Smallcare.IServices.IPetService;
import Smallcare.Models.Pet;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RequestMapping("/user/pets")
@Controller
public class PetController {

    @Autowired
    IPetService petService;

    @GetMapping("")
    public String pets(Model model) {
        List<Pet> pets = petService.findAll();
        if (!pets.isEmpty()) {
            model.addAttribute("pets", pets);
        }
        return "pets";
    }

    @GetMapping("/{id}")
    public String pet(Model model, @PathVariable Long id) {
        Pet pet = petService.findById(id).orElseThrow(EntityNotFoundException::new);
        if (pet != null) {
            model.addAttribute("pet", pet);
        }
        return "pet";
    }

    @GetMapping("/add")
    public String addPet(Model model) {
        return "add-pet";
    }

    @PostMapping("/add")
    public String postPet(@RequestParam(value = "name") String name,
                          @RequestParam(value = "description", required = false) String description,
                          @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

        Pet pet = new Pet(name, description, "default");
        System.out.println(pet.getId());

        if(file != null){
            System.out.println(file.getContentType());
            System.out.println(file.getName());
            file.transferTo(new File("C:\\Users\\Nazar\\Documents\\GitHub\\Small-care\\src\\main\\resources\\images\\" + file.getOriginalFilename()));
        } else {
            System.out.println("Fuck");
        }




//        MultipartFile multipartFile = new MockMultipartFile("sourceFile.tmp", "Hello World".getBytes());
//        InputStream initialStream = file.getInputStream();
//        byte[] buffer = new byte[initialStream.available()];
//        initialStream.read(buffer);
//
//        try (OutputStream outStream = new FileOutputStream(new File("src/main/resources/targetFile."))) {
//            outStream.write(buffer);
//        }
//        petService.save(pet);

        return "redirect:";
    }
}