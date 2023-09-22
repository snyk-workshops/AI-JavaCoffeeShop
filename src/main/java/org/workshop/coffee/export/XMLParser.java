package org.workshop.coffee.export;

import javax.management.modelmbean.XMLParseException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {

    XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

    XMLEventReader reader;
    List<ExportOrder> orders = new ArrayList<>();
    ExportOrder order = null;
    ExportOrderLine orderLine = null;

    public static XMLParser getNewInstance() {
        return new XMLParser();
    }



    public List<ExportOrder> parse(InputStream inputStream) throws XMLStreamException, XMLParseException {
        reader = xmlInputFactory.createXMLEventReader(inputStream);

        while (reader.hasNext()) {
            var nextEvent = reader.nextEvent();
            handleEvent(nextEvent);
        }

        return orders;
    }

    private void handleEvent(XMLEvent nextEvent) throws XMLParseException, XMLStreamException {
        if (nextEvent.isStartElement()) {
            StartElement startElement = nextEvent.asStartElement();
            switch (startElement.getName().getLocalPart()) {

                case "Order":
                    order = new ExportOrder();
                    break;

                case "orderDate":
                    nextEvent = reader.nextEvent();
                    if (order == null) throw new XMLParseException("Invalid XML");
                    order.setOrderDate(Date.valueOf(nextEvent.asCharacters().getData()));
                    break;

                case "OrderLine":
                    if (order == null) throw new XMLParseException("Invalid XML");
                    orderLine = new ExportOrderLine();
                    break;

                case "quantity":
                    nextEvent = reader.nextEvent();
                    if (orderLine == null) throw new XMLParseException("Invalid XML");
                    orderLine.setQuantity(Integer.parseInt(nextEvent.asCharacters().getData()));
                    break;

                case "productName":
                    nextEvent = reader.nextEvent();
                    if (orderLine == null) throw new XMLParseException("Invalid XML");
                    String productName = "";
                    while (nextEvent.isCharacters()) {
                       productName += nextEvent.asCharacters().getData();
                       nextEvent = reader.nextEvent();
                    }
                    orderLine.setProductName(productName);
                    handleEvent(nextEvent);
                    break;

                case "price":
                    nextEvent = reader.nextEvent();
                    if (orderLine == null) throw new XMLParseException("Invalid XML");
                    orderLine.setPrice(Double.valueOf(nextEvent.asCharacters().getData()));
                    break;

            }
        }

        if (nextEvent.isEndElement()) {
            EndElement endElement = nextEvent.asEndElement();
            switch (endElement.getName().getLocalPart()){
                case "Order":
                    orders.add(order);
                    break;

                case "OrderLine":
                    order.getOrderLines().add(orderLine);
                    break;

            }
        }
    }



}
