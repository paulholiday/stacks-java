package com.xxAMIDOxx.menu.repository;

import com.xxAMIDOxx.menu.domain.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class AzureMenuFacade implements MenuFacade {

    protected AzureMenuRepository azureMenuRepository;

    public Page<Menu> findAllByRestaurantId(String restaurantId, Pageable pageable){
        return azureMenuRepository.findAllByRestaurantId(restaurantId,pageable);
    }

    @Override
    public Menu save(Menu menu) {
      return azureMenuRepository.save(menu);
    }

    @Override
    public Page<Menu> findAllByRestaurantIdAndName(String restaurantId, String name, Pageable pageable) {
       return azureMenuRepository.findAllByRestaurantIdAndName(restaurantId,name,pageable);
    }

    @Override
    public Page<Menu> findAllByNameContaining(String searchTerm, Pageable pageable) {
        return azureMenuRepository.findAllByNameContaining(searchTerm,pageable);
    }

    @Override
    public Page<Menu> findAllByRestaurantIdAndNameContaining(String restaurantId, String searchTerm, Pageable pageable) {
        return azureMenuRepository.findAllByRestaurantIdAndNameContaining(restaurantId,searchTerm,pageable);
    }

}
