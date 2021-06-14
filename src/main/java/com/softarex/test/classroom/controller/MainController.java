package com.softarex.test.classroom.controller;

import com.softarex.test.classroom.model.Student;
import com.softarex.test.classroom.repo.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
public class MainController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    @Autowired
    private StudentRepository studentRepository;
    private final int PORT = 0;
    List<Socket> clients = new ArrayList<>();

    @GetMapping("/room")
    public String home(HttpServletRequest request, Model model) {
        Object user = request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        model.addAttribute("students", getAllStudents());
        model.addAttribute("user", (Student) user);
        return "room";
    }

    private List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        studentRepository.findAll().forEach(studentList::add);
        Comparator<Student> comparator = Comparator.comparing(Student::getLogin);
        studentList.sort(comparator);
        return studentList;
    }

    @PostMapping("/")
    public String postLogin(@RequestParam String login, HttpServletRequest request, Model model) {
        if (request.getSession().getAttribute("user") != null) {
            return "redirect:/room";
        }

        if (login.trim().equals("") || studentRepository.findById(login.trim()).isPresent()) {
            model.addAttribute("errorMsg", "Such student is already joined");
            return "login";
        } else {
            Student user = new Student(login, false);
            studentRepository.save(user);
            request.getSession().setAttribute("user", user);
            sendMessage(((Student) user).getLogin() + " joined the room!");
            try {
                ServerSocket serverSocket = new ServerSocket(PORT);
                Socket socket = serverSocket.accept();
                request.getSession().setAttribute("socket", socket);
                clients.add(socket);
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
            return "redirect:/room";
        }
    }

    @GetMapping("/")
    public String login(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") != null) {
            LOGGER.info("hi :)");
            return "redirect:/room";
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        Object user = request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        Student student = (Student) user;
        studentRepository.delete(student);
        Socket socket = (Socket) request.getSession().getAttribute("socket");
        clients.remove(socket);
        sendMessage(((Student) user).getLogin() + " left the room!");
        request.getSession().invalidate();
        return "redirect:/";
    }

    @GetMapping("/raiseHand")
    public String riseHand(HttpServletRequest request) {
        Object user = request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        Student student = (Student) user;
        student.setHandUp(!student.isHand());
        if (student.isHand()){
            sendMessage(((Student) user).getLogin() + " raised up his/her hand!");
        } else {
            sendMessage(((Student) user).getLogin() + " raised down his/her hand!");
        }
        studentRepository.save(student);
        return "redirect:/room";
    }

    private void sendMessage(String message) {
        for (Socket socket : clients ) {
            try {;
                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);
                bw.write(message);
                bw.flush();
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

//    @RequestMapping("/error")
//    public String handleError(HttpServletRequest request, Model model) {
//        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//        if (status != null) {
//            int statusCode = Integer.parseInt(status.toString());
//            if(statusCode == HttpStatus.NOT_FOUND.value()) {
//                model.addAttribute("error", "404: Not Found!");
//                return "error";
//            }
//            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
//                model.addAttribute("error", "505: " + RequestDispatcher.ERROR_EXCEPTION + ' ' + RequestDispatcher.ERROR_MESSAGE);
//                return "error";
//            }
//        }
//        return "errorPage";
//    }

}