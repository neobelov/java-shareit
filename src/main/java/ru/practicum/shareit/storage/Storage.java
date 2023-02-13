package ru.practicum.shareit.storage;

import java.util.List;

public interface Storage <T extends StorageObject<T>> {
    public T post(T obj);
    public T put(T obj);
    public T delete (Integer id);
    public List<T> getAll();
    public T getById(Integer id);
}