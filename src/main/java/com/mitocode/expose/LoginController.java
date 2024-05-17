package com.mitocode.expose;

import com.mitocode.business.UserService;
import com.mitocode.security.AuthRequest;
import com.mitocode.security.AuthResponse;
import com.mitocode.security.JwtUtil;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class LoginController {

  private final JwtUtil jwtUtil;
  private final UserService service;

  @PostMapping("/login")
  public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest authRequest) {
    return service.searchByUser(authRequest.getUsername())
      .map(userDetails -> {
        if (BCrypt.checkpw(authRequest.getPassword(), userDetails.getPassword())) {
          String token = jwtUtil.generateToken(userDetails);
          Date expiration = jwtUtil.getExpirationDateFromToken(token);

          return ResponseEntity.ok(new AuthResponse(token, expiration));
        } else {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
      })
      .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
  }
}
