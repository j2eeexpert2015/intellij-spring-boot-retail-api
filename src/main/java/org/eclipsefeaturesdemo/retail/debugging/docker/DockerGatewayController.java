package org.eclipsefeaturesdemo.retail.debugging.docker;

import org.eclipsefeaturesdemo.retail.dto.ProductResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/debug/gateway")
@ConditionalOnProperty(name = "retail.peer-url")
public class DockerGatewayController {

    private final PeerProductService peerProductService;

    public DockerGatewayController(PeerProductService peerProductService) {
        this.peerProductService = peerProductService;
    }

    @GetMapping("/product/{id}")
    public ProductResponse findProduct(@PathVariable Long id) {
        return peerProductService.findProduct(id);
    }
}