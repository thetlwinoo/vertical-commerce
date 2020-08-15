package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.service.ProductBrandExtendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the ProductBrandExtendResource REST controller.
 *
 * @see ProductBrandExtendResource
 */
@SpringBootTest(classes = {VscommerceApp.class, TestSecurityConfiguration.class})
public class ProductBrandExtendResourceIT {

    private MockMvc restMockMvc;
    private final ProductBrandExtendService productBrandExtendService;

    public ProductBrandExtendResourceIT(ProductBrandExtendService productBrandExtendService) {
        this.productBrandExtendService = productBrandExtendService;
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ProductBrandExtendResource productBrandExtendResource = new ProductBrandExtendResource(productBrandExtendService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(productBrandExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/product-brand-extend/default-action"))
            .andExpect(status().isOk());
    }
}
