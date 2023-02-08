package ru.practicum.ewn.service.users.mapper;

import org.mapstruct.*;
import ru.practicum.ewn.service.users.dto.UserDtoCreate;
import ru.practicum.ewn.service.users.dto.UserDtoResponse;
import ru.practicum.ewn.service.users.dto.UserShortDto;
import ru.practicum.ewn.service.users.model.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDtoCreate userDtoCreate);

    UserDtoResponse toDto(User user);

    UserShortDto toShortDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserDtoCreate userDtoCreate, @MappingTarget User user);
}