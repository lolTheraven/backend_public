package com.agentes.academy.backend.repository;

import com.agentes.academy.backend.domain.Category;
import com.agentes.academy.backend.domain.News;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NewsRepository extends CrudRepository<News,Long> {
    @Query("FROM News WHERE category = ?1")
    List<News> findByCategory(Category category);
}
