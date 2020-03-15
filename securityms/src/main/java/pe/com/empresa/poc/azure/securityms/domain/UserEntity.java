package pe.com.empresa.poc.azure.securityms.domain;

import lombok.*;
import pe.com.empresa.poc.azure.core.domain.AuditEntity;
import pe.com.empresa.poc.azure.securityms.enums.DocumentTypeEnum;
import pe.com.empresa.poc.azure.securityms.helper.SerializableCoreVersion;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "T_USER")
public class UserEntity extends AuditEntity<String> implements Serializable {

    private static final long serialVersionUID = SerializableCoreVersion.SERIAL_VERSION_UID;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="user_id_seq")
    @SequenceGenerator(name="user_id_seq", sequenceName="user_id_seq", allocationSize=1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "DOCUMENT_NUMBER")
    private String documentNumber;

    @Column(name = "DOCUMENT_TYPE")
    @Enumerated(EnumType.STRING)
    private DocumentTypeEnum documentTypeEnum;

    @Column(name = "ACTIVE")
    private Boolean active;

}
