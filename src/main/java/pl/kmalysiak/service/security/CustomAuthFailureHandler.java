package pl.kmalysiak.service.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomAuthFailureHandler {

    public ResponseEntity getReponseOnAuthFailure() {
        Map<String, Object> data = new HashMap<>();
        data.put("timestamp", Calendar.getInstance().getTime());
        data.put("message", "Bad login request");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(data);
    }

}
