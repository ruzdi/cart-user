package com.artomus.cartuser.controller;

import com.artomus.cartuser.filter.RequestResponseLoggingFilter;
import com.artomus.cartuser.model.CartUserDetails;
import com.artomus.cartuser.model.JwtRequest;
import com.artomus.cartuser.model.JwtResponse;
import com.artomus.cartuser.service.CartUserDetailsService;
import com.artomus.cartuser.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CartUserDetailsService cartUserDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationController.class);


    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        logger.info("Authentication request received for user: {}", authenticationRequest.getUsername());

        try {
            logger.debug("Attempting authentication for user: {}", authenticationRequest.getUsername());

            // Authenticate the user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );

            logger.info("User authenticated successfully: {}", authenticationRequest.getUsername());

        } catch (BadCredentialsException e) {
            logger.error("Authentication failed for user: {}", authenticationRequest.getUsername());
            throw new Exception("Incorrect username or password", e);
        }

        // Load user details and generate JWT
        final CartUserDetails userDetails = cartUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        logger.info("JWT generated for user: {}", authenticationRequest.getUsername());

        return ResponseEntity.ok(new JwtResponse(jwt));
    }

}
