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
@Table(name = "T_PROJECT")
public class ProjectEntity extends AuditEntity<String> implements Serializable {

    private static final long serialVersionUID = SerializableCoreVersion.SERIAL_VERSION_UID;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="project_id_seq")
    @SequenceGenerator(name="project_id_seq", sequenceName="project_id_seq", allocationSize=1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ACTIVE")
    private Boolean active;

}
