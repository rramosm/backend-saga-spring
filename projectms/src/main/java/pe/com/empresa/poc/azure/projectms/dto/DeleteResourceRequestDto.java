package pe.com.empresa.poc.azure.projectms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.empresa.poc.azure.projectms.helper.SerializableCoreVersion;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteResourceRequestDto implements Serializable {
    private static final long serialVersionUID = SerializableCoreVersion.SERIAL_VERSION_UID;
    private Long userId;
}
