package com.Kunchala.Ecommerce.Advice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import java.util.List;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
    private HttpStatus status;
    private String msg;
    private List<String> subError;
}
