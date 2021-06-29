package com.softarex.test.classroom.controller;

import com.softarex.test.classroom.model.Student;
import com.softarex.test.classroom.service.MainService;
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

import java.util.List;

/**
 * Main controller to handle urls
 *
 * @author Elmax19
 * @version 1.0
 */
@RestController
public class MainController {
    /**
     * domain url of frontend
     */
    private static final String DOMAIN = "http://localhost:4200";
    /**
     * {@link MainService} class to call
     */
    @Autowired
    MainService ms;

    /**
     * method to handle changing student hand status
     *
     * @param login user login
     * @return {@link HttpStatus#OK} if student information was updated, otherwise - {@link HttpStatus#EXPECTATION_FAILED}
     * @see MainService#changeHandStatus(String login)
     */
    @CrossOrigin(origins = DOMAIN)
    @PutMapping("/raiseHand/{login}")
    public HttpStatus riseHand(@PathVariable String login) {
        if (ms.changeHandStatus(login)) {
            return HttpStatus.OK;
        }
        return HttpStatus.EXPECTATION_FAILED;
    }

    /**
     * method to handle student logout
     *
     * @param login student login
     * @see MainService#deleteStudent(String login)
     */
    @CrossOrigin(origins = DOMAIN)
    @DeleteMapping("/logout/{login}")
    public void logout(@PathVariable String login) {
        ms.deleteStudent(login);
    }

    /**
     * method to handle student login
     *
     * @param login student login
     * @see MainService#addStudent(String login)
     */
    @CrossOrigin(origins = DOMAIN)
    @PostMapping("/login")
    public void postLogin(@RequestBody String login) {
        ms.addStudent(login);
    }

    /**
     * method to handle url of getting list of students
     *
     * @return all logged in students
     * @see MainService#findAllStudents()
     */
    @CrossOrigin(origins = DOMAIN)
    @GetMapping("/studentsList")
    public @ResponseBody
    List<Student> getStudents() {
        return ms.findAllStudents();
    }

    /**
     * method to handle url of finding current student
     *
     * @param login student login
     * @return searched student
     * @see MainService#findStudentByLogin(String login)
     */
    @CrossOrigin(origins = DOMAIN)
    @PostMapping("/user")
    public @ResponseBody
    Student getUser(@RequestBody String login) {
        return ms.findStudentByLogin(login).orElse(null);
    }

}