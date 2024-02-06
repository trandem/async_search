//package dem.search;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.client.ClientConfiguration;
//import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchConfiguration;
//
//@Configuration
//public class SearchElasticSearchConfig extends ReactiveElasticsearchConfiguration {
//    @Value("${elastic.hostAndPort}")
//    private String esHostPort;
//
//    @Override
//    public ClientConfiguration clientConfiguration() {
//        return ClientConfiguration.builder()
//                .connectedTo(esHostPort)
//                .build();
//    }
//}
