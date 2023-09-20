package com.agentes.academy.backend.repository;

import com.agentes.academy.backend.domain.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, Long> {
}
