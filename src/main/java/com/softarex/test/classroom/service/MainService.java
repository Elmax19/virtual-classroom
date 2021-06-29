package com.softarex.test.classroom.service;

import com.softarex.test.classroom.model.Student;
import com.softarex.test.classroom.repo.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Student service class to work with {@link StudentRepository}
 * @author Elmax19
 * @version 1.0
 */
@Service("ms")
public class MainService {
    /**
     * {@link Logger} to log some situations
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MainService.class);
    /**
     * {@link StudentRepository} class to call
     */
    @Autowired
    private StudentRepository studentRepository;

    /**
     * method to find student information
     * @param login required student login
     * @return student information
     * @see StudentRepository#findById(Object)
     */
    public Optional<Student> findStudentByLogin(String login) {
        return studentRepository.findById(login);
    }

    /**
     * method to find information about all logged in students
     * @return sorted list of students
     * @see StudentRepository#findAll()
     */
    public List<Student> findAllStudents() {
        List<Student> studentList = new ArrayList<>();
        studentRepository.findAll().forEach(studentList::add);
        Comparator<Student> comparator = Comparator.comparing(Student::getLogin);
        studentList.sort(comparator);
        return studentList;
    }

    /**
     * method to create new student
     * @param login student login
     * @see StudentRepository#save(Object)
     */
    public void addStudent(String login) {
        LOGGER.info("Adding new Student: " + login);
        Student user = new Student(login, false);
        studentRepository.save(user);
    }

    /**
     * method to delete student information
     * @param login required student login
     * @see StudentRepository#delete(Object)
     */
    public void deleteStudent(String login) {
        LOGGER.info("Deleting Student:" + login);
        Optional<Student> user = studentRepository.findById(login);
        if (user.isPresent()) {
            Student student = user.get();
            studentRepository.delete(student);
        }
    }

    /**
     * method to change student hand status
     * @param login require student login
     * @return has hand status been changed
     * @see StudentRepository#save(Object)
     */
    public boolean changeHandStatus(String login) {
        LOGGER.info("Changing " + login + " hand status");
        Optional<Student> user = studentRepository.findById(login);
        if (user.isPresent()) {
            Student student = user.get();
            student.setHandUp(!student.isHand());
            studentRepository.save(student);
            return true;
        }
        return false;
    }

}
