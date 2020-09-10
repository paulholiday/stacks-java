package com.xxAMIDOxx.menu.repository;

import com.xxAMIDOxx.menu.domain.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MenuFacade {

    Page<Menu> findAllByRestaurantId(String restaurantId, Pageable pageable);

    Menu save(Menu menu);

    Page<Menu> findAllByRestaurantIdAndName(String restaurantId, String name, Pageable pageable);

    Page<Menu> findAllByNameContaining(String searchTerm, Pageable pageable);

    Page<Menu> findAllByRestaurantIdAndNameContaining(
            String restaurantId, String searchTerm, Pageable pageable);

}
