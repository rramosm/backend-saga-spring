package pe.com.empresa.poc.azure.projectms.services;

import pe.com.empresa.poc.azure.projectms.dto.ItemListProjectResponseDto;

import java.util.List;

public interface ProjectService {
    List<ItemListProjectResponseDto> listProjectActive();
}
