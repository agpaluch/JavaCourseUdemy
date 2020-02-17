package io.github.mat3e.todo;

import io.github.mat3e.HibernateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

class TodoRepository {

    private final Logger logger = LoggerFactory.getLogger(TodoRepository.class);

    List<Todo> findAll(){
        var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction();

        var result = session.createQuery("from Todo", Todo.class).list();

        transaction.commit();
        session.close();
        return result;
    }

    Todo toggleTodo(Integer id){
        var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction();
        var todo = session.get(Todo.class, id);

        try {
            todo.setDone(!todo.isDone());
        } catch (NullPointerException e){
            logger.error("There is no todo with id " + id + " in the database.");
        }

        transaction.commit();
        session.close();
        return todo;
    }

    Todo addTodo(Todo newTodo){
        var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction();

        session.persist(newTodo);

        transaction.commit();
        session.close();
        return newTodo;
    }

}
