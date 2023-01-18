package nl.brianvermeer.workshop.coffee.controller;

import nl.brianvermeer.workshop.coffee.domain.Person;
import nl.brianvermeer.workshop.coffee.exception.EmailTakenException;
import nl.brianvermeer.workshop.coffee.exception.UsernameTakenException;
import nl.brianvermeer.workshop.coffee.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final PersonService personService;

    @Autowired
    public RegisterController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public String showRegisterForm(Model model) {
        model.addAttribute("person", new Person());
        return "register";
    }

    @PostMapping
    public String register(@Valid Person person, BindingResult result, Model model, HttpServletRequest request) {
        String password = person.getPassword();
        String passwordConfirm = person.getPasswordConfirm();

        if (password.isEmpty()) {
            result.rejectValue("password", null, "Password cannot be empty");
        }

        if (passwordConfirm.isEmpty()) {
            result.rejectValue("passwordConfirm", null, "Password confirm cannot be empty");
        }

        if (!password.equals(passwordConfirm)) {
            result.rejectValue("passwordConfirm", null, "Password does not match");
        }

        boolean hasError = result.hasErrors();

        if (!hasError) {
            try {
                personService.registerNewPerson(person);
                loginAfterRegistering(person, request);
            } catch (EmailTakenException e) {
                hasError = true;
                result.rejectValue("email", "", "Email is already taken");
            } catch (UsernameTakenException e) {
                hasError = true;
                result.rejectValue("username", "", "Username is already taken");
            }
        }

        if (hasError) {
            model.addAttribute("person", person);
            return "register";
        }

        return "redirect:/";
    }

    private void loginAfterRegistering(Person person, HttpServletRequest request) {
//        UsernamePasswordAuthenticationToken token;
//        token = new UsernamePasswordAuthenticationToken(person.getUsername(), person.getPassword());
//
//        // create session if not exist
//        request.getSession();
//
//        token.setDetails(new WebAuthenticationDetails(request));
//
//        Authentication authentication = authenticationManager.authenticate(token);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
