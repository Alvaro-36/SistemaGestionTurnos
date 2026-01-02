package com.example.sistTurnos.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
public class TimeRange {
    private LocalTime startTime;
    private LocalTime endTime;

    public boolean includesDateTimeRange(TimeRange range) {
        return this.startTime.isBefore(range.startTime) && this.endTime.isAfter(range.endTime);
    }
    public boolean includesDateTime(LocalTime time) {
        return this.startTime.isBefore(time) && this.endTime.isAfter(time);
    }
}
