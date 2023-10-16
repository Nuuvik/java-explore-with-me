package ru.practicum.main.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.model.Categories;

public interface CategoriesMainServiceRepository extends JpaRepository<Categories, Long> {

    Page<Categories> findAll(Pageable pageable);
}
