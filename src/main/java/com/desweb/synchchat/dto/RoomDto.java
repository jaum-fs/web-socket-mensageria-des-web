package com.desweb.synchchat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public record RoomDto(
        UUID id,
        Long ownerId,
        List<String> historico,
        @JsonProperty("room_users")
        List<Long> userIds,
        Boolean isPrivate
) {
}