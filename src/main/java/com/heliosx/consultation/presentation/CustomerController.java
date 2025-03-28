package com.heliosx.consultation.presentation;

import com.heliosx.consultation.application.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/create/{customerUid}")
    public void createCustomer(@PathVariable UUID customerUid) {
        customerService.saveCustomer(customerUid);
    }

}
