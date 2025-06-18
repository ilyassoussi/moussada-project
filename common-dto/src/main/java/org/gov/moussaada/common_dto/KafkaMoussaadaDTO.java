package org.gov.moussaada.common_dto;

public class KafkaMoussaadaDTO {
    private String type;
    private Object payload;

    public KafkaMoussaadaDTO() {
    }

    public KafkaMoussaadaDTO(String type, Object payload) {
        this.type = type;
        this.payload = payload;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}

