package pe.com.empresa.poc.azure.projectms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.empresa.poc.azure.projectms.domain.ProjectEntity;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    List<ProjectEntity> findByActiveTrue();
}
