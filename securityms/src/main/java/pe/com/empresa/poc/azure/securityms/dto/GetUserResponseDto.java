package pe.com.empresa.poc.azure.securityms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserResponseDto {
    private Long userId;
    private Long projectId;
    private String name;
    private String surname;
    private String documentNumber;
    private String documentTypeEnum;
    private Boolean active;
}
