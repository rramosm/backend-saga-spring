package pe.com.empresa.poc.azure.projectms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.empresa.poc.azure.projectms.domain.ResourceEntity;

import java.util.List;

public interface ResourceRepository extends JpaRepository<ResourceEntity, Long> {

    List<ResourceEntity> findByUserId(Long userId);

}
