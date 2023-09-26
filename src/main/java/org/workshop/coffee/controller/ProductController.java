package org.workshop.coffee.controller;

import org.springframework.web.util.HtmlUtils;
import org.workshop.coffee.domain.Product;
import org.workshop.coffee.domain.ProductType;
import org.workshop.coffee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;

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

    @GetMapping("/direct")
    public void directLink (@RequestParam String param, HttpServletResponse response) throws IOException {
        Product prod = productService.getProductByName(param);
        if (prod == null) {
            prod = new Product();
        }
        response.setContentType("text/html");
        var writer = response.getWriter();
        buildProductPage(param, prod.getDescription(), prod.getProductType(), prod.getPrice(), writer);
        writer.flush();
    }

    private void buildProductPage(String productName, String desc, ProductType productType, Double price, PrintWriter writer) throws IOException {
        String head = "<html>\n" +
                "  <head lang=\"en\">\n" +
                "    <title>CoffeeShop</title>\n" +
                "     \n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "    <link href=\"/webjars/bootstrap/3.3.4/css/bootstrap.min.css\" rel=\"stylesheet\" media=\"screen\" />\n" +
                "    <link href=\"/css/style.css\" rel=\"stylesheet\" media=\"screen\" />\n" +
                "    <script src=\"/webjars/jquery/2.1.4/jquery.js\"></script>\n" +
                "    <script src=\"/webjars/bootstrap/3.3.4/js/bootstrap.js\"></script>\n" +
                "   \n" +
                "  </head>\n" +
                "  <body><div class=\"container\"><div class=\"panel panel-default\">";

        String foot = "  </div></div></body>\n" +
                "</html>";

        writer.write(head);

        writer.write("<div class=\"panel-heading\"><h1>" + productName + "</h1></div>");

        String output = "<div class=\"panel-body\">" +
                "<ul>" +
                "<li>%s</li>" +
                "<li>%s</li>" +
                "<li>%s</li>" +
                "</ul>" +
                "</div>";

        writer.write(String.format(output, desc, productType, price));
        writer.write(foot);


    }
}
