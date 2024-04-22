package com.software.a3.SoftwareToolsApi.Services;


import com.software.a3.SoftwareToolsApi.Exceptions.EntityNotFoundException;
import com.software.a3.SoftwareToolsApi.Models.Enum.ESoftwareTypeTool;
import com.software.a3.SoftwareToolsApi.Models.SoftwareToolModel;
import com.software.a3.SoftwareToolsApi.Repositories.SoftwareToolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SoftwareToolService {

    @Autowired
    private SoftwareToolRepository softwareToolRepository;

    @Transactional(readOnly = true)
    public List<SoftwareToolModel> findAll() {
        return softwareToolRepository.findAll();
    }

    @Transactional
    public SoftwareToolModel findById(Long id) throws EntityNotFoundException {
        return softwareToolRepository.findById(id).orElseThrow(() -> new EntityNotFoundException
                (String.format("Error:Ferramenta não encontrada pelo Id %s", id)));
    }

    @Transactional(readOnly = true)
    public Optional<SoftwareToolModel> findByTypeTool(ESoftwareTypeTool typeTool) throws EntityNotFoundException {
        Optional<SoftwareToolModel> optionalTool = softwareToolRepository.findByTypeTool(ESoftwareTypeTool.valueOf(typeTool.name()));
        if (optionalTool.isEmpty()) {
            throw new EntityNotFoundException("ERROR:Ferramenta não encontrada para o tipo: " + typeTool);
        }
        return optionalTool;

    }


    @Transactional(readOnly = true)
    public SoftwareToolModel findByName(String name) throws EntityNotFoundException {
        var softwareName = softwareToolRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException
                (String.format("ERROR:Ferramenta não encontrada pelo nome %s", name)));
        return softwareName;
    }

    @Transactional
    public SoftwareToolModel saveTool(SoftwareToolModel softwareTool) {
        return softwareToolRepository.save(softwareTool);
    }

    @Transactional
    public void deleteToolById(Long toolId) throws EntityNotFoundException {

        Optional<SoftwareToolModel> optionalTool = softwareToolRepository.findById(toolId);
        if (optionalTool.isPresent()) {

            SoftwareToolModel softwareTool = optionalTool.get();
            softwareToolRepository.delete(softwareTool);
        } else {

            throw new EntityNotFoundException("ERROR:Ferramenta não encontrada para o ID: " + toolId);
        }
    }
}

