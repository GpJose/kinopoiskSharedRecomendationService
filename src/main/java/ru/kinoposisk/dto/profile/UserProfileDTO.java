package ru.kinoposisk.dto.profile;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileDTO {

    private String login;
    private String email;
    private List<FriendProfileDTO> friends;
    private List<MovieHistoryDTO> movieHistories;
}
