package com.agentes.academy.backend.repository;

import com.agentes.academy.backend.domain.Category;
import com.agentes.academy.backend.domain.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewsRepository extends JpaRepository<News,Long> {
    @Query("FROM News WHERE category = ?1")
    List<News> findByCategory(Category category);

    @Query("FROM News WHERE category = ?1")
    Page<News> findPaginatedByCategory(Category category, Pageable pageable);
}
