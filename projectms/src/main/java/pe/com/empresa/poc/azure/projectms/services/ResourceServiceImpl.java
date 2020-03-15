package pe.com.empresa.poc.azure.projectms.services;

import org.apache.servicecomb.pack.omega.transaction.annotations.Compensable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.empresa.poc.azure.projectms.domain.ResourceEntity;
import pe.com.empresa.poc.azure.projectms.dto.*;
import pe.com.empresa.poc.azure.projectms.repo.ResourceRepository;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResourceServiceImpl implements ResourceService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    ResourceRepository resourceRepository;

    public SaveResourceResponseDto saveResource(SaveResourceRequestDto saveResourceRequestDto) {
        List<ResourceEntity> resourceEntityList = resourceRepository.findByUserId(saveResourceRequestDto.getUserId());
        ResourceEntity resourceEntity = null;
        if(resourceEntityList !=null && resourceEntityList.size()>0){
            resourceEntity = resourceEntityList.get(0);
            resourceEntity.setProjectId(saveResourceRequestDto.getProjectId());
            resourceEntity.setCompleteName(saveResourceRequestDto.getCompleteName());
            resourceEntity.setActive(true);
        } else{
            resourceEntity = ResourceEntity.builder().projectId(saveResourceRequestDto.getProjectId())
                    .userId(saveResourceRequestDto.getUserId()).active(true).completeName(saveResourceRequestDto.getCompleteName()).build();

        }
        resourceRepository.save(resourceEntity);
        return SaveResourceResponseDto.builder().id(resourceEntity.getId()).build();
    }

    public GetResourceResponseDto findByUserId(Long userId) {
        List<ResourceEntity> resourceEntityList = resourceRepository.findByUserId(userId);
        GetResourceResponseDto getResourceResponseDto = null;
        if (resourceEntityList.size() > 0) {
            getResourceResponseDto = GetResourceResponseDto.builder().projectId(resourceEntityList.get(0).getProjectId()).build();
        }
        return getResourceResponseDto;
    }

    public List<GetResourceResponseDto> findAll() {
        List<ResourceEntity> resourceEntityList = resourceRepository.findAll();
        List<GetResourceResponseDto> getResourceResponseDtoList =
                resourceEntityList.stream()
                        .map(x -> GetResourceResponseDto.builder().projectId(x.getProjectId())
                                .userId(x.getUserId()).active(x.getActive()).build())
                        .collect(Collectors.toList());
        return getResourceResponseDtoList;
    }

    @Compensable(compensationMethod = "cancelSaveResource")
    public void addCompensationSaveResource(SaveResourceResponseDto saveResourceResponseDto) {
        LOG.info("add compensation in UserServiceImpl.addCompensationCreateUser ... resourceResponseDto.id = " + saveResourceResponseDto.getId());
    }

    @Transactional
    public void cancelSaveResource(SaveResourceResponseDto saveResourceResponseDto) {
        LOG.info("execute compensation in UserServiceImpl.addCompensationCreateUser ... resourceResponseDto.id = " + saveResourceResponseDto.getId());
        ResourceEntity resourceEntity = resourceRepository.findById(saveResourceResponseDto.getId()).orElse(null);
        resourceEntity.setActive(false);
        resourceRepository.save(resourceEntity);
    }

    @Transactional
    public DeleteResourceResponseDto deleteResource(DeleteResourceRequestDto deleteResourceRequestDto) {
        ResourceEntity resourceEntity = resourceRepository.findById(deleteResourceRequestDto.getUserId()).orElse(null);
        resourceEntity.setActive(false);
        resourceRepository.save(resourceEntity);
        return DeleteResourceResponseDto.builder().userId(resourceEntity.getId()).active(resourceEntity.getActive()).build();
    }

    @Compensable(compensationMethod = "cancelDeleteResource")
    public void addCompensationDeleteResource(DeleteResourceResponseDto deleteResourceResponseDto) {
        LOG.info("add compensation in ResourceServiceImpl.addCompensationDeleteResource ... deleteUserResponseDto.id = " + deleteResourceResponseDto.getUserId());
    }

    @Transactional
    public void cancelDeleteResource(DeleteResourceResponseDto deleteUserResponseDto) {
        LOG.info("execute compensation in UserServiceImpl.cancelDeleteResource ... deleteUserResponseDto.id = " + deleteUserResponseDto.getUserId());
        ResourceEntity resourceEntity = resourceRepository.findById(deleteUserResponseDto.getUserId()).orElse(null);
        resourceEntity.setActive(true);
        resourceRepository.save(resourceEntity);
    }

}
