package ir.behzadnikbin.oauth2example.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import ir.behzadnikbin.oauth2example.service.serviceresult.ServiceResultFactory;
import ir.behzadnikbin.oauth2example.service.serviceresult.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Component;

import java.util.Collections;


/*
    An aspect to catch all exceptions in services and return response according to type of exception
 */

@Slf4j
@Aspect
@Component
public class ExceptionHandler {

    @Around("execution( ir.behzadnikbin.oauth2example.service.serviceresult.ServiceResult ir.behzadnikbin.oauth2example.service.*.*(..) )")
    public Object catchExceptions(final ProceedingJoinPoint pjp) {
        try {
            return pjp.proceed();
        } catch (AuthenticationCredentialsNotFoundException e) {
            return ServiceResultFactory.unauthenticated("invalid credentials");
        } catch (AccessDeniedException | UnauthorizedUserException e) {
            return ServiceResultFactory.unauthorized("unauthorized user");
        } catch (JsonProcessingException e) {
            log.warn("error in processing json", e);
            return ServiceResultFactory.validationError(Collections.singletonList(new ValidationError("", "bad request. may be invalid json")));
        } catch (Throwable e) {
            log.error("error in doing service action", e);
            return ServiceResultFactory.serviceError(e.getMessage());
        }
    }
}
