package org.workshop.coffee.controller;

import org.workshop.coffee.service.PersonService;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;

public class OldUploadController {

    private PersonService personService;

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";


        public String uploadImage(Model model, MultipartFile file, Principal principal) throws IOException {
        var name = file.getOriginalFilename().replace(" ", "_");
        var fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, name);
        Files.write(fileNameAndPath, file.getBytes());
            model.addAttribute("msg", "Uploaded images: " + name);

        if (principal == null) {
            model.addAttribute("message", "ERROR");
            return "person/upload";
        }

        var user = principal.getName();
        var person = personService.findByUsername(user);

        person.setProfilePic(name);
        personService.savePerson(person);
        return "person/upload";
    }
}
