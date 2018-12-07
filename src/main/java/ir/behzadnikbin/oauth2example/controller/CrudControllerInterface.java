package ir.behzadnikbin.oauth2example.controller;

import ir.behzadnikbin.oauth2example.service.serviceresult.ServiceResult;

public interface CrudControllerInterface <T> {

    ServiceResult create(T t);

    ServiceResult<T> read(T t);

    ServiceResult update(T t);

    ServiceResult delete(T t);
}
