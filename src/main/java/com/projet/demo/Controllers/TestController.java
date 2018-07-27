package com.projet.demo.Controllers;





import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/")
public class TestController {


@GetMapping("/")
@CrossOrigin(origins="*")
/**
 * this is just a test controller to check if the application is working correctly
 * or not you can access this controller by sending a GET request to the {baseURL}
 */
public String Hello(){
    System.out.println("application is working!");
    return "application is working";
}

}
