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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pe.com.empresa.poc.azure.core.controllers.BaseController;
import pe.com.empresa.poc.azure.projectms.dto.ListProjectResponseDto;
import pe.com.empresa.poc.azure.projectms.services.ProjectService;

import java.lang.invoke.MethodHandles;

@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.OPTIONS})
@RestController
@RequestMapping("project")
public class ProjectController extends BaseController {

  private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @Value("${security.service.address:http://localhost:8081}")
  private String securityUrl;

  @Autowired
  private ProjectService projectService;

  @Autowired
  private RestTemplate template;

  @GetMapping("/all/active")
  public ListProjectResponseDto listProjectActive() throws Throwable {
    LOG.info("******************listProjectActive()*********************");
    return ListProjectResponseDto.builder().projects(projectService.listProjectActive()).build();
  }
}
