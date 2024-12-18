package ru.kinoposisk.dto.profile;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieHistoryDTO {

    private Long movieId;
    private String movieName;
    private String posterUrl;
    private Date watchedDate;
    private Integer rating;
}
