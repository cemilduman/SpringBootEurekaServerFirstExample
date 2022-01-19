package com.duman.courseapp.controller;

import com.duman.courseapp.entity.Course;
import com.duman.courseapp.respository.CourseRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CourseController {

    @Autowired
    private CourseRepository repository;

    @GetMapping
    public String welcome(){
        return "CourseApp'ye hoşgeldiniz";
    }

    @RequestMapping(value = "/courses", method = RequestMethod.GET) // Bildiğimiz GetMapping hani bu da böyle bir örnek
    public List<Course> getCourses(){
        return repository.findAll();
    }

    @RequestMapping(value = "/courses/{paramId}", method = RequestMethod.GET) // Bildiğimiz GetMapping hani bu da böyle bir örnek
    public Course getCourseById(@PathVariable("paramId") String courseId){
        Optional<Course> courseOpt = repository.findById(courseId);
        Course course = null;
        if(courseOpt.isPresent()){
            course = courseOpt.get();
        }
        return course;
    }
}
