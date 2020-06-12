package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.service.SpecialDealsExtendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the SpecialDealsExtendResource REST controller.
 *
 * @see SpecialDealsExtendResource
 */
@SpringBootTest(classes = {VscommerceApp.class, TestSecurityConfiguration.class})
public class SpecialDealsExtendResourceIT {

    private MockMvc restMockMvc;
    private final SpecialDealsExtendService specialDealsExtendService;

    public SpecialDealsExtendResourceIT(SpecialDealsExtendService specialDealsExtendService) {
        this.specialDealsExtendService = specialDealsExtendService;
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        SpecialDealsExtendResource specialDealsExtendResource = new SpecialDealsExtendResource(specialDealsExtendService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(specialDealsExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/special-deals-extend/default-action"))
            .andExpect(status().isOk());
    }
}
