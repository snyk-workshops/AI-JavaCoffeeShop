package org.workshop.coffee.export;

import org.workshop.coffee.domain.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ExportOrder {


    private Long id;

    private Date orderDate;

    private List<ExportOrderLine> orderLines = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<ExportOrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<ExportOrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    @Override
    public String toString() {
        return "ExportOrder{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", orderLines=" + orderLines +
                '}';
    }

    public static ExportOrder fromOrder(Order order) {
        var exportOrder = new ExportOrder();
        exportOrder.setId(order.getId());
        exportOrder.setOrderDate(order.getOrderDate());
        var exportOrderLines = order.getOrderLines().stream()
                .map(ExportOrderLine::fromOrderLine)
                .collect(Collectors.toList());
        exportOrder.setOrderLines(exportOrderLines);
        return exportOrder;
    }
}
