package pe.com.empresa.poc.azure.core.dto;

import lombok.Data;

@Data
public class SaveResourceRequestDto {
    private Long projectId;
    private Long userId;
    private String completeName;
}
