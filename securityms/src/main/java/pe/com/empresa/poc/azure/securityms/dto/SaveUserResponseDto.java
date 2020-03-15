package pe.com.empresa.poc.azure.securityms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveUserResponseDto {
    private Long userId;
    private String name;
    private String surname;
}
