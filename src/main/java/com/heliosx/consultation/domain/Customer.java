package com.heliosx.consultation.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID customerUid;

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(customerUid, customer.customerUid) && Objects.equals(name, customer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerUid, name);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", customerUid=" + customerUid +
                ", name='" + name + '\'' +
                '}';
    }
}
