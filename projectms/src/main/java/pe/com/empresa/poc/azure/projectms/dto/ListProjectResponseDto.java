package pe.com.empresa.poc.azure.projectms.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ListProjectResponseDto {
    private List<ItemListProjectResponseDto> projects;
}
