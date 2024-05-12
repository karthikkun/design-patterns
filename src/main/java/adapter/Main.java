package adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

interface XmlStockDataProvider {
    String getStockDataAsXml();
}

abstract class JsonAnalyticsLibrary {
    void processStockData(String jsonData) {
        System.out.println("Processing JSON Data: " + jsonData);
    }
}

class XmlToJsonStockDataAdapter extends JsonAnalyticsLibrary {
    private XmlStockDataProvider provider;

    public XmlToJsonStockDataAdapter(XmlStockDataProvider provider) {
        this.provider = provider;
    }

    void processXMLStockData() {
        String xmlData = this.provider.getStockDataAsXml();
        XmlMapper xmlMapper = new XmlMapper();
        try {
            Object obj = xmlMapper.readValue(xmlData, Object.class);
            ObjectMapper jsonMapper = new ObjectMapper();
            String jsonData = jsonMapper.writeValueAsString(obj);
            this.processStockData(jsonData);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

/**
 * 1. encapsulating the conversion logic within the adapter, the system eliminates
 * the need for repeated code across various parts of the application that might otherwise need to convert XML to JSON
 * 2. Adapter pattern allows you to integrate new types of data sources and formats
 * without significant changes to existing code. calability is crucial for evolving systems,
 */


public class Main {
    public static void main(String[] args) {
        XmlStockDataProvider provider = () -> "<stock><name>Apple Inc.</name><price>150.10</price></stock>";
        XmlToJsonStockDataAdapter adapter = new XmlToJsonStockDataAdapter(provider);
//        making changes to provider
//        JsonAnalyticsLibrary lib = new JsonAnalyticsLibrary() {
//            @Override
//            void processStockData(String jsonData) {
//                System.out.println("Processing JSON Data: " + jsonData);
//            }
//        };

//        lib.processStockData(provider.getStockDataAsJson());
        adapter.processXMLStockData();
    }
}
