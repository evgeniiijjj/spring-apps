package com.example.bookingservice.aop;

import com.example.bookingservice.dtos.UserDto;
import com.example.bookingservice.exceptions.AccessDeniedException;
import com.example.bookingservice.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Aspect
@Component
@RequiredArgsConstructor
public class VerifyUserAspect {

    private final UserDetailsServiceImpl detailsService;

    @Before("@annotation(VerifyUserGetOrDelete)")
    public void verifyUserGetOrDelete(JoinPoint joinPoint) {
        String name = (String) joinPoint.getArgs()[0];
        if (detailsService.getUserDetails().getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).noneMatch(role -> role.equals("ROLE_ADMIN")) &&
                !name.equals(detailsService.getUserDetails().getUsername())) {
            throw new AccessDeniedException(
                    MessageFormat
                            .format(
                                    "User {0} does not have rights to view or delete another account!",
                                    detailsService.getUserDetails().getUsername()
                            )
            );
        }
    }

    @Before("@annotation(VerifyUserUpdate)")
    public void verifyUserUpdate(JoinPoint joinPoint) {
        UserDto user = (UserDto) joinPoint.getArgs()[0];
        if (detailsService.getUserDetails().getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).noneMatch(role -> role.equals("ROLE_ADMIN")) &&
                !user.getUserName().equals(detailsService.getUserDetails().getUsername())) {
            throw new AccessDeniedException(
                    MessageFormat
                            .format(
                                    "User {0} does not have rights to update another account!",
                                    detailsService.getUserDetails().getUsername()
                            )
            );
        }
    }

    @Before("@annotation(VerifyGetBookingsByUser)")
    public void verifyGetBookingsByUser(JoinPoint joinPoint) {
        String userName = (String) joinPoint.getArgs()[1];
        if (detailsService.getUserDetails().getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).noneMatch(role -> role.equals("ROLE_ADMIN")) &&
                !userName.equals(detailsService.getUserDetails().getUsername())) {
            throw new AccessDeniedException(
                    MessageFormat
                            .format(
                                    "User {0} does not have rights to get bookings another user!",
                                    detailsService.getUserDetails().getUsername()
                            )
            );
        }
    }
}
