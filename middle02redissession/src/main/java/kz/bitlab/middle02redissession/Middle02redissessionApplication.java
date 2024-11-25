package kz.bitlab.middle02redissession;

import jakarta.servlet.http.HttpSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@SpringBootApplication
public class Middle02redissessionApplication {

	public static void main(String[] args) {
		SpringApplication.run(Middle02redissessionApplication.class, args);
	}

	@GetMapping(value = "/")
	public String indexPage(){
		return "This is Index Page";
	}

	@GetMapping(value = "/session-page")
	public String getSession(HttpStatus session){
		return "session is " + session;
	}

	@GetMapping(value = "/save-to-session/{data}")
	public String saveToSession(@PathVariable(name = "data") String data, HttpSession session){
		ArrayList<String> dataList;
		if(session.getAttribute("dataList")!=null) {
			dataList = (ArrayList<String>) session.getAttribute("dataList");
		} else {
			dataList = new ArrayList<>();
		}
		dataList.add(data);
		session.setAttribute("dataList", dataList);
		return "success";
	}

	@GetMapping(value = "/view-data")
	public String dataList(HttpSession session){
		ArrayList<String> dataList;
		if(session.getAttribute("dataList")!=null){
			dataList = (ArrayList<String>) session.getAttribute("dataList");
		} else {
			dataList = new ArrayList<>();
		}
		String result = "";
		for (String data : dataList){
			result += data + " ";
		}
		return result;
	}
}
