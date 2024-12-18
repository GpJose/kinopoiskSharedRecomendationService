package ru.kinoposisk.dto.profile;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsersDTO {

    private String login;
    private String email;
    private List<FriendsDTO> friends;
    private List<MovieHistoryDTO> movieHistories;
}
