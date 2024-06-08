package com.example.todo.service;

import com.example.todo.config.BaseException;
import com.example.todo.config.BaseResponseStatus;
import com.example.todo.dto.TodoItemDto;
import com.example.todo.entity.TodoItem;
import com.example.todo.entity.TodoStatus;
import com.example.todo.repository.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoItemRepository todoItemRepository;

    public TodoItem createTodoItem(TodoItemDto todoItemDto) throws BaseException {
        TodoStatus status = todoItemDto.getStatus();
        if (status != null && !EnumSet.of(TodoStatus.COMPLETED, TodoStatus.PROCEED, TodoStatus.INCOMPLETE).contains(status)) {
            throw new BaseException(BaseResponseStatus.INVALID_STATUS);
        }
        TodoItem todoItem = new TodoItem();
        todoItem.setName(todoItemDto.getName());
        todoItem.setStatus(todoItemDto.getStatus());
        return todoItemRepository.save(todoItem);
    }

    public Page<TodoItem> getAllTodoItems(Pageable pageable) {
        return todoItemRepository.findAll(pageable);
    }


    public TodoItem getTodoItemById(Long id) throws BaseException {
        return todoItemRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.ITEM_NOT_FOUND));
    }

    public TodoItem updateTodoItem(Long id, TodoItemDto todoItemDto) throws BaseException {
        Optional<TodoItem> optionalTodoItem = todoItemRepository.findById(id);

        if (optionalTodoItem.isEmpty()) {
            throw new BaseException(BaseResponseStatus.ITEM_NOT_FOUND);
        }

        TodoItem todoItem = optionalTodoItem.get();

        if (todoItemDto.getName() != null) {
            todoItem.setName(todoItemDto.getName());
        }
        TodoStatus status = todoItemDto.getStatus();
        if (status != null && !EnumSet.of(TodoStatus.COMPLETED, TodoStatus.PROCEED, TodoStatus.INCOMPLETE).contains(status)) {
            throw new BaseException(BaseResponseStatus.INVALID_STATUS);
        }
        if (status != null) {
            todoItem.setStatus(status);
        }

        if (todoItemDto.getStatus() != null) {
            todoItem.setStatus(todoItemDto.getStatus());
        }

        return todoItemRepository.save(todoItem);
    }

    public void deleteTodoItem(Long id) throws BaseException {
        Optional<TodoItem> optionalTodoItem = todoItemRepository.findById(id);

        if (optionalTodoItem.isEmpty()) {
            throw new BaseException(BaseResponseStatus.ITEM_NOT_FOUND);
        }

        todoItemRepository.delete(optionalTodoItem.get());
    }

    public List<TodoItem> getTodoItemsByName(String name, int page, int size) throws BaseException {
        Pageable pageable = PageRequest.of(page, size);
        Page<TodoItem> todoItemsPage = todoItemRepository.findByNameContaining(name, pageable);
        List<TodoItem> todoItems = todoItemsPage.getContent();
        if (todoItems.isEmpty()) {
            throw new BaseException(BaseResponseStatus.ITEM_NOT_FOUND);
        }
        return todoItems;
    }

    public List<TodoItem> getTodoItemsByStatus(TodoStatus status, int page, int size) throws BaseException {
        if (!EnumSet.of(TodoStatus.COMPLETED, TodoStatus.PROCEED, TodoStatus.INCOMPLETE).contains(status)) {
            throw new BaseException(BaseResponseStatus.INVALID_STATUS);
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<TodoItem> todoItemsPage = todoItemRepository.findByStatus(status, pageable);
        List<TodoItem> todoItems = todoItemsPage.getContent();
        if (todoItems.isEmpty()) {
            throw new BaseException(BaseResponseStatus.ITEM_NOT_FOUND);
        }
        return todoItems;
    }

    //월별조회
    public List<TodoItem> getTodoItemsByMonth(int month, int page, int size) throws BaseException {
        Pageable pageable = PageRequest.of(page, size);
        Page<TodoItem> todoItemsPage = todoItemRepository.findByMonth(month, pageable);
        List<TodoItem> todoItems = todoItemsPage.getContent();
        if (todoItems.isEmpty()) {
            throw new BaseException(BaseResponseStatus.ITEM_NOT_FOUND);
        }
        return todoItems;
    }

    //오래된순
    public List<TodoItem> getTodoItemsByOldest(int page, int size) throws BaseException {
        Pageable pageable = PageRequest.of(page, size);
        Page<TodoItem> todoItemsPage = todoItemRepository.findAllByOrderByCreatedAtAsc(pageable);
        List<TodoItem> todoItems = todoItemsPage.getContent();
        if (todoItems.isEmpty()) {
            throw new BaseException(BaseResponseStatus.ITEM_NOT_FOUND);
        }
        return todoItems;
    }

    //최신순 조회
    public List<TodoItem> getTodoItemsByLatest(int page, int size) throws BaseException {
        Pageable pageable = PageRequest.of(page, size);
        Page<TodoItem> todoItemsPage = todoItemRepository.findAllByOrderByCreatedAtDesc(pageable);
        List<TodoItem> todoItems = todoItemsPage.getContent();
        if (todoItems.isEmpty()) {
            throw new BaseException(BaseResponseStatus.ITEM_NOT_FOUND);
        }
        return todoItems;
    }


    // 완성도 확인
    public String getCompletionRatio() {
        long completedCount = todoItemRepository.countByStatus(TodoStatus.COMPLETED);
        long totalCount = todoItemRepository.count();
        double ratio = (double) completedCount / totalCount;
        DecimalFormat df = new DecimalFormat("0%");
        return df.format(ratio);
    }

}
