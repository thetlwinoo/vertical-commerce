package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.service.CustomerPaymentExtendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the CustomerPaymentExtendResource REST controller.
 *
 * @see CustomerPaymentExtendResource
 */
@SpringBootTest(classes = {VscommerceApp.class, TestSecurityConfiguration.class})
public class CustomerPaymentExtendResourceIT {

    private MockMvc restMockMvc;
    private final CustomerPaymentExtendService customerPaymentExtendService;

    public CustomerPaymentExtendResourceIT(CustomerPaymentExtendService customerPaymentExtendService) {
        this.customerPaymentExtendService = customerPaymentExtendService;
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        CustomerPaymentExtendResource customerPaymentExtendResource = new CustomerPaymentExtendResource(customerPaymentExtendService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(customerPaymentExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/customer-payment-extend/default-action"))
            .andExpect(status().isOk());
    }
}
