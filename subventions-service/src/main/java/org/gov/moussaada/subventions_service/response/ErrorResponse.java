package org.gov.moussaada.subventions_service.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter

public class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
