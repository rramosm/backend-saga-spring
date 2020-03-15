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

package pe.com.empresa.poc.azure.projectms.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pe.com.empresa.poc.azure.core.controllers.BaseController;
import pe.com.empresa.poc.azure.projectms.dto.*;
import pe.com.empresa.poc.azure.projectms.services.ResourceService;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.List;

@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RestController
@RequestMapping("resource")
public class ResourceController extends BaseController {

  private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @Value("${security.service.address:http://localhost:8081}")
  private String securityUrl;

  @Autowired
  private ResourceService resourceService;

  @Autowired
  private RestTemplate template;

  @PostMapping("/save")
  public SaveResourceResponseDto saveResource(@Valid final @RequestBody SaveResourceRequestDto saveResourceRequestDto,
                                              BindingResult bindingResult) throws Throwable {
    LOG.info("******************ResourceController.saveResource(ResourceRequestDto resourceRequestDto)*********************");
    getBindingResultError(bindingResult);
    SaveResourceResponseDto saveResourceResponseDto = resourceService.saveResource(saveResourceRequestDto);
    resourceService.addCompensationSaveResource(saveResourceResponseDto);
    return saveResourceResponseDto;
  }

  @DeleteMapping("/{id}")
  public DeleteResourceResponseDto delete(@PathVariable("id") Long id) throws Throwable {
    LOG.info("******************ResourceController.delete(@PathVariable(\"id\") Long id)*********************");
    DeleteResourceResponseDto deleteUserResponseDto = resourceService
            .deleteResource(DeleteResourceRequestDto.builder().userId(id).build());
    resourceService.addCompensationDeleteResource(deleteUserResponseDto);
    return deleteUserResponseDto;
  }

  @GetMapping("/{id}")
  public GetResourceResponseDto getResource(@PathVariable("id") Long id) throws Throwable {
    LOG.info("******************ResourceController.getResource(@PathVariable(\"id\") Long id)*********************");
    return resourceService.findByUserId(id);
  }

  @GetMapping("/all")
  public List<GetResourceResponseDto> getAll() throws Throwable {
    LOG.info("******************ResourceController.getAll()*********************");
    return resourceService.findAll();
  }

}
