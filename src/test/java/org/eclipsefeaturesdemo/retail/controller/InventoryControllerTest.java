package org.eclipsefeaturesdemo.retail.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;

import org.eclipsefeaturesdemo.retail.dto.StockAdjustmentRequest;
import org.eclipsefeaturesdemo.retail.dto.StockAdjustmentResponse;
import org.eclipsefeaturesdemo.retail.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(InventoryController.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InventoryService inventoryService;

    @Test
    void shouldCreateStockAdjustment() throws Exception {
        StockAdjustmentResponse response = new StockAdjustmentResponse(
                100L,
                1L,
                5,
                "New shipment",
                Instant.parse("2026-07-08T10:15:30Z"),
                17);

        when(inventoryService.adjustStock(any(StockAdjustmentRequest.class))).thenReturn(response);

        String requestJson = """
                {
                  "productId": 1,
                  "quantity": 5,
                  "reason": "New shipment"
                }
                """;

        mockMvc.perform(post("/api/inventory/adjustments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId").value(1))
                .andExpect(jsonPath("$.quantity").value(5))
                .andExpect(jsonPath("$.availableQuantityAfterAdjustment").value(17));
    }

    @Test
    void shouldRejectInvalidStockAdjustmentRequest() throws Exception {
        String invalidJson = """
                {
                  "productId": null,
                  "quantity": null,
                  "reason": ""
                }
                """;

        mockMvc.perform(post("/api/inventory/adjustments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.productId").exists())
                .andExpect(jsonPath("$.fieldErrors.quantity").exists())
                .andExpect(jsonPath("$.fieldErrors.reason").exists());
    }
}
