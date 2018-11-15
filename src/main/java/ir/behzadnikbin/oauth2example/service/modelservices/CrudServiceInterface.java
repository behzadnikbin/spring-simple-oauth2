package ir.behzadnikbin.oauth2example.service.modelservices;

import ir.behzadnikbin.oauth2example.service.serviceresult.ServiceResult;

public interface CrudServiceInterface<T> {

    ServiceResult create(T t);

    ServiceResult read(T t);

    ServiceResult update(T t);

    ServiceResult delete(T t);
}
