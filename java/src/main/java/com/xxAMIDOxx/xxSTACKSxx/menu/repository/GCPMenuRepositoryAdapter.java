package com.xxAMIDOxx.xxSTACKSxx.menu.repository;

import com.xxAMIDOxx.xxSTACKSxx.menu.domain.Menu;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class GCPMenuRepositoryAdapter implements MenuRepositoryAdapter {

  @Override
  public Page<Menu> findAllByRestaurantId(String restaurantId, Pageable pageable) {
    throw new UnsupportedOperationException("GCP Nt Implemented");
  }

  @Override
  public Menu save(Menu menu) {
    throw new UnsupportedOperationException("GCP Nt Implemented");
  }

  @Override
  public void delete(Menu menu) {
    throw new UnsupportedOperationException("GCP Nt Implemented");
  }

  @Override
  public Optional<Menu> findById(String menuId) {
    throw new UnsupportedOperationException("GCP Nt Implemented");
  }

  @Override
  public Page<Menu> findAllByRestaurantIdAndName(
      String restaurantId, String name, Pageable pageable) {
    throw new UnsupportedOperationException("GCP Nt Implemented");
  }

  @Override
  public Page<Menu> findAllByNameContaining(String searchTerm, Pageable pageable) {
    throw new UnsupportedOperationException("GCP Nt Implemented");
  }

  @Override
  public Page<Menu> findAllByRestaurantIdAndNameContaining(
      String restaurantId, String searchTerm, Pageable pageable) {
    throw new UnsupportedOperationException("GCP Nt Implemented");
  }

  @Override
  public Page<Menu> findAll(Pageable pageable) {
    throw new UnsupportedOperationException("GCP Nt Implemented");
  }

  @Override
  public void deleteAll() {
    throw new UnsupportedOperationException("GCP Nt Implemented");
  }
}
