package com.alexander.identityservice.controller;

import com.alexander.identityservice.controller.request.EmailRequest;
import com.alexander.identityservice.controller.request.UserBody;
import com.alexander.identityservice.controller.response.TokenResponse;
import com.alexander.identityservice.dto.UserDto;
import com.alexander.identityservice.service.TokenService;
import com.alexander.identityservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final TokenService tokenService;

    @PostMapping("signin")
    public ResponseEntity<TokenResponse> signIn(@RequestBody @Validated EmailRequest phoneRequest) {
        UserDto userDto = userService.getUserByEmail(phoneRequest.getEmail());
        return ResponseEntity.ok(tokenService.createTokenResponse(userDto.getId()));
    }

    @PostMapping("signup")
    public ResponseEntity<HttpStatus> signUp(@RequestBody @Validated UserBody userBody) {
        userService.saveUser(userBody);
        return ResponseEntity.ok().build();
    }

    @GetMapping("refresh-token")
    public ResponseEntity<TokenResponse> refreshTokens(HttpServletRequest request) {
        String username = (String) request.getAttribute("email");
        String refreshToken = (String) request.getAttribute("token");
        return ResponseEntity.ok(tokenService.refreshTokens(username, refreshToken));
    }

    @GetMapping("me")
    public ResponseEntity<UserDto> getMe(Authentication authentication) {
        return ResponseEntity.ok(userService.getUserByEmail(authentication.getName()));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handleRuntimeExceptions(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

}
