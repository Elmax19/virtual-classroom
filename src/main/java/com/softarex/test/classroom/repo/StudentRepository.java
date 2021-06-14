package com.softarex.test.classroom.repo;

import com.softarex.test.classroom.model.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, String> {
}
