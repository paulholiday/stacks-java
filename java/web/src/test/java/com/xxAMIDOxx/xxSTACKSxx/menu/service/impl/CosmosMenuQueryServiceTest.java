package com.xxAMIDOxx.xxSTACKSxx.menu.service.impl;

import static com.xxAMIDOxx.xxSTACKSxx.menu.domain.MenuHelper.createMenus;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.xxAMIDOxx.menu.domain.Menu;
import com.xxAMIDOxx.menu.repository.AzureMenuRepository;
import com.xxAMIDOxx.menu.service.MenuQueryService;
import java.util.List;

import com.xxAMIDOxx.menu.service.impl.CosmosMenuQueryService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@Tag("Unit")
public class CosmosMenuQueryServiceTest {

  @Test
  void findAll() {

    AzureMenuRepository repository = mock(AzureMenuRepository.class);
    MenuQueryService menuQueryServiceImpl = new CosmosMenuQueryService(repository);

    Pageable pageable = mock(Pageable.class);

    List<Menu> results = createMenus(2);
    Page<Menu> page1 = new PageImpl<>(results, pageable, 2);
    Page<Menu> page2 = new PageImpl<>(results, pageable, 2);

    // Given
    given(repository.findAll(any(Pageable.class))).willReturn(page1);
    given(repository.findAll(eq(pageable))).willReturn(page2);

    // When
    List<Menu> actualResults = menuQueryServiceImpl.findAll(2, 5);

    // Then
    then(actualResults).isEqualTo(results);
  }
}
