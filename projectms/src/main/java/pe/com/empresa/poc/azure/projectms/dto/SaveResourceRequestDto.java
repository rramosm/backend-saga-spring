package pe.com.empresa.poc.azure.projectms.dto;

import lombok.Data;

@Data
public class SaveResourceRequestDto {
    private Long projectId;
    private Long userId;
    private String completeName;
}
