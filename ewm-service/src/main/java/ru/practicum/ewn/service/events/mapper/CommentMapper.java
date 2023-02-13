package ru.practicum.ewn.service.events.mapper;

import org.mapstruct.*;
import ru.practicum.ewn.service.events.dto.CommentDtoCreate;
import ru.practicum.ewn.service.events.dto.CommentDtoResponse;
import ru.practicum.ewn.service.events.dto.CommentDtoUpdate;
import ru.practicum.ewn.service.events.model.Comment;
import ru.practicum.ewn.service.users.mapper.UserMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {UserMapper.class,
        EventMapper.class})
public interface CommentMapper {
    Comment toEntity(CommentDtoCreate commentDtoCreate);

    @Mapping(source = "author.name", target = "authorName")
    CommentDtoResponse toDto(Comment comment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(CommentDtoUpdate commentDtoUpdate, @MappingTarget Comment comment);
}