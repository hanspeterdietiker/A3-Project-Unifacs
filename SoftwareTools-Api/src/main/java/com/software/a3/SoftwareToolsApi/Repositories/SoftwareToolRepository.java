package com.software.a3.SoftwareToolsApi.Repositories;

import com.software.a3.SoftwareToolsApi.Models.Enum.ESoftwareTypeTool;
import com.software.a3.SoftwareToolsApi.Models.SoftwareToolModel;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface SoftwareToolRepository extends JpaRepository<SoftwareToolModel, Long> {
    Optional<SoftwareToolModel> findByTypeTool(ESoftwareTypeTool typeTool);

    Optional<SoftwareToolModel> findByName(String name);
}
