package com.duman.coursecatalog.controller;

import com.duman.coursecatalog.pojo.Course;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/*
Şimdi burda Eureka Server kullanmamız, arkada bikaç farklı coursepp instance ı çalışıyo registered. Gidip hangisi çalışıyo, hangisine load balancer a göre
erişmesi gerekiyor vs. biliyor ona göre yönlendirme yapıyor.
 */

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private EurekaClient client;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String getCatalogHome(){
        String courseAppMessage = "";
        //String courseAppUrl = "http://localhost:8082/"; Eureka client ı inject ettiğimiz için artık buna gerek kalmadı

        RestTemplate restTemplate = new RestTemplate();
        InstanceInfo inf = client.getNextServerFromEureka("duman-course-app", false);
        // Gittik diğer microservice e eureka server ile microservisin registered olan adıyla adıyla bağlandık

        //courseAppMessage = restTemplate.getForObject(courseAppUrl, String.class); // Hangi url de ne tür dönüş var. String mi, obje mi vs vs.
        String courseAppUrl = inf.getHomePageUrl();
        courseAppMessage = restTemplate.getForObject(courseAppUrl, String.class); // Hangi url de ne tür dönüş var. String mi, obje mi vs vs.
        return "CourseApp'den gelen mesaj: --> " + courseAppMessage;
    }

    @GetMapping("/courses")
    public String getCourseCatalog(){


        RestTemplate restTemplate = new RestTemplate();
        InstanceInfo inf = client.getNextServerFromEureka("duman-course-app", false);
        // Gittik diğer microservice e eureka server ile microservisin registered olan adıyla adıyla bağlandık

        //courseAppMessage = restTemplate.getForObject(courseAppUrl, String.class); // Hangi url de ne tür dönüş var. String mi, obje mi vs vs.
        String courseAppUrl = inf.getHomePageUrl();
        courseAppUrl += "/courses";
        String courses = restTemplate.getForObject(courseAppUrl, String.class); // Hangi url de ne tür dönüş var. String mi, obje mi vs vs.
        return "CourseApp'nin kursları: --> " + courses;
    }

    @GetMapping("/courseslist")
    public List<Course> getCoursesList(){

        RestTemplate restTemplate = new RestTemplate();
        InstanceInfo inf = client.getNextServerFromEureka("duman-course-app", false);

        String courseAppUrl = inf.getHomePageUrl();
        courseAppUrl += "/courses";
        List<Course> courses = restTemplate.getForObject(courseAppUrl, List.class);

        return courses;
    }

    @GetMapping("/courses/{courseId}")
    public Course getSpecificCourse(@PathVariable("courseId") String courseId){
        Course course = null;
        RestTemplate restTemplate = new RestTemplate();
        InstanceInfo inf = client.getNextServerFromEureka("duman-course-app", false);


        String courseAppUrl = inf.getHomePageUrl();
        courseAppUrl += "/courses/" + courseId;
        course = restTemplate.getForObject(courseAppUrl, Course.class); // Hangi url de ne tür dönüş var. String mi, obje mi vs vs.
        return course;
    }
}
