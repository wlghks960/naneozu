package nnz.adminservice.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nnz.adminservice.entity.Show;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowKafkaDTO {
    private Long id;

    private String title;

    private String location;

    private String startDate;

    private String endDate;

    private String ageLimit;
    private String region;

    private String posterImage;
    private String categoryCode;

    private Long createdBy;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    private Long updatedBy;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updatedAt;

    protected boolean isDelete;


    public static ShowKafkaDTO entityToDTO(Show show) {

        return ShowKafkaDTO.builder()
                .categoryCode(show.getCategory().getCode())
                .ageLimit(show.getAgeLimit())
                .location(show.getLocation())
                .region(show.getRegion())
                .posterImage(show.getPosterImage())
                .startDate(show.getStartDate())
                .endDate(show.getEndDate())
                .title(show.getTitle())
                .createdBy(show.getCreatedBy())
                .createdAt(show.getCreatedAt())
                .updatedBy(show.getUpdatedBy())
                .updatedAt(show.getUpdatedAt())
                .isDelete(show.getIsDelete())
                .build();
    }
}
