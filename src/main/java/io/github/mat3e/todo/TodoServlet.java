package io.github.mat3e.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="todo", urlPatterns = {"api/todos/*"})
public class TodoServlet extends HttpServlet {

    private TodoRepository todoRepository;
    private ObjectMapper mapper;

    private final Logger logger = LoggerFactory.getLogger(TodoServlet.class);

    /**
     * Constructor used by Hibernate
     */
    @SuppressWarnings("unused")
    public TodoServlet(){
        this(new TodoRepository(), new ObjectMapper());
    }

    TodoServlet(TodoRepository todoRepository, ObjectMapper mapper){
        this.todoRepository = todoRepository;
        this.mapper = mapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        mapper.writeValue(resp.getOutputStream(), todoRepository.findAll());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Todo newTodo = mapper.readValue(req.getInputStream(), Todo.class);
            resp.setContentType("application/json;charset=UTF-8");
            mapper.writeValue(resp.getOutputStream(), todoRepository.addTodo(newTodo));
        } catch (Exception e){
            logger.error(e.getMessage());
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var pathInfo = req.getPathInfo();
        Integer todoId = null;
        try {
            todoId = Integer.valueOf(pathInfo.substring(1));
            var todo = todoRepository.toggleTodo(todoId);
            resp.setContentType("application/json;charset=UTF-8");
            mapper.writeValue(resp.getOutputStream(), todo);
        } catch (NullPointerException | NumberFormatException e){
            logger.error("Wrong path used: " +pathInfo);
        }


    }



}
