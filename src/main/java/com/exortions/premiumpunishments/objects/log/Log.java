package com.exortions.premiumpunishments.objects.log;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class Log {

    private final String uuid;
    private final String type;
    private final String reason;
    private final String target;
    private final String username;
    private final Timestamp date;
    private final String time;

}
