package pe.com.empresa.poc.azure.securityms.services;

import pe.com.empresa.poc.azure.securityms.dto.*;

import java.util.List;

public interface UserService {
    SaveUserResponseDto saveUser(SaveUserRequestDto createUserRequestDto);
    void addCompensationCreateUser(SaveUserResponseDto createUserResponseDto);
    DeleteUserResponseDto deleteUser(Long id);
    void addCompensationDeleteUser(Long id);
    GetUserResponseDto findById(Long id);
    List<GetUserResponseDto> findAll(List<GetResourceResponseDto> getResourceResponseDtoList);
}
