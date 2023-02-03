package nl.brianvermeer.workshop.coffee.export;

import nl.brianvermeer.workshop.coffee.domain.Order;
import nl.brianvermeer.workshop.coffee.domain.OrderLine;
import nl.brianvermeer.workshop.coffee.domain.Person;

public class ExportOrderConvertor {

    public static Order createOrders(ExportOrder eo, Person person) {
        var order = new Order();
        order.setOrderDate(eo.getOrderDate());
        order.setPerson(person);
        eo.getOrderLines().forEach(eol -> order.addOrderLine(createOrderLines(eol)));
        return order;
    }

    private static OrderLine createOrderLines(ExportOrderLine eol) {
        var line = new OrderLine();
        line.setPrice(eol.getPrice());
        line.setProductName(eol.getProductName());
        line.setQuantity(eol.getQuantity());
        return line;
    }
}
