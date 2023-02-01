package ru.practicum.ewn.service.utils;

import org.mapstruct.*;
import ru.practicum.ewn.service.dtos.UserDtoCreate;
import ru.practicum.ewn.service.dtos.UserDtoResponse;
import ru.practicum.ewn.service.dtos.UserShortDto;
import ru.practicum.ewn.service.model.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDtoCreate userDtoCreate);

    UserDtoResponse toDto(User user);

    UserShortDto toShortDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserDtoCreate userDtoCreate, @MappingTarget User user);
}