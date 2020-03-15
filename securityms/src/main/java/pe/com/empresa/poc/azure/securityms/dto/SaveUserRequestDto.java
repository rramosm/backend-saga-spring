package pe.com.empresa.poc.azure.securityms.dto;

import lombok.Data;
import pe.com.empresa.poc.azure.securityms.enums.DocumentTypeEnum;

import javax.validation.constraints.NotNull;

@Data
public class SaveUserRequestDto {
    private Long userId;
    @NotNull
    private Long projectId;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String documentNumber;
    @NotNull
    private String documentTypeEnum;
    @NotNull
    private Boolean active;
}
