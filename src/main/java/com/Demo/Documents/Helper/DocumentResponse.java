package com.Demo.Documents.Helper;

import lombok.*;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentResponse {
    private String imageName;
    private String message;

    private boolean success;

    private HttpStatus status;
}
