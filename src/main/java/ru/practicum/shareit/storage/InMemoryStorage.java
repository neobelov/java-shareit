package ru.practicum.shareit.storage;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.ResourceNotFoundException;

import java.util.*;

public class InMemoryStorage<T extends StorageObject<T>> implements Storage<T> {
    Map<Integer, T> objects = new HashMap<>();
    Integer prevId = 0;

    @Override
    public T post(T obj) {
        obj.setId(++prevId);
        objects.put(prevId, obj);
        return obj;
    }

    @Override
    public T put(T obj) {
        if (!objects.containsKey(obj.getId())) {
            throw new ResourceNotFoundException(String.format("Object with id %d for update is not found", obj.getId()));
        }
        objects.put(obj.getId(), obj);
        return obj;
    }

    @Override
    public T delete(Integer id) {
        T obj = objects.remove(id);
        if (obj == null) {
            throw new ResourceNotFoundException(String.format("Object with id %d for removal is not found", id));
        }
        return obj;
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(objects.values());
    }

    @Override
    public T getById(Integer id) {
        T obj = objects.get(id);
        if (obj == null) {
            throw new ResourceNotFoundException(String.format("Object with id %d is not found", id));
        }
        return obj;
    }
}
