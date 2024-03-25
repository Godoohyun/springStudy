package study_spring.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.Getter;
import lombok.Setter;

@Controller
public class ExampleController {
	
	@GetMapping("/thymeleaf/example")
	public String thymeleafException(Model model) {
		Person examplePerson = new Person();
		examplePerson.setId(1L);
		examplePerson.setAge(37);
		examplePerson.setName("김두한");
		examplePerson.setHobbies(List.of("공차기", "게임", "영화"));
		
		model.addAttribute("person", examplePerson);
		model.addAttribute("today", LocalDateTime.now());
		
		return "example";
	}
	
	@Getter
	@Setter
	class Person {
		private Long id;
		private String name;
		private int age;
		private List<String> hobbies;
	}
}
