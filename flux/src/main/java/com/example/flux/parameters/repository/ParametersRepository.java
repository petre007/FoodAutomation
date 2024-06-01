package com.example.flux.parameters.repository;


import com.example.flux.parameters.model.ParametersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametersRepository extends JpaRepository<ParametersEntity, Integer> {

    ParametersEntity findParametersEntityByName(String name);

}
