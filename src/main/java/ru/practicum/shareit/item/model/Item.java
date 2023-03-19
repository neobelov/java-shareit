package ru.practicum.shareit.item.model;

import lombok.Data;
import ru.practicum.shareit.exceptions.validation.PostInfo;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "items", schema = "public")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(groups = PostInfo.class, message = "Item name can't be null")
    @NotEmpty(groups = PostInfo.class, message = "Item name can't be empty")
    @NotBlank(groups = PostInfo.class, message = "Item name can't be blank")
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull(groups = PostInfo.class, message = "Item description can't be null")
    @NotEmpty(groups = PostInfo.class, message = "Item description can't be empty")
    @NotBlank(groups = PostInfo.class, message = "Item description can't be blank")
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull(groups = PostInfo.class, message = "Item availability must be sent")
    @Column(name = "available", nullable = false)
    private Boolean available;

    @Column(name = "owner_id", nullable = false)
    private Long owner;

}
