package org.eclipsefeaturesdemo.retail.debugging.docker;

import org.eclipsefeaturesdemo.retail.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@ConditionalOnProperty(name = "retail.peer-url")
public class PeerProductService {

    private final RestClient restClient;

    public PeerProductService(
            RestClient.Builder restClientBuilder,
            @Value("${retail.peer-url}") String peerUrl) {

        this.restClient = restClientBuilder
                .baseUrl(peerUrl)
                .build();
    }

    public ProductResponse findProduct(Long id) {
        return restClient.get()
                .uri("/api/products/{id}", id)
                .retrieve()
                .body(ProductResponse.class);
    }
}