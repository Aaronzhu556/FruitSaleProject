package org.songxueyu.cdgy.fruitsaleproject.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MyResponse {
    private String code;
    private String msg;
    private String info;
    private Object object;
    private String page;


}
