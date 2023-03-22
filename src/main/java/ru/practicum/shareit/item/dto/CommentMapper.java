package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

public class CommentMapper {
    public CommentDto mapToCommentDto(Comment comment) {
        if (comment == null || comment.getAuthor().getName() == null) {
            return new CommentDto();
        }
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getAuthor().getName(),
                comment.getCreated());
    }
    public Comment mapToComment(CommentDto dto, Item item, User booker) {
        return new Comment(
                dto.getId(),
                dto.getText(),
                item,
                booker,
                LocalDateTime.now());
    }
}
