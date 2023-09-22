package org.workshop.coffee.export;

import org.workshop.coffee.domain.Order;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

public class XMLExporter {

    public static String exportOrders(List<Order> orders) throws XMLStreamException, TransformerException {
        var stringOut = new StringWriter();
        var factory = XMLOutputFactory.newInstance();
        var writer = factory.createXMLStreamWriter(stringOut);

        writer.writeStartDocument("utf-8", "1.0");

        // <company>
        writer.writeStartElement("Orders");

        for (var order : orders) {
            writer.writeStartElement("Order");

            writeElement(writer, "id", order.getId().toString());
            writeElement(writer, "orderDate", order.getOrderDate().toString());

            for (var ol : order.getOrderLines()) {
                writer.writeStartElement("OrderLine");
                writeElement(writer, "id", ol.getId().toString());
                writeElement(writer, "quantity", String.valueOf(ol.getQuantity()));
                writeElement(writer, "productName", ol.getProductName());
                writeElement(writer, "price", String.valueOf(ol.getPrice()));
                writer.writeEndElement();
            }
            writer.writeEndElement();
        }
        writer.writeEndElement();
        writer.flush();
        writer.close();
        return formatXML(stringOut.toString());
    }

    private static void writeElement(XMLStreamWriter writer, String elementName, String value) throws XMLStreamException {
        writer.writeStartElement(elementName);
        writer.writeCharacters(value);
        writer.writeEndElement();
    }

    private static String formatXML(String xml) throws TransformerException {
        var transformerFactory = TransformerFactory.newInstance();
        var transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");

        var source = new StreamSource(new StringReader(xml));
        var output = new StringWriter();
        transformer.transform(source, new StreamResult(output));

        return output.toString();
    }

}
