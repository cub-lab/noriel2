package com.rsystems.noriel.userrecomandations;

import com.rsystems.noriel.jsoncsvconverter.JsonCsvConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class UserRecommendationsController {
    @Autowired
    private UserRecommendationsDAOService service;

    @RequestMapping("/testVC")
    String hello() {
    	
    	BasicExample bs = new BasicExample();
    	String stResponse = new String();
    	try {
			stResponse = bs.azureConn();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        return "Hello World! spring is here ULTIMATE: " + stResponse;
        
    }

    
    @PostMapping("/userRecommendations")
    public List createUser(@RequestBody User user) {
        System.out.printf("user id = " + user.getUserId());
        return service.getRecommendationsByUser();
    }

    @PostMapping("/userActionTypes")
    public void saveUserActionTypes(@RequestBody String jsonString) {
        try {
            JsonCsvConverter.JsonToCsv(jsonString, new File("d:/aaa.csv"));
//            AzureServices az = new AzureServices();
//            az.azureConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
