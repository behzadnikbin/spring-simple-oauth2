package ir.behzadnikbin.oauth2example.service.serviceresult;

import org.springframework.http.ResponseEntity;

/*
    generic content of every response
 */

public class ServiceResult<T> extends ResponseEntity<ServiceResultContent<T>> {
    ServiceResult(ServiceResultContent<T> content) {
        super(content, content.status);
    }
}

