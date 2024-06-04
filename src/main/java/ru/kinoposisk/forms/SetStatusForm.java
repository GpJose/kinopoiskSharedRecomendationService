package ru.kinoposisk.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetStatusForm {
    private Boolean isActive;
    private Long userId;
}
