package com.monitora.aulamicroservices.course.endpoint.controller;

import com.monitora.aulamicroservices.core.model.Course;
import com.monitora.aulamicroservices.course.endpoint.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //define que essa classe será um controller do spring
@RequestMapping("v1/admin/course") //rota da API
@Slf4j //fornece uma API de log Java por meio de um padrão de fachada simples
@RequiredArgsConstructor(onConstructor = @__(@Autowired)) //injeta na classe a dependencia usando o lombok
@Api(value = "Endpoints to manage Courses")
public class CourseController {
    private final CourseService courseService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "List all available course", response = Course[].class)
    public ResponseEntity<Iterable<Course>> list(Pageable pageable) {
        log.info("Controller listing all courses");
        return new ResponseEntity<>(courseService.list(pageable), HttpStatus.OK);
    }
}
