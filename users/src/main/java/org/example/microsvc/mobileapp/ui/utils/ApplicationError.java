package org.example.microsvc.mobileapp.ui.utils;

import lombok.Data;

import java.util.Date;

@Data
public class ApplicationError {
    private Date timestamp;
    private String message;
}
