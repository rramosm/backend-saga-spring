package pe.com.empresa.poc.azure.projectms.services;

import pe.com.empresa.poc.azure.projectms.dto.*;

import java.util.List;

public interface ResourceService {
    SaveResourceResponseDto saveResource(SaveResourceRequestDto saveResourceRequestDto);
    void addCompensationSaveResource(SaveResourceResponseDto saveResourceResponseDto);
    DeleteResourceResponseDto deleteResource(DeleteResourceRequestDto deleteResourceRequestDto);
    void addCompensationDeleteResource(DeleteResourceResponseDto deleteResourceResponseDto);
    GetResourceResponseDto findByUserId(Long userId);
    List<GetResourceResponseDto> findAll();
}
