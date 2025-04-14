package org.gov.moussaada.admin_service.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class SuccessResponse<T> {
    private String message;
    private T data;
    private int code;

    public SuccessResponse(String message, int code , T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public SuccessResponse(String message) {
        this.message = message;
    }
}