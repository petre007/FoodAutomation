package com.example.flux.connector.repository;

import com.example.flux.connector.model.ComponentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComponentsRepository extends JpaRepository<ComponentsEntity, Integer> {

    ComponentsEntity getComponentsEntityByName(String name);

}
