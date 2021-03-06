package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.service.OrderPackagesExtendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the OrderPackagesExtendResource REST controller.
 *
 * @see OrderPackagesExtendResource
 */
@SpringBootTest(classes = {VscommerceApp.class, TestSecurityConfiguration.class})
public class OrderPackagesExtendResourceIT {

    private MockMvc restMockMvc;
    private final OrderPackagesExtendService orderPackagesExtendService;

    public OrderPackagesExtendResourceIT(OrderPackagesExtendService orderPackagesExtendService) {
        this.orderPackagesExtendService = orderPackagesExtendService;
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        OrderPackagesExtendResource orderPackagesExtendResource = new OrderPackagesExtendResource(orderPackagesExtendService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(orderPackagesExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/order-packages-extend/default-action"))
            .andExpect(status().isOk());
    }
}
