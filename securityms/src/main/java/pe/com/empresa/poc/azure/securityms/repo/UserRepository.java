package pe.com.empresa.poc.azure.securityms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.empresa.poc.azure.securityms.domain.UserEntity;
import pe.com.empresa.poc.azure.securityms.enums.DocumentTypeEnum;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByDocumentTypeEnumAndDocumentNumber(DocumentTypeEnum documentTypeEnum, String documentNumber);
}
