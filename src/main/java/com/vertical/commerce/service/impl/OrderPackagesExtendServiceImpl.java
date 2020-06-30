package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.OrderPackagesExtendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderPackagesExtendServiceImpl implements OrderPackagesExtendService {

    private final Logger log = LoggerFactory.getLogger(OrderPackagesExtendServiceImpl.class);

}
