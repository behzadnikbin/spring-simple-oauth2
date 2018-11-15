package ir.behzadnikbin.oauth2example.service.serviceresult;

import org.springframework.http.HttpStatus;

import java.util.List;

/*
    generic content of every response
 */

public class ServiceResultContent<T> {
    //  if the status is OK, content may be not null. null content with OK status means an acknowledge
    public T content;
    //  http status which is in header part of http response is repeated in the body
    public HttpStatus status;
    //  if the status is BAD_REQUEST, validationErrors is not null
    public List<ValidationError> validationErrors;
    //  if the status is INTERNAL_SERVER_ERROR serviceErrorString is not null
    public String serviceErrorString;
}
