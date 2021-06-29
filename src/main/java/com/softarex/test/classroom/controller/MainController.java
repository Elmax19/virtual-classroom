package com.softarex.test.classroom.controller;

import com.softarex.test.classroom.model.Student;
import com.softarex.test.classroom.repo.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
public class MainController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    @Autowired
    private StudentRepository studentRepository;

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/raiseHand/{login}")
    public HttpStatus riseHand(@PathVariable String login) {
        LOGGER.info("changing hand status!");
        Optional<Student> user = studentRepository.findById(login);
        if (user.isPresent()) {
            Student student = user.get();
            student.setHandUp(!student.isHand());
            studentRepository.save(student);
            return HttpStatus.OK;
        }
        return HttpStatus.EXPECTATION_FAILED;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/logout/{login}")
    public void logout(@PathVariable String login) {
        LOGGER.info("hi");
        LOGGER.info(login);
        Optional<Student> user = studentRepository.findById(login);
        if (user.isPresent()) {
            Student student = user.get();
            studentRepository.delete(student);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/login")
    public void postLogin(@RequestBody String login, HttpServletRequest request) {
        Student user = new Student(login, false);
        studentRepository.save(user);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/studentsList")
    public @ResponseBody
    List<Student> getStudents() {
        List<Student> studentList = new ArrayList<>();
        studentRepository.findAll().forEach(studentList::add);
        Comparator<Student> comparator = Comparator.comparing(Student::getLogin);
        studentList.sort(comparator);
        return studentList;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/user")
    public @ResponseBody
    Student getUser(@RequestBody String login) {
        Optional<Student> user = studentRepository.findById(login);
        return user.orElse(null);
    }

}