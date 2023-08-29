package com.agentes.academy.backend.repository;

import com.agentes.academy.backend.domain.News;
import org.springframework.data.repository.CrudRepository;

public interface NewsRepository extends CrudRepository<News,Long> {
}
