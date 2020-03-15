package pe.com.empresa.poc.azure.projectms.domain;

import lombok.*;
import pe.com.empresa.poc.azure.core.domain.AuditEntity;
import pe.com.empresa.poc.azure.projectms.helper.SerializableCoreVersion;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "T_RESOURCE")
public class ResourceEntity extends AuditEntity<String> implements Serializable {

    private static final long serialVersionUID = SerializableCoreVersion.SERIAL_VERSION_UID;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="resource_id_seq")
    @SequenceGenerator(name="resource_id_seq", sequenceName="resource_id_seq", allocationSize=1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PROYECT_ID")
    private Long projectId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "COMPLETE_NAME")
    private String completeName;

    @Column(name = "ACTIVE")
    private Boolean active;

}
