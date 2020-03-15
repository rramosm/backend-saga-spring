package pe.com.empresa.poc.azure.projectms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetResourceResponseDto {
    private Long projectId;
    private Long userId;
    private Boolean active;
}
