package ru.practicum.shareit.storage;

import java.util.List;

public interface Storage <T extends StorageObject<T>> {
    T post(T obj);

    T put(T obj);

    T delete(Integer id);

    List<T> getAll();

    T getById(Integer id);

}