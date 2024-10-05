package com.github.bruce_mig.paypal_integration.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * ErrorDetails
 * @author Bruce Migeri
 */
@Getter
@Setter
@AllArgsConstructor
public class ErrorDetails {
    private LocalDateTime timeStamp;
    private String message;
    private String details;
}
