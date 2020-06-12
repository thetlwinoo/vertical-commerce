package com.vertical.commerce.service.impl;

import com.vertical.commerce.domain.People;
import com.vertical.commerce.domain.StockItems;
import com.vertical.commerce.domain.WishlistLines;
import com.vertical.commerce.domain.Wishlists;
import com.vertical.commerce.repository.*;
import com.vertical.commerce.service.CommonService;
import com.vertical.commerce.service.WishlistsExtendService;
import com.vertical.commerce.service.dto.StockItemsDTO;
import com.vertical.commerce.service.dto.WishlistsDTO;
import com.vertical.commerce.service.mapper.StockItemsMapper;
import com.vertical.commerce.service.mapper.WishlistsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class WishlistsExtendServiceImpl implements WishlistsExtendService {

    private final Logger log = LoggerFactory.getLogger(WishlistsExtendServiceImpl.class);
    private final PeopleExtendRepository peopleExtendRepository;
    private final WishlistsRepository wishlistsRepository;
    private final WishlistLinesRepository wishlistLinesRepository;
    private final StockItemsRepository stockItemsRepository;
    private final StockItemsMapper stockItemsMapper;
    private final UserRepository userRepository;
    private final WishlistsMapper wishlistsMapper;
    private final CommonService commonService;

    public WishlistsExtendServiceImpl(PeopleExtendRepository peopleExtendRepository, WishlistsRepository wishlistsRepository, WishlistLinesRepository wishlistLinesRepository, StockItemsRepository stockItemsRepository, StockItemsMapper stockItemsMapper, UserRepository userRepository, WishlistsMapper wishlistsMapper, CommonService commonService) {
        this.peopleExtendRepository = peopleExtendRepository;
        this.wishlistsRepository = wishlistsRepository;
        this.wishlistLinesRepository = wishlistLinesRepository;
        this.stockItemsRepository = stockItemsRepository;
        this.stockItemsMapper = stockItemsMapper;
        this.userRepository = userRepository;
        this.wishlistsMapper = wishlistsMapper;
        this.commonService = commonService;
    }

    @Override
    public WishlistsDTO addToWishlist(Principal principal, Long id) {
        try {
            People people = commonService.getPeopleByPrincipal(principal);

            Wishlists wishlists = people.getWishlist();

            if (wishlists == null) {
                wishlists = new Wishlists();
                wishlists.setWishlistUser(people);
                wishlistsRepository.save(wishlists);
            } else if (wishlists.getWishlistLineLists() != null || !wishlists.getWishlistLineLists().isEmpty()) {
                for (WishlistLines i : wishlists.getWishlistLineLists()) {
                    if (i.getStockItem().getId().equals(id)) {
                        return wishlistsMapper.toDto(wishlists) ;
                    }
                }
            }

            StockItems stockItems = stockItemsRepository.getOne(id);
            WishlistLines wishlistLines = new WishlistLines();
            wishlistLines.setStockItem(stockItems);

            wishlistLines.setWishlist(wishlists);
            wishlists.getWishlistLineLists().add(wishlistLines);
            wishlistLinesRepository.save(wishlistLines);

            return wishlistsMapper.toDto(wishlists) ;
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    @Override
    public Boolean isInWishlist(Principal principal, Long id) {
        People people = commonService.getPeopleByPrincipal(principal);
        for (WishlistLines wishlistLines : people.getWishlist().getWishlistLineLists()) {
            if (wishlistLines.getStockItem().getId().equals(id)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public WishlistsDTO fetchWishlist(Principal principal) {
        People people = commonService.getPeopleByPrincipal(principal);
        return wishlistsMapper.toDto(people.getWishlist());
    }

    @Override
    public List<StockItemsDTO> fetchWishlistStockItems(Principal principal) {
        try {
            People people = commonService.getPeopleByPrincipal(principal);

            List<StockItems> stockItemsList = new ArrayList<>();
            Set<WishlistLines> wishlistLinesList;

            if (people.getWishlist() != null) {
                wishlistLinesList = people.getWishlist().getWishlistLineLists();
                for (WishlistLines wishlistLines : wishlistLinesList) {
                    stockItemsList.add(wishlistLines.getStockItem());
                }
            }

            return stockItemsList.stream()
                .map(stockItemsMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    @Override
    public WishlistsDTO removeFromWishlist(Principal principal, Long id) {
        People people = commonService.getPeopleByPrincipal(principal);
        Wishlists wishlists = people.getWishlist();
        if (wishlists == null) {
            throw new IllegalArgumentException("Not found");
        }
        Set<WishlistLines> wishlistLineList = wishlists.getWishlistLineLists();
        WishlistLines wishlistLineToDelete = null;
        for (WishlistLines i : wishlistLineList) {
            if (i.getStockItem().getId().equals(id)) {
                wishlistLineToDelete = i;
                break;
            }
        }
        if (wishlistLineToDelete == null) {
            throw new IllegalArgumentException("Delete Item Not Found");
        }

        wishlistLineList.remove(wishlistLineToDelete);

        if (wishlists.getWishlistLineLists() == null || wishlists.getWishlistLineLists().size() == 0) {
            people.setWishlist(null);
            peopleExtendRepository.save(people);
            return null;
        }

        wishlists.setWishlistLineLists(wishlistLineList);
        wishlistLinesRepository.delete(wishlistLineToDelete);

        return wishlistsMapper.toDto(wishlists);
    }

    @Override
    public void emptyWishlist(Principal principal) {
        People people = commonService.getPeopleByPrincipal(principal);
        people.setWishlist(null);
        peopleExtendRepository.save(people);
    }
}
