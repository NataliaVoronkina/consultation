package com.heliosx.consultation.types;

import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
public class ConsultationUid {

    private final UUID value;

    public ConsultationUid(UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("ConsultationUid cannot be null");
        }
        this.value = value;
    }

    public ConsultationUid() {
        this.value = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsultationUid that = (ConsultationUid) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return "ConsultationUid{" +
                "value=" + value +
                '}';
    }

}
