package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.service.OrderLinesExtendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the OrderLinesExtendResource REST controller.
 *
 * @see OrderLinesExtendResource
 */
@SpringBootTest(classes = {VscommerceApp.class, TestSecurityConfiguration.class})
public class OrderLinesExtendResourceIT {

    private MockMvc restMockMvc;
    private final OrderLinesExtendService orderLinesExtendService;

    public OrderLinesExtendResourceIT(OrderLinesExtendService orderLinesExtendService) {
        this.orderLinesExtendService = orderLinesExtendService;
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        OrderLinesExtendResource orderLinesExtendResource = new OrderLinesExtendResource(orderLinesExtendService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(orderLinesExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/order-lines-extend/default-action"))
            .andExpect(status().isOk());
    }
}
