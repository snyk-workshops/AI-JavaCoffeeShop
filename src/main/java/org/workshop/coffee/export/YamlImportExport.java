package org.workshop.coffee.export;

import org.workshop.coffee.domain.Order;
import org.workshop.coffee.domain.Person;
import org.springframework.http.MediaType;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class YamlImportExport {


    public static final MediaType MEDIA_TYPE_YAML = MediaType.valueOf("text/yaml");

    public static String exportOrders(List<Order> orders) {
        var exportOrders = orders.stream()
                .map(ExportOrder::fromOrder)
                .collect(Collectors.toList());

        DumperOptions options = new DumperOptions();
        options.setIndent(2);
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);
        return yaml.dump(exportOrders);
    }


    public static List<Order> importOrders(InputStream inputStream, Person person) {
        Yaml yaml = new Yaml();
        List<ExportOrder> orders = yaml.load(inputStream);
        return orders.stream()
                .map(eo -> ExportOrderConvertor.createOrders(eo, person))
                .collect(Collectors.toList());
    }

}
