package com.monitora.aulamicroservices.core.repository;

import com.monitora.aulamicroservices.core.model.Course;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CourseRepository extends PagingAndSortingRepository<Course, Long> {

}
