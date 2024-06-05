package auth_service.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import auth_service.constant.ResponseConstant;
import auth_service.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TestServiceController {
    public ResponseEntity<ResponseData<String>> user(Long id) {
        ResponseData<String> response = new ResponseData<>(ResponseConstant.EMPTY);

        try {
            response.setData("User Dashboard");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Unauthorized");
            log.error(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<ResponseData<String>> admin() {
        ResponseData<String> response = new ResponseData<>(ResponseConstant.EMPTY);

        try {
            response.setData("Admin Dashboard");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Unauthorized");
            log.error(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<ResponseData<String>> publicAccess() {
        ResponseData<String> response = new ResponseData<>(ResponseConstant.EMPTY);

        try {
            response.setData("Public Access");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Unauthorized");
            log.error(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
}
