package com.geara.drugpack.controllers;

import com.geara.drugpack.dao.UserDao;
import com.geara.drugpack.requests.AuthenticationRequest;
import com.geara.drugpack.securityconfig.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
@Tag(
    name = "Authorization",
    description = "Methods used for authorization, registration and restoring account")
@SecurityRequirement(name = "noAuth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final UserDao userDao;
  private final JwtUtils jwtUtils;

  @Operation(summary = "Request to sign up new account")
  @ApiResponse(
      responseCode = "200",
      description = "Returns token",
      content = @Content(schema = @Schema(implementation = ResponseEntity.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Returns error message",
      content = @Content(schema = @Schema(implementation = ResponseEntity.class)))
  @PostMapping("signUp")
  ResponseEntity<String> signUp(@RequestBody AuthenticationRequest request) {
    final var user = userDao.findUserByEmail(request.getEmail());

    if (user != null) {
      return ResponseEntity.badRequest().body("Account with this e-mail already exists");
    }

    final var newUser = userDao.newUser(request.getEmail(), request.getPassword());

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

    return ResponseEntity.ok(jwtUtils.generateToken(newUser));
  }

  @Operation(summary = "Request to sign into existing account")
  @ApiResponse(
      responseCode = "200",
      description = "Returns token",
      content = @Content(schema = @Schema(implementation = ResponseEntity.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Returns error message",
      content = @Content(schema = @Schema(implementation = ResponseEntity.class)))
  @PostMapping("signIn")
  ResponseEntity<String> signIn(@RequestBody AuthenticationRequest request) {
    final var user = userDao.findUserByEmail(request.getEmail());

    if (user == null) {
      return ResponseEntity.badRequest().body("No such account found");
    }

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

    return ResponseEntity.ok(jwtUtils.generateToken(user));
  }
}
