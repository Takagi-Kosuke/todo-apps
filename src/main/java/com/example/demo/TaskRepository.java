package com.example.demo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
	// ユーザー名でフィルタリングするメソッド
	List<Task> findByUsername(String username);

}
