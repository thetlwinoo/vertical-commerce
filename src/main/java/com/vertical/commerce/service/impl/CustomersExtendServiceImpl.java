package com.vertical.commerce.service.impl;

import com.vertical.commerce.domain.Customers;
import com.vertical.commerce.domain.DeliveryMethods;
import com.vertical.commerce.domain.People;
import com.vertical.commerce.domain.enumeration.Gender;
import com.vertical.commerce.repository.CustomersRepository;
import com.vertical.commerce.repository.PeopleRepository;
import com.vertical.commerce.service.CommonService;
import com.vertical.commerce.service.CustomersExtendService;
import com.vertical.commerce.service.dto.CustomersDTO;
import com.vertical.commerce.service.mapper.CustomersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.Instant;
import java.util.Random;

@Service
@Transactional
public class CustomersExtendServiceImpl implements CustomersExtendService {

    private final Logger log = LoggerFactory.getLogger(CustomersExtendServiceImpl.class);
    private final PeopleRepository peopleRepository;
    private final CommonService commonService;
    private final CustomersRepository customersRepository;
    private final CustomersMapper customersMapper;

    public CustomersExtendServiceImpl(PeopleRepository peopleRepository, CommonService commonService, CustomersRepository customersRepository, CustomersMapper customersMapper) {
        this.peopleRepository = peopleRepository;
        this.commonService = commonService;
        this.customersRepository = customersRepository;
        this.customersMapper = customersMapper;
    }

    public CustomersDTO createCustomerAccount(Principal principal){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        People people = commonService.getPeopleByPrincipal(principal);
        Customers customers = new Customers();

        if(people == null){
            Object nameClaim = ((Jwt) ((JwtAuthenticationToken) principal).getCredentials()).getClaims().get("name");
            Object emailClaim = ((Jwt) ((JwtAuthenticationToken) principal).getCredentials()).getClaims().get("email");
            Object preferredUsernameClaim = ((Jwt) ((JwtAuthenticationToken) principal).getCredentials()).getClaims().get("preferred_username");
            Object givenNameClaim = ((Jwt) ((JwtAuthenticationToken) principal).getCredentials()).getClaims().get("given_name");
            Object familyNameClaim = ((Jwt) ((JwtAuthenticationToken) principal).getCredentials()).getClaims().get("family_name");
            Object emailVerifiedClaim = ((Jwt) ((JwtAuthenticationToken) principal).getCredentials()).getClaims().get("email_verified");

            people = new People();
            people.setFullName(nameClaim.toString());
            people.setPreferredName(familyNameClaim.toString());
            people.setSearchName(givenNameClaim.toString());
            people.setIsPermittedToLogon(true);
            people.logonName(preferredUsernameClaim.toString());
            people.setGender(Gender.MALE);
            people.setEmailAddress(emailClaim.toString());
            people.setIsExternalLogonProvider(false);
            people.setIsSystemUser(false);
            people.setIsEmployee(false);
            people.setIsSalesPerson(false);
            people.setIsGuestUser(false);
            people.setEmailPromotion(false);
            people.setUserId(principal.getName());
            people.setValidFrom(Instant.now());
            people.setValidTo(Instant.now());
            peopleRepository.save(people);

            customers = new Customers();
            String prefixNumber = preferredUsernameClaim.toString().replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
            prefixNumber = prefixNumber.length() > 4 ? prefixNumber.substring(0,4): prefixNumber;
            Random random= new Random();
            int pnum = random.nextInt(10000);
            prefixNumber = prefixNumber + pnum;

            customers.setAccountNumber(prefixNumber);
            customers.setAccountOpenedDate(Instant.now());
            DeliveryMethods deliveryMethods = commonService.getDeliveryMethodsEntity(null,"Standard");
            customers.setDeliveryMethod(deliveryMethods);
            customers.setIsOnCreditHold(false);
            customers.setIsStatementSent(false);
            customers.setName(people.getFullName());
            customers.setPeople(people);
            customers.setLastEditedBy("SYSTEM");
            customers.setValidFrom(Instant.now());
            customers.setValidTo(Instant.now());
            customers.setStandardDiscountPercentage(BigDecimal.ZERO);
            customers.setPaymentDays(7);
            customers = customersRepository.save(customers);

        }else{
            customers = commonService.getCustomerByPrincipal(principal);
        }

        return customersMapper.toDto(customers);
    }
}
