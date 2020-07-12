package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.service.CustomersExtendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the CustomersExtendResource REST controller.
 *
 * @see CustomersExtendResource
 */
@SpringBootTest(classes = {VscommerceApp.class, TestSecurityConfiguration.class})
public class CustomersExtendResourceIT {

    private MockMvc restMockMvc;
    private final CustomersExtendService customersExtendService;

    public CustomersExtendResourceIT(CustomersExtendService customersExtendService) {
        this.customersExtendService = customersExtendService;
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        CustomersExtendResource customersExtendResource = new CustomersExtendResource(customersExtendService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(customersExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/customers-extend/default-action"))
            .andExpect(status().isOk());
    }
}
