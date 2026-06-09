package com.alpefesekerci.trello_clone_app.repository;

import com.alpefesekerci.trello_clone_app.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
