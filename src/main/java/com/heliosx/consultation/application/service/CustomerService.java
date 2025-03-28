package com.heliosx.consultation.application.service;

import com.heliosx.consultation.domain.Customer;
import com.heliosx.consultation.infrastructure.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public void saveCustomer(UUID customerUid) {
        Optional<Customer> existingCustomer = customerRepository.findByCustomerUid(customerUid);
        if (existingCustomer.isPresent()) {
            log.info("Customer {} already exists", customerUid);
            return;
        }
        Customer customer = Customer.builder()
                .customerUid(customerUid)
                .build();
        customerRepository.save(customer);
        log.info("Created customer with uid {}", customerUid);
    }

}
