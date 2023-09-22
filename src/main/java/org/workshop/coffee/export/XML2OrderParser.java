package org.workshop.coffee.export;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class XML2OrderParser {

    private SAXParserFactory factory = SAXParserFactory.newInstance();



    public List<ExportOrder> parse(InputStream f) throws ParserConfigurationException, SAXException, IOException {
        return null;
    }

    class OrderHandler extends DefaultHandler {
        boolean order, od, oline, olquant, olpname, olprice = false;
        String orderDate = "", lineQuantity = "", lineProductName = "", linePrice = "";

        public List<ExportOrder> orders = new ArrayList<>();
        List<ExportOrderLine> orderLines = new ArrayList();

        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equalsIgnoreCase("Order")) order = true;
            if (qName.equalsIgnoreCase("orderDate")) od = true;

            if (qName.equalsIgnoreCase("OrderLine")) oline = true;
            if (qName.equalsIgnoreCase("quantity")) olquant = true;
            if (qName.equalsIgnoreCase("productName")) olpname = true;
            if (qName.equalsIgnoreCase("price")) olprice = true;
        }

        public void characters(char ch[], int start, int length) throws SAXException {

            if (od && order) {
                orderDate += new String(ch, start, length);
            }

            if (olquant && oline && order) {
                lineQuantity += new String(ch, start, length);
            }

            if (olpname && oline && order) {
                lineProductName += new String(ch, start, length);
            }

            if (olprice && oline && order) {
                linePrice += new String(ch, start, length);
            }

        }

        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (qName.equalsIgnoreCase("Order")) order = false;
            if (qName.equalsIgnoreCase("orderDate")) od = false;

            if (qName.equalsIgnoreCase("OrderLine")) oline = false;
            if (qName.equalsIgnoreCase("quantity")) olquant = false;
            if (qName.equalsIgnoreCase("productName")) olpname = false;
            if (qName.equalsIgnoreCase("price")) olprice = false;


            if (qName.equalsIgnoreCase("OrderLine")) {
                ExportOrderLine ol = new ExportOrderLine();
                ol.setPrice(Double.valueOf(linePrice));
                ol.setProductName(lineProductName);
                ol.setQuantity(Integer.parseInt(lineQuantity));
                orderLines.add(ol);
                resetOline();
            }

            if (qName.equalsIgnoreCase("Order")) {
                ExportOrder order = new ExportOrder();
                order.setOrderDate(Date.valueOf(orderDate));
                order.setOrderLines(orderLines);
                orders.add(order);
                resetOrder();
            }

        }

        private void resetOline() {
            oline = false;
            olquant = false;
            olpname = false;
            olprice = false;

            lineQuantity = "";
            lineProductName = "";
            linePrice = "";
        }

        private void resetOrder() {
            order = false;
            od = false;
            orderDate = "";

            resetOline();
            orderLines = new ArrayList<>();
        }

    }

    ;
}

