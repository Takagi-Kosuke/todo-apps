package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

@Controller
public class HelloController {

	@Autowired
	private TaskRepository taskRepository;

	@GetMapping("/")
	public String index(Model model,@AuthenticationPrincipal UserDetails user) {
		String currentUsername = user.getUsername();
		
		
		model.addAttribute("tasks", taskRepository.findByUsername(currentUsername));
		model.addAttribute("username",currentUsername);
		return "index";
	}

	@PostMapping("/add")
	public String addTask(@RequestParam String title,
			@RequestParam String deadline, 
			@RequestParam String priority,
			@AuthenticationPrincipal UserDetails user) {
		Task task = new Task();
		task.setTitle(title);
		task.setDeadline(deadline);
		task.setPriority(priority);
		task.setCompleted(false);
		
		task.setUsername(user.getUsername());
		
		taskRepository.save(task);
		return "redirect:/";

	}

	@PostMapping("/complete")
	public String copleteTask(@RequestParam Long id) {
		Task task = taskRepository.findById(id).orElseThrow();

		task.setCompleted(!task.isCompleted());

		taskRepository.save(task);

		return "redirect:/";
	}

	@PostMapping("/delete")
	public String deleteTask(@RequestParam Long id) {
		taskRepository.deleteById(id);

		return "redirect:/";
	}
}