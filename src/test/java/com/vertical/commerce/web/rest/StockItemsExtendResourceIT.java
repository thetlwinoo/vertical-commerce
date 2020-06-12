package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.service.StockItemsExtendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the StockItemsExtendResource REST controller.
 *
 * @see StockItemsExtendResource
 */
@SpringBootTest(classes = {VscommerceApp.class, TestSecurityConfiguration.class})
public class StockItemsExtendResourceIT {

    private MockMvc restMockMvc;
    private final StockItemsExtendService stockItemsExtendService;

    public StockItemsExtendResourceIT(StockItemsExtendService stockItemsExtendService) {
        this.stockItemsExtendService = stockItemsExtendService;
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        StockItemsExtendResource stockItemsExtendResource = new StockItemsExtendResource(stockItemsExtendService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(stockItemsExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/stock-items-extend/default-action"))
            .andExpect(status().isOk());
    }
}
