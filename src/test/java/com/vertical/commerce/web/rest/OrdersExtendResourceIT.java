package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.service.OrdersExtendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the OrdersExtendResource REST controller.
 *
 * @see OrdersExtendResource
 */
@SpringBootTest(classes = {VscommerceApp.class, TestSecurityConfiguration.class})
public class OrdersExtendResourceIT {

    private MockMvc restMockMvc;
    private final OrdersExtendService ordersExtendService;

    public OrdersExtendResourceIT(OrdersExtendService ordersExtendService) {
        this.ordersExtendService = ordersExtendService;
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        OrdersExtendResource ordersExtendResource = new OrdersExtendResource(ordersExtendService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(ordersExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/orders-extend/default-action"))
            .andExpect(status().isOk());
    }
}
