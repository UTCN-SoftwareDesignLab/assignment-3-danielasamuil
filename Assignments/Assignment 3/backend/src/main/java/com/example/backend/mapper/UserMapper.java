package com.example.backend.mapper;

import com.example.backend.model.User;
import com.example.backend.model.dtos.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "name", source = "user.username")
    })
    UserDto toDto(User user);

    @Mappings({
            @Mapping(target = "username", source = "name")
    })
    User fromDto(UserDto userDto);
}
