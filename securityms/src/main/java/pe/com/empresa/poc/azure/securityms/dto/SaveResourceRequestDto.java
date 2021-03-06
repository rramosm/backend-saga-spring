package pe.com.empresa.poc.azure.securityms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.empresa.poc.azure.securityms.helper.SerializableCoreVersion;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveResourceRequestDto implements Serializable {
    private static final long serialVersionUID = SerializableCoreVersion.SERIAL_VERSION_UID;

    private Long projectId;
    private Long userId;
    private String completeName;

}
