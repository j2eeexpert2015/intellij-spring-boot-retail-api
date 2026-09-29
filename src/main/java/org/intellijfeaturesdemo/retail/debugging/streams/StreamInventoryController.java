package org.intellijfeaturesdemo.retail.debugging.streams;

import java.util.List;

import org.intellijfeaturesdemo.retail.dto.InventoryReportRow;
import org.intellijfeaturesdemo.retail.dto.ProductActivityRow;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/debug/streams")
public class StreamInventoryController {

    private final StreamInventoryService streamInventoryService;

    public StreamInventoryController(StreamInventoryService streamInventoryService) {
        this.streamInventoryService = streamInventoryService;
    }

    @GetMapping("/all-products")
    public List<ProductActivityRow> findAllProductActivity() {
        return streamInventoryService.findAllProductActivity();
    }

    @GetMapping("/active-product-names")
    public List<String> findActiveProductNames() {
        return streamInventoryService.findActiveProductNames();
    }

    @GetMapping("/top-inventory")
    public List<InventoryReportRow> findTopInventory(
            @RequestParam(defaultValue = "3") int limit) {
        return streamInventoryService.findTopInventory(limit);
    }
}
