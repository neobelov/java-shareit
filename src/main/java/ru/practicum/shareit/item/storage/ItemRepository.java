package ru.practicum.shareit.item.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByOwnerEquals(Long owner);
    @Query("SELECT item " +
            "FROM Item item " +
            "WHERE item.name like ?1 " +
            "AND item.description like ?1 " +
            "AND item.available = true")
    List<Item> searchItems();
}
