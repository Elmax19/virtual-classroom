package com.softarex.test.classroom.repo;

import com.softarex.test.classroom.model.Student;
import org.springframework.data.repository.CrudRepository;

/**
 * Student repository class to work with database
 * @author Elmax19
 * @version 1.0
 * @see CrudRepository
 */
public interface StudentRepository extends CrudRepository<Student, String> {
}
