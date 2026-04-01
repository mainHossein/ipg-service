package com.example.ipgserviceproject.repository;

import com.example.ipgserviceproject.model.output.ResultObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ResultObjectRepository extends JpaRepository<ResultObject, UUID> {
}
