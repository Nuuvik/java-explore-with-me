package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class StatDto {

    @NotBlank(groups = Marker.Create.class)
    @Size(max = 50, groups = Marker.Create.class)
    private String app;

    @NotBlank(groups = Marker.Create.class)
    @Size(max = 50, groups = Marker.Create.class)
    private String uri;

    @NotBlank(groups = Marker.Create.class)
    @Size(max = 15, groups = Marker.Create.class)
    private String ip;

    @NotNull(groups = Marker.Create.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime timestamp;
}
