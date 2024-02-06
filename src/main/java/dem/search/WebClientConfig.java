package dem.search;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class WebClientConfig {

    @Value("${category.domain}")
    private String categoryDomain;

    @Value("${category.domain}")
    private String brandDomain;

    @Bean
    public WebClient categoryClient() {
        return commonClient(10, 10, 2, categoryDomain);
    }


    @Bean
    public WebClient brandClient() {
        return commonClient(6, 6, 2, brandDomain);
    }


    private WebClient commonClient(int readTimeout, int writeTimeout, int responseTimeout, String domain) {
        var httpClient = HttpClient.create()
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(readTimeout))
                        .addHandlerLast(new WriteTimeoutHandler(writeTimeout)))
                .responseTimeout(Duration.ofSeconds(responseTimeout))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000);

        var connector = new ReactorClientHttpConnector(httpClient);

        return WebClient.builder()
                .clientConnector(connector)
                .baseUrl(domain)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
