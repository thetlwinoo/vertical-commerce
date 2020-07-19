package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.StockItemsCriteria;
import com.vertical.commerce.service.dto.StockItemsDTO;
import net.minidev.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;

public interface StockItemsExtendService {
    StockItemsDTO importStockItems(StockItemsDTO stockItemsDTO, Principal principal);

    Page<StockItemsDTO> getAllStockItems(Long supplierId,StockItemsCriteria criteria, Pageable pageable, Principal principal);

    JSONObject getStatistics(Long supplierId);
}
