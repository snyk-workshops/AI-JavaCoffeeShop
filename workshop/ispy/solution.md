# Solution Fix for I SPY Assignment

The XML parsing is done in the `XML2OrderParser`

You can choose to disallow doctype declaration all together to solve the problem by setting `"http://apache.org/xml/features/disallow-doctype-decl"` to true
Alternatively you can just disallow external entities by `"http://xml.org/sax/features/external-general-entities"` to false

For the `SAXParser` that we use in this application we can either do this on the `factory` or the `parser`

## Solution 1

Create a constructor in `XML2OrderParser` that sets both (or one of the 2) features on the factory level

```java
    public XML2OrderParser()  {
        try {
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        } catch (ParserConfigurationException | SAXNotRecognizedException | SAXNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
```

## Solution 2

Set the these features to the created parser instead of the factory

```java
    public List<ExportOrder> parse(InputStream f) throws ParserConfigurationException, SAXException, IOException {
        SAXParser saxParser = factory.newSAXParser();
        saxParser.setProperty("http://apache.org/xml/features/disallow-doctype-decl", true);
        saxParser.setProperty("http://xml.org/sax/features/external-general-entities", false);
        ...    


```