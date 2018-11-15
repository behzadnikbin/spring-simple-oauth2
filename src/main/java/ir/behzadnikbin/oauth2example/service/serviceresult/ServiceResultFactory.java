package ir.behzadnikbin.oauth2example.service.serviceresult;

import org.springframework.http.HttpStatus;

import java.util.List;

@SuppressWarnings("unchecked")
public class ServiceResultFactory {
    private ServiceResultFactory(){}

    public static ServiceResult ok() {
        ServiceResultContent res = new ServiceResultContent();
        res.status = HttpStatus.OK;
        return new ServiceResult(res);
    }

    public static <T> ServiceResult <T> ok(T content) {
        ServiceResultContent<T> res = new ServiceResultContent<>();
        res.status = HttpStatus.OK;
        res.content = content;
        return new ServiceResult<>(res);
    }

    public static ServiceResult validationError(List<ValidationError> errors) {
        ServiceResultContent res = new ServiceResultContent();
        res.status = HttpStatus.BAD_REQUEST;
        res.validationErrors = errors;
        return new ServiceResult<>(res);
    }

    public static ServiceResult serviceError(String message) {
        ServiceResultContent res = new ServiceResultContent();
        res.status = HttpStatus.INTERNAL_SERVER_ERROR;
        res.serviceErrorString = message;
        return new ServiceResult(res);
    }

    public static ServiceResult unauthenticated(String message) {
        ServiceResultContent res = new ServiceResultContent();
        res.status = HttpStatus.FORBIDDEN;
        res.serviceErrorString = message;
        return new ServiceResult(res);
    }

    public static ServiceResult unauthorized(String message) {
        ServiceResultContent res = new ServiceResultContent();
        res.status = HttpStatus.UNAUTHORIZED;
        res.serviceErrorString = message;
        return new ServiceResult(res);
    }
}
