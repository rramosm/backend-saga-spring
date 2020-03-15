package pe.com.empresa.poc.azure.securityms.services;

import org.apache.servicecomb.pack.omega.transaction.annotations.Compensable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.empresa.poc.azure.securityms.domain.UserEntity;
import pe.com.empresa.poc.azure.securityms.dto.*;
import pe.com.empresa.poc.azure.securityms.enums.DocumentTypeEnum;
import pe.com.empresa.poc.azure.securityms.exception.BusinessCoreException;
import pe.com.empresa.poc.azure.securityms.helper.ErrorConstant;
import pe.com.empresa.poc.azure.securityms.repo.UserRepository;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public SaveUserResponseDto saveUser(SaveUserRequestDto createUserRequestDto) {
        List<UserEntity> userEntityList = userRepository
                .findByDocumentTypeEnumAndDocumentNumber(DocumentTypeEnum.valueOf(createUserRequestDto.getDocumentTypeEnum()),
                createUserRequestDto.getDocumentNumber());
        if(userEntityList != null && userEntityList.size() >0 &&  createUserRequestDto.getUserId() != userEntityList.get(0).getId() ) {
            throw new BusinessCoreException(ErrorConstant.DEFAULT_ERROR, "Existe otro usuario con el mismo documento",
                    "Data existente");
        }

        UserEntity userEntity = null;

        if(createUserRequestDto.getUserId() != null && createUserRequestDto.getUserId() > 0) {
            userEntity = userRepository.getOne(createUserRequestDto.getUserId());
        } else {
            userEntity = new UserEntity();
        }
        userEntity.setActive(createUserRequestDto.getActive());
        userEntity.setDocumentNumber(createUserRequestDto.getDocumentNumber());
        userEntity.setDocumentTypeEnum(DocumentTypeEnum.valueOf(createUserRequestDto.getDocumentTypeEnum()));
        userEntity.setName(createUserRequestDto.getName());
        userEntity.setSurname(createUserRequestDto.getSurname());
        userRepository.save(userEntity);

        return SaveUserResponseDto.builder().userId(userEntity.getId()).name(userEntity.getName())
                .surname(userEntity.getSurname()).build();
    }

    @Compensable(compensationMethod = "cancelCreateUser")
    public void addCompensationCreateUser(SaveUserResponseDto createUserResponseDto) {
        LOG.info("add compensation in UserServiceImpl.addCompensationCreateUser ... createUserResponseDto.id = " + createUserResponseDto.getUserId());
    }

    @Transactional
    public void cancelCreateUser(SaveUserResponseDto createUserResponseDto) {
        LOG.info("execute compensation in UserServiceImpl.addCompensationCreateUser ... createUserResponseDto.id = " + createUserResponseDto.getUserId());
        UserEntity userEntity = userRepository.findById(createUserResponseDto.getUserId()).orElse(null);
        userEntity.setActive(false);
    }

    @Transactional
    public DeleteUserResponseDto deleteUser(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        userEntity.setActive(false);
        userRepository.save(userEntity);
        return DeleteUserResponseDto.builder().userId(userEntity.getId()).active(userEntity.getActive()).build();
    }

    @Compensable(compensationMethod = "cancelDeleteUser")
    public void addCompensationDeleteUser(Long id) {
        LOG.info("add compensation in UserServiceImpl.addCompensationDeleteUser ... deleteUserResponseDto.id = " + id);
    }

    @Transactional
    public void cancelDeleteUser(Long id) {
        LOG.info("execute compensation in UserServiceImpl.cancelDeleteUser ... deleteUserResponseDto.id = " + id);
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        userEntity.setActive(true);
    }

    public GetUserResponseDto findById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        return GetUserResponseDto.builder().userId(userEntity.getId()).documentTypeEnum(userEntity.getDocumentTypeEnum().toString())
                .documentNumber(userEntity.getDocumentNumber()).name(userEntity.getName()).surname(userEntity.getSurname())
                .active(userEntity.getActive()).build();
    }

    public List<GetUserResponseDto> findAll(List<GetResourceResponseDto> getResourceResponseDtoList) {
        List<UserEntity> userEntityList = userRepository.findAll();
        List<GetUserResponseDto> getUserResponseDtos =
                userEntityList.stream()
                        .map(x -> GetUserResponseDto.builder().userId(x.getId()).documentTypeEnum(x.getDocumentTypeEnum().toString())
                                .documentNumber(x.getDocumentNumber()).name(x.getName()).surname(x.getSurname())
                                .projectId(getResourceResponseDtoList.stream()
                                        .filter(y -> x.getId().equals(y.getUserId())).findFirst()
                                        .orElse(new GetResourceResponseDto()).getProjectId())
                                .active(x.getActive()).build())
                        .collect(Collectors.toList());
        //TODO agregarr
        return getUserResponseDtos;
    }

}
