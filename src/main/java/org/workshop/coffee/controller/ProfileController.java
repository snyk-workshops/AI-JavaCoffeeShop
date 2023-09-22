package org.workshop.coffee.controller;

import org.workshop.coffee.domain.Person;
import org.workshop.coffee.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final PersonService personService;

    @Autowired
    public ProfileController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public String showProfile(Model model, Principal principal) {
        Person person = personService.findByUsername(principal.getName());
        model.addAttribute("person", person);
        return "profile";
    }

    @PostMapping
    public String saveProfile(@Valid Person person, BindingResult result, RedirectAttributes redirectAttributes) {
        String password = person.getPassword();
        String passwordConfirm = person.getPasswordConfirm();

        if (!password.isEmpty() && !password.equals(passwordConfirm)) {
            result.rejectValue("passwordConfirm", null, "Password does not match");
        }

        if (result.hasErrors()) {
            return "profile";
        }

        boolean isAdd = person.getId() == null;

        personService.savePerson(person);

        if (isAdd) {
            redirectAttributes.addFlashAttribute("message", "Your profile has been created successfully.");
        } else {
            redirectAttributes.addFlashAttribute("message", "Your profile has been updated successfully.");
        }

        return "redirect:/profile";
    }
}
