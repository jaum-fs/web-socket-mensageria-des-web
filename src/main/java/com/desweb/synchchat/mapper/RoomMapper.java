package com.desweb.synchchat.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.desweb.synchchat.dto.RoomDto;
import com.desweb.synchchat.model.Room;
import com.desweb.synchchat.model.Usuario;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    // Room -> RoomDto
    @Mapping(target = "userIds", source = "users", qualifiedByName = "mapUsersToIds")
    @Mapping(target = "isPrivate", source = "password", qualifiedByName = "mapPasswordToIsPrivate")
    @Mapping(target = "ownerId", source = "owner.id")
    @Mapping(target = "historico", ignore = true)
    RoomDto toRoomDto(Room room);

    // RoomDto -> Room (inverso automático)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @InheritInverseConfiguration(name = "toRoomDto")
    Room toRoom(RoomDto roomDto);

    // List<Room> -> List<RoomDto>
    List<RoomDto> toRoomsDto(List<Room> rooms);

    // List<RoomDto> -> List<Room>
    List<Room> toRooms(List<RoomDto> roomDtos);

    @Named("mapUsersToIds")
    default List<Long> mapUsersToIds(List<Usuario> users) {
        if (users == null) {
            return null;
        }
        return users.stream()
                .map(Usuario::getId)
                .collect(Collectors.toList());
    }

    @Named("mapPasswordToIsPrivate")
    default Boolean mapPasswordToIsPrivate(String password) {
        return password != null && !password.isEmpty();
    }
}