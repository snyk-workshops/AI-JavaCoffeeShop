package nl.brianvermeer.workshop.coffee.controller;

import nl.brianvermeer.workshop.coffee.domain.Order;
import nl.brianvermeer.workshop.coffee.domain.OrderLine;
import nl.brianvermeer.workshop.coffee.domain.Product;
import nl.brianvermeer.workshop.coffee.service.OrderService;
import nl.brianvermeer.workshop.coffee.service.PersonService;
import nl.brianvermeer.workshop.coffee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final ProductService productService;
    private final OrderService orderService;
    private final PersonService personService;

    @Autowired
    public OrderController(ProductService productService, OrderService orderService, PersonService personService) {
        this.productService = productService;
        this.orderService = orderService;
        this.personService = personService;
    }

    @ModelAttribute("products")
    public List<Product> populateProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/add")
    public String showOrderForm(Model model) {
        Order order = new Order();
        order.setOrderDate(new Date());
        order.addOrderLine(new OrderLine());

        model.addAttribute("order", order);

        return "order/add";
    }

    @PostMapping(value = "/add", params = "addOrderLine")
    public String addOrderLine(Order order, BindingResult result) {
        OrderLine orderLine = new OrderLine();
        order.addOrderLine(orderLine);

        return "order/add";
    }

    @PostMapping(value = "/add", params = "removeOrderLine")
    public String removeOrderLine(Order order, BindingResult result, HttpServletRequest request) {
        int orderLineId = Integer.valueOf(request.getParameter("removeOrderLine"));
        order.getOrderLines().remove(orderLineId);

        return "order/add";
    }

    @PostMapping("/add")
    public String saveOrder(@Valid Order order, BindingResult result, Principal principal, RedirectAttributes redirectAttributes) {
        if (!result.hasErrors()) {
            try {
                List<OrderLine> orderLines = order.getOrderLines();
                int lastOrderLineIndex = orderLines.size() - 1;

                if (orderLines.get(lastOrderLineIndex).getProduct() == null) {
                    orderLines.remove(lastOrderLineIndex);
                }

                if (orderLines.size() == 0) {
                    order.addOrderLine(new OrderLine());
                    throw new Exception("At least one product must be added to the Order");
                }

                for (OrderLine orderLine : orderLines) {
                    if (orderLine.getQuantity() == 0) {
                        throw new Exception("Quantity of product " + orderLine.getProduct().getProductName() + " must be positive");
                    }
                }

                order.setPerson(personService.findByUsername(principal.getName()));
                order.getOrderLines().forEach(orderLine -> {
                    orderLine.setOrder(order);
                });

                orderService.save(order);

                redirectAttributes.addFlashAttribute("message", "Your order has been placed successfully.");

                return "redirect:/orders/add";
            } catch (Exception e) {
                result.reject(null, e.getMessage());
            }
        }

        return "order/add";
    }

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "order/list";
    }
}
