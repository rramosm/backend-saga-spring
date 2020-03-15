package pe.com.empresa.poc.azure.core.dto;

import lombok.Data;
import pe.com.empresa.poc.azure.core.enums.DocumentTypeEnum;

import javax.validation.constraints.NotNull;

@Data
public class CreateUserRequestDto {
    @NotNull
    private Long idProject;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String documentNumber;
    @NotNull
    private DocumentTypeEnum documentType;
}
