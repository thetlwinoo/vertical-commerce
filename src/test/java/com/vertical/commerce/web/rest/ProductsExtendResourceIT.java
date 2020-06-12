package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.service.ProductsExtendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the ProductsExtendResource REST controller.
 *
 * @see ProductsExtendResource
 */
@SpringBootTest(classes = {VscommerceApp.class, TestSecurityConfiguration.class})
public class ProductsExtendResourceIT {

    private MockMvc restMockMvc;
    private final ProductsExtendService productsExtendService;

    public ProductsExtendResourceIT(ProductsExtendService productsExtendService) {
        this.productsExtendService = productsExtendService;
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ProductsExtendResource productsExtendResource = new ProductsExtendResource(productsExtendService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(productsExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/products-extend/default-action"))
            .andExpect(status().isOk());
    }
}
