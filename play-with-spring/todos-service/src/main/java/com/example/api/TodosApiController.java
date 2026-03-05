package com.example.api;


import com.example.api.dto.EditTodoDto;
import com.example.api.dto.NewTodoDto;
import com.example.exception.HttpError;
import com.example.exception.TodoNotFoundException;
import com.example.model.Todo;
import com.example.model.TodoType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//@Controller
@RestController
@RequestMapping("/api/v1/todos")
@CrossOrigin(origins = "http://localhost:4200")
public class TodosApiController {

    private static List<Todo> todos = new ArrayList<>();

    static {
        todos.add(new Todo(1L, "Buy groceries", "Milk, Bread, Eggs", false, 1L, TodoType.PERSONAL));
        todos.add(new Todo(2L, "Finish project", "Complete the API project", false, 1L, TodoType.WORK));
        todos.add(new Todo(3L, "Call friend", "Catch up with John", false, 1L, TodoType.SOCIAL));
        todos.add(new Todo(4L, "Go to gym", "Workout session at 6 PM", false, 1L, TodoType.PERSONAL));
        todos.add(new Todo(5L, "Team meeting", "Discuss project progress", false, 1L, TodoType.WORK));
    }


    /*
        How to map request to handler-methods?
        -> by URL path
        -> by HTTP method (GET, POST, PUT, DELETE, PATCH)
        -> by presence/absence of query parameters (e.g., ?type=WORK)
        -> by presence/absence of request headers (e.g., Content-Type, Accept)
     */

    //     - GET /api/v1/todos -> List of all todos
    @GetMapping(
            produces = {"application/json"},
            params = {"!type"}, // This means the 'type' parameter should NOT be present
            headers = {"X-FOO"} //
    )
    public List<Todo> getAllTodosV1() {
        System.out.println("X-FOO header is present with value BAR");
        return todos;
    }

    @GetMapping(
            produces = {"application/json"},
            params = {"!type"}, // This means the 'type' parameter should NOT be present
            headers = {"!X-FOO"} // This means the 'X-FOO' header should NOT be present
    )
    public List<Todo> getAllTodos() {
        System.out.println("GET /api/v1/todos called without 'type' query parameter");
        return todos;
    }

    @GetMapping(
            produces = {"application/json"},
            params = {"type"} // This means the 'type' parameter should be present
    )
    public List<Todo> getTodosByType(@RequestParam TodoType type) {
        return todos.stream()
                .filter(todo -> todo.getType() == type)
                .collect(Collectors.toList());
    }

    @GetMapping(
            produces = {"application/xml"},
            params = {"!type"}, // This means the 'type' parameter should NOT be present
            headers = {"!X-FOO"} // This means the 'X-FOO
    )
    public List<Todo> getAllTodosXml() {
        System.out.println("GET /api/v1/todos called without 'type' query parameter and with Accept: application/xml header");
        return todos;
    }


    //     - GET /api/v1/todos/{id} -> Get a specific todo by id
    @GetMapping("/{id}")
    public Todo getTodoById(@PathVariable Long id) {
        return todos.stream()
                .filter(todo -> todo.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + id));
    }

    // HEAD /api/todos/{id} -> Get metadata of a specific todo by id (without response body)
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<?> getTodoMetadataById(@PathVariable Long id) {
        boolean exists = todos.stream()
                .anyMatch(todo -> todo.getId().equals(id));
        if (!exists) {
            throw new TodoNotFoundException("Todo not found with id: " + id);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-FOO", "HEAD");
        headers.add("X-TODO-EXISTS", exists ? "true" : "false");
        return ResponseEntity.ok().headers(headers).build();
    }

    // Write handler methods for POST, PUT, DELETE, PATCH as needed

    @PostMapping(
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public ResponseEntity<Todo> createTodo(@RequestBody NewTodoDto newTodo) {
        Long newId = todos.stream()
                .mapToLong(Todo::getId)
                .max()
                .orElse(0L) + 1;
        Todo todo = new Todo(newId, newTodo.getTitle(), newTodo.getDescription(), false, 1L, TodoType.valueOf(newTodo.getType()));
        todos.add(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(todo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody EditTodoDto editTodoDto) {
        Todo existingTodo = todos.stream()
                .filter(todo -> todo.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + id));

        existingTodo.setTitle(editTodoDto.getNewTitle());
        existingTodo.setDescription(editTodoDto.getNewDescription());
        existingTodo.setType(TodoType.valueOf(editTodoDto.getNewType()));
        existingTodo.setCompleted(editTodoDto.isNewCompleted());
        return ResponseEntity.ok(existingTodo);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Todo> partiallyUpdateTodo(@PathVariable Long id, @RequestBody EditTodoDto editTodoDto) {
        Todo existingTodo = todos.stream()
                .filter(todo -> todo.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + id));

        if (editTodoDto.getNewTitle() != null) {
            existingTodo.setTitle(editTodoDto.getNewTitle());
        }
        if (editTodoDto.getNewDescription() != null) {
            existingTodo.setDescription(editTodoDto.getNewDescription());
        }
        if (editTodoDto.getNewType() != null) {
            existingTodo.setType(TodoType.valueOf(editTodoDto.getNewType()));
        }
        existingTodo.setCompleted(editTodoDto.isNewCompleted());
        return ResponseEntity.ok(existingTodo);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        boolean removed = todos.removeIf(todo -> todo.getId().equals(id));
        if (!removed) {
            throw new TodoNotFoundException("Todo not found with id: " + id);
        }
        return ResponseEntity.noContent().build();
    }


}
