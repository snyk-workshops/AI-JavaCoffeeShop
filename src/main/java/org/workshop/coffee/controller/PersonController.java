package org.workshop.coffee.controller;

import org.workshop.coffee.domain.Person;
import org.workshop.coffee.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/persons")
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public String listPersons(Model model) {
        model.addAttribute("persons", personService.getAllPersons());
        return "person/list";
    }

    @GetMapping("/edit/{id}")
    public String showPersonEdit(@PathVariable Long id, Model model) {
        Person person = personService.findById(id);
        model.addAttribute("person", person);
        return "person/edit";
    }

    @GetMapping("/add")
    public String showPersonAdd(Model model) {
        model.addAttribute("person", new Person());
        return "person/edit";
    }

    @PostMapping({"/edit/{id}", "/add"})
    public String savePerson(@Valid Person person, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "person/edit";
        }

        boolean isAdd = person.getId() == null;

        if (isAdd) {
            person.setPassword("123123");
        }

        personService.savePerson(person);

        if (isAdd) {
            redirectAttributes.addFlashAttribute("message", "Person has been created successfully.");
        } else {
            redirectAttributes.addFlashAttribute("message", "Person has been updated successfully.");
        }

        return "redirect:/persons/edit/" + person.getId();
    }

    @GetMapping("/delete/{id}")
    public String deletePerson(@PathVariable Long id) {
        personService.removePerson(id);
        return "redirect:/persons";
    }

}
