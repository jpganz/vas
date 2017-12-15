package com.sigetel.web.repository;


import com.sigetel.web.domain.MenuUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


/**
 * Spring Data JPA repository for the MenuUrl entity.
 */
public interface MenuUrlRepository extends JpaRepository<MenuUrl, Long> {

    List<MenuUrl> findByAuthority(String role);


}
