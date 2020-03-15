package pe.com.empresa.poc.azure.projectms.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.empresa.poc.azure.projectms.domain.ProjectEntity;
import pe.com.empresa.poc.azure.projectms.dto.ItemListProjectResponseDto;
import pe.com.empresa.poc.azure.projectms.repo.ProjectRepository;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    ProjectRepository projectRepository;

    @Autowired
    public void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<ItemListProjectResponseDto> listProjectActive() {
        List<ProjectEntity> projectEntityList = projectRepository.findByActiveTrue();
        List<ItemListProjectResponseDto> names =
                projectEntityList.stream()
                        .map(x -> ItemListProjectResponseDto.builder().id(x.getId()).name(x.getName()).build())
                        .collect(Collectors.toList());
        return names;
    }
}
