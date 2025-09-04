package org.jw.preechingsheet.api.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.jw.preechingsheet.api.models.ApiResponse;
import org.jw.preechingsheet.api.services.IRateLimiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class RateLimitAspect {

	@Autowired
    private IRateLimiterService rateLimiterService;


    @Around("@annotation(org.jw.preechingsheet.api.annotations.RateLimited) || @within(org.jw.preechingsheet.api.annotations.RateLimited)")
    public Object applyRateLimiting(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request =
            ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String clientIp = request.getRemoteAddr();

        if (!rateLimiterService.isRateLimited(clientIp)) {
        	ApiResponse<Void> response = ApiResponse.error("Demasiadas peticiones. Intentelo más tarde");
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(response);
        }

        return joinPoint.proceed();
    }
}