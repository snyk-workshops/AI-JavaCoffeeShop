package nl.brianvermeer.workshop.coffee.controller;

import nl.brianvermeer.workshop.coffee.domain.Product;
import nl.brianvermeer.workshop.coffee.service.ProductService;
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
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/add")
    public String showProductAdd(Model model) {
        model.addAttribute("product", new Product());
        return "product/edit";
    }

    @PostMapping({"/edit/{id}", "/add"})
    public String saveProduct(@Valid Product product, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        boolean isAdd = product.getId() == null;

        if (result.hasErrors()) {
            return "product/edit";
        }

        productService.save(product);

        if (isAdd) {
            redirectAttributes.addFlashAttribute("message", "Your product has been created successfully.");
        } else {
            redirectAttributes.addFlashAttribute("message", "Your product has been updated successfully.");
        }

        return "redirect:/products/edit/" + product.getId();
    }

    @GetMapping("/edit/{id}")
    public String showProductEdit(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProduct(id));
        return "product/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/";
    }
}
