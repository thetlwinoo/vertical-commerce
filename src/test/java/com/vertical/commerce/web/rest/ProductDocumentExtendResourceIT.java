package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.service.ProductDocumentExtendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the ProductDocumentExtendResource REST controller.
 *
 * @see ProductDocumentExtendResource
 */
@SpringBootTest(classes = {VscommerceApp.class, TestSecurityConfiguration.class})
public class ProductDocumentExtendResourceIT {

    private MockMvc restMockMvc;
    private final ProductDocumentExtendService productDocumentExtendService;

    public ProductDocumentExtendResourceIT(ProductDocumentExtendService productDocumentExtendService) {
        this.productDocumentExtendService = productDocumentExtendService;
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ProductDocumentExtendResource productDocumentExtendResource = new ProductDocumentExtendResource(productDocumentExtendService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(productDocumentExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/product-document-extend/default-action"))
            .andExpect(status().isOk());
    }
}
