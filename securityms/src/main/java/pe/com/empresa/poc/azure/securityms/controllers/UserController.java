/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pe.com.empresa.poc.azure.securityms.controllers;

import org.apache.servicecomb.pack.omega.context.annotations.SagaStart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pe.com.empresa.poc.azure.core.controllers.BaseController;
import pe.com.empresa.poc.azure.securityms.dto.*;
import pe.com.empresa.poc.azure.securityms.exception.BusinessCoreException;
import pe.com.empresa.poc.azure.securityms.helper.ErrorConstant;
import pe.com.empresa.poc.azure.securityms.services.UserService;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.OPTIONS})
@RestController
@RequestMapping("user")
public class UserController extends BaseController {

  private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @Value("${projectms.service.address:http://localhost:8080}")
  private String projectUrl;

  @Value("${api.authentication.basic.username}")
  private String authUsername;

  @Value("${api.authentication.basic.password}")
  private String authPassword;

  @Autowired
  private UserService userService;

  @Autowired
  private RestTemplate template;

  @SagaStart
  @PostMapping
  public SaveUserResponseDto create(@Valid final @RequestBody SaveUserRequestDto request, BindingResult bindingResult) throws Throwable {
    getBindingResultError(bindingResult);
    return save(request, bindingResult);
  }

  @SagaStart
  @PutMapping
  public SaveUserResponseDto update(@Valid final @RequestBody SaveUserRequestDto request, BindingResult bindingResult) throws Throwable {
    if (request.getUserId() == null) {
      throw new BusinessCoreException(ErrorConstant.REQUEST_INVALID, "El Id de user es obligatorio",
              "Faltan Datos");
    }
    return save(request, bindingResult);
  }

  @SagaStart
  @DeleteMapping("/{id}")
  public DeleteUserResponseDto delete(@PathVariable("id") Long id) throws Throwable {
    DeleteUserResponseDto deleteUserResponseDto = userService.deleteUser(id);

    userService.addCompensationDeleteUser(id);

    MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    String originalInput = authUsername+":"+authPassword;
    String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
    headers.add("Authorization", "Basic " + encodedString);

    HttpEntity<?> requestHttpEntity = new HttpEntity<Object>(headers);
    template.exchange( projectUrl + "/resource/{userId}", HttpMethod.DELETE, requestHttpEntity, String.class, id);

    //para simular error
    if (id == 1){
      throw new RuntimeException("Error de prueba");
    }

    return deleteUserResponseDto;
  }

  @GetMapping("/{id}")
  public GetUserResponseDto getUser(@PathVariable("id") Long id) throws Throwable {
    LOG.info("******************getUser(@PathVariable(\"id\") Long id)*********************");
    GetUserResponseDto getUserResponseDto = userService.findById(id);

    HttpHeaders headers = new HttpHeaders();
    String originalInput = authUsername+":"+authPassword;
    String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
    headers.add("Authorization", "Basic " + encodedString);
    ResponseEntity<GetResourceResponseDto> response = template.exchange(
            projectUrl + "/resource/{id}", HttpMethod.GET, new HttpEntity<Object>(headers),
            GetResourceResponseDto.class, id);

    GetResourceResponseDto getResourceResponseDto = response.getBody();

    getUserResponseDto.setProjectId(getResourceResponseDto.getProjectId());

    return getUserResponseDto;
  }

  @GetMapping("/all")
  public List<GetUserResponseDto> getAll() throws Throwable {
    LOG.info("******************UserController.getAll()*********************");
    HttpHeaders headers = new HttpHeaders();
    String originalInput = authUsername+":"+authPassword;
    String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
    headers.add("Authorization", "Basic " + encodedString);
    ResponseEntity<GetResourceResponseDto[]> response = template.exchange(
      projectUrl + "/resource/all", HttpMethod.GET, new HttpEntity<Object>(headers),
      GetResourceResponseDto[].class);
    GetResourceResponseDto[] getResourceResponseDtos = response.getBody();
    List<GetResourceResponseDto> getResourceResponseDtoList = Arrays.asList(getResourceResponseDtos);
    return userService.findAll(getResourceResponseDtoList);
  }

  public SaveUserResponseDto save(SaveUserRequestDto request, BindingResult bindingResult) throws Throwable {
    getBindingResultError(bindingResult);


    SaveUserResponseDto createUserResponseDto = userService.saveUser(request);
    userService.addCompensationCreateUser(createUserResponseDto);
    if (createUserResponseDto.getUserId() == null) {
      throw new Exception("Error en guardado de usuario");
    }

    SaveResourceRequestDto saveResourceRequestDto = SaveResourceRequestDto.builder().projectId(request.getProjectId())
            .userId(createUserResponseDto.getUserId()).completeName(request.getName() + " " + request.getSurname()).build();

    template.postForEntity(
            projectUrl + "/resource/save",
            resourceRequestBody(saveResourceRequestDto), SaveResourceResponseDto.class);

    //para simular error
    if ("PostProjectFail".equals(request.getName())){
      throw new RuntimeException("Error de prueba");
    }

    return createUserResponseDto;
  }

  private HttpEntity resourceRequestBody(SaveResourceRequestDto saveResourceRequestDto) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    String originalInput = authUsername+":"+authPassword;
    String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
    headers.add("Authorization", "Basic " + encodedString);

    HttpEntity resourceRequestDtoHttpEntity = new HttpEntity<>(
            saveResourceRequestDto,
            headers
    );

    return resourceRequestDtoHttpEntity;
  }

}
