package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.service.ShoppingCartsExtendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the ShoppingCartsExtendResource REST controller.
 *
 * @see ShoppingCartsExtendResource
 */
@SpringBootTest(classes = {VscommerceApp.class, TestSecurityConfiguration.class})
public class ShoppingCartsExtendResourceIT {

    private MockMvc restMockMvc;
    private final ShoppingCartsExtendService shoppingCartsExtendService;

    public ShoppingCartsExtendResourceIT(ShoppingCartsExtendService shoppingCartsExtendService) {
        this.shoppingCartsExtendService = shoppingCartsExtendService;
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ShoppingCartsExtendResource shoppingCartsExtendResource = new ShoppingCartsExtendResource(shoppingCartsExtendService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(shoppingCartsExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/shopping-carts-extend/default-action"))
            .andExpect(status().isOk());
    }
}
