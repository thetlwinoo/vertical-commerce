package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.BusinessEntityAddress;
import com.vertical.commerce.domain.Addresses;
import com.vertical.commerce.domain.People;
import com.vertical.commerce.domain.AddressTypes;
import com.vertical.commerce.repository.BusinessEntityAddressRepository;
import com.vertical.commerce.service.BusinessEntityAddressService;
import com.vertical.commerce.service.dto.BusinessEntityAddressDTO;
import com.vertical.commerce.service.mapper.BusinessEntityAddressMapper;
import com.vertical.commerce.service.dto.BusinessEntityAddressCriteria;
import com.vertical.commerce.service.BusinessEntityAddressQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BusinessEntityAddressResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class BusinessEntityAddressResourceIT {

    @Autowired
    private BusinessEntityAddressRepository businessEntityAddressRepository;

    @Autowired
    private BusinessEntityAddressMapper businessEntityAddressMapper;

    @Autowired
    private BusinessEntityAddressService businessEntityAddressService;

    @Autowired
    private BusinessEntityAddressQueryService businessEntityAddressQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBusinessEntityAddressMockMvc;

    private BusinessEntityAddress businessEntityAddress;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessEntityAddress createEntity(EntityManager em) {
        BusinessEntityAddress businessEntityAddress = new BusinessEntityAddress();
        return businessEntityAddress;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessEntityAddress createUpdatedEntity(EntityManager em) {
        BusinessEntityAddress businessEntityAddress = new BusinessEntityAddress();
        return businessEntityAddress;
    }

    @BeforeEach
    public void initTest() {
        businessEntityAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createBusinessEntityAddress() throws Exception {
        int databaseSizeBeforeCreate = businessEntityAddressRepository.findAll().size();
        // Create the BusinessEntityAddress
        BusinessEntityAddressDTO businessEntityAddressDTO = businessEntityAddressMapper.toDto(businessEntityAddress);
        restBusinessEntityAddressMockMvc.perform(post("/api/business-entity-addresses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessEntityAddressDTO)))
            .andExpect(status().isCreated());

        // Validate the BusinessEntityAddress in the database
        List<BusinessEntityAddress> businessEntityAddressList = businessEntityAddressRepository.findAll();
        assertThat(businessEntityAddressList).hasSize(databaseSizeBeforeCreate + 1);
        BusinessEntityAddress testBusinessEntityAddress = businessEntityAddressList.get(businessEntityAddressList.size() - 1);
    }

    @Test
    @Transactional
    public void createBusinessEntityAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = businessEntityAddressRepository.findAll().size();

        // Create the BusinessEntityAddress with an existing ID
        businessEntityAddress.setId(1L);
        BusinessEntityAddressDTO businessEntityAddressDTO = businessEntityAddressMapper.toDto(businessEntityAddress);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessEntityAddressMockMvc.perform(post("/api/business-entity-addresses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessEntityAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessEntityAddress in the database
        List<BusinessEntityAddress> businessEntityAddressList = businessEntityAddressRepository.findAll();
        assertThat(businessEntityAddressList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBusinessEntityAddresses() throws Exception {
        // Initialize the database
        businessEntityAddressRepository.saveAndFlush(businessEntityAddress);

        // Get all the businessEntityAddressList
        restBusinessEntityAddressMockMvc.perform(get("/api/business-entity-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessEntityAddress.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getBusinessEntityAddress() throws Exception {
        // Initialize the database
        businessEntityAddressRepository.saveAndFlush(businessEntityAddress);

        // Get the businessEntityAddress
        restBusinessEntityAddressMockMvc.perform(get("/api/business-entity-addresses/{id}", businessEntityAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(businessEntityAddress.getId().intValue()));
    }


    @Test
    @Transactional
    public void getBusinessEntityAddressesByIdFiltering() throws Exception {
        // Initialize the database
        businessEntityAddressRepository.saveAndFlush(businessEntityAddress);

        Long id = businessEntityAddress.getId();

        defaultBusinessEntityAddressShouldBeFound("id.equals=" + id);
        defaultBusinessEntityAddressShouldNotBeFound("id.notEquals=" + id);

        defaultBusinessEntityAddressShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBusinessEntityAddressShouldNotBeFound("id.greaterThan=" + id);

        defaultBusinessEntityAddressShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBusinessEntityAddressShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllBusinessEntityAddressesByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        businessEntityAddressRepository.saveAndFlush(businessEntityAddress);
        Addresses address = AddressesResourceIT.createEntity(em);
        em.persist(address);
        em.flush();
        businessEntityAddress.setAddress(address);
        businessEntityAddressRepository.saveAndFlush(businessEntityAddress);
        Long addressId = address.getId();

        // Get all the businessEntityAddressList where address equals to addressId
        defaultBusinessEntityAddressShouldBeFound("addressId.equals=" + addressId);

        // Get all the businessEntityAddressList where address equals to addressId + 1
        defaultBusinessEntityAddressShouldNotBeFound("addressId.equals=" + (addressId + 1));
    }


    @Test
    @Transactional
    public void getAllBusinessEntityAddressesByPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        businessEntityAddressRepository.saveAndFlush(businessEntityAddress);
        People person = PeopleResourceIT.createEntity(em);
        em.persist(person);
        em.flush();
        businessEntityAddress.setPerson(person);
        businessEntityAddressRepository.saveAndFlush(businessEntityAddress);
        Long personId = person.getId();

        // Get all the businessEntityAddressList where person equals to personId
        defaultBusinessEntityAddressShouldBeFound("personId.equals=" + personId);

        // Get all the businessEntityAddressList where person equals to personId + 1
        defaultBusinessEntityAddressShouldNotBeFound("personId.equals=" + (personId + 1));
    }


    @Test
    @Transactional
    public void getAllBusinessEntityAddressesByAddressTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        businessEntityAddressRepository.saveAndFlush(businessEntityAddress);
        AddressTypes addressType = AddressTypesResourceIT.createEntity(em);
        em.persist(addressType);
        em.flush();
        businessEntityAddress.setAddressType(addressType);
        businessEntityAddressRepository.saveAndFlush(businessEntityAddress);
        Long addressTypeId = addressType.getId();

        // Get all the businessEntityAddressList where addressType equals to addressTypeId
        defaultBusinessEntityAddressShouldBeFound("addressTypeId.equals=" + addressTypeId);

        // Get all the businessEntityAddressList where addressType equals to addressTypeId + 1
        defaultBusinessEntityAddressShouldNotBeFound("addressTypeId.equals=" + (addressTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBusinessEntityAddressShouldBeFound(String filter) throws Exception {
        restBusinessEntityAddressMockMvc.perform(get("/api/business-entity-addresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessEntityAddress.getId().intValue())));

        // Check, that the count call also returns 1
        restBusinessEntityAddressMockMvc.perform(get("/api/business-entity-addresses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBusinessEntityAddressShouldNotBeFound(String filter) throws Exception {
        restBusinessEntityAddressMockMvc.perform(get("/api/business-entity-addresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBusinessEntityAddressMockMvc.perform(get("/api/business-entity-addresses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingBusinessEntityAddress() throws Exception {
        // Get the businessEntityAddress
        restBusinessEntityAddressMockMvc.perform(get("/api/business-entity-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusinessEntityAddress() throws Exception {
        // Initialize the database
        businessEntityAddressRepository.saveAndFlush(businessEntityAddress);

        int databaseSizeBeforeUpdate = businessEntityAddressRepository.findAll().size();

        // Update the businessEntityAddress
        BusinessEntityAddress updatedBusinessEntityAddress = businessEntityAddressRepository.findById(businessEntityAddress.getId()).get();
        // Disconnect from session so that the updates on updatedBusinessEntityAddress are not directly saved in db
        em.detach(updatedBusinessEntityAddress);
        BusinessEntityAddressDTO businessEntityAddressDTO = businessEntityAddressMapper.toDto(updatedBusinessEntityAddress);

        restBusinessEntityAddressMockMvc.perform(put("/api/business-entity-addresses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessEntityAddressDTO)))
            .andExpect(status().isOk());

        // Validate the BusinessEntityAddress in the database
        List<BusinessEntityAddress> businessEntityAddressList = businessEntityAddressRepository.findAll();
        assertThat(businessEntityAddressList).hasSize(databaseSizeBeforeUpdate);
        BusinessEntityAddress testBusinessEntityAddress = businessEntityAddressList.get(businessEntityAddressList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingBusinessEntityAddress() throws Exception {
        int databaseSizeBeforeUpdate = businessEntityAddressRepository.findAll().size();

        // Create the BusinessEntityAddress
        BusinessEntityAddressDTO businessEntityAddressDTO = businessEntityAddressMapper.toDto(businessEntityAddress);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessEntityAddressMockMvc.perform(put("/api/business-entity-addresses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessEntityAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessEntityAddress in the database
        List<BusinessEntityAddress> businessEntityAddressList = businessEntityAddressRepository.findAll();
        assertThat(businessEntityAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBusinessEntityAddress() throws Exception {
        // Initialize the database
        businessEntityAddressRepository.saveAndFlush(businessEntityAddress);

        int databaseSizeBeforeDelete = businessEntityAddressRepository.findAll().size();

        // Delete the businessEntityAddress
        restBusinessEntityAddressMockMvc.perform(delete("/api/business-entity-addresses/{id}", businessEntityAddress.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BusinessEntityAddress> businessEntityAddressList = businessEntityAddressRepository.findAll();
        assertThat(businessEntityAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
