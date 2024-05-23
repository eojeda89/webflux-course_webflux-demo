package com.webfluxcourse.webfluxdemo.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class Response {

    private Date date = new Date();
    private int output = 0;

    public Response(int output) {
        this.output = output;
    }

}
