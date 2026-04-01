package com.example.ipgserviceproject.controller;

import com.example.ipgserviceproject.model.output.Client;
import com.example.ipgserviceproject.model.output.MetaData;
import com.example.ipgserviceproject.model.output.ResultObject;
import com.example.ipgserviceproject.model.output.Status;
import com.example.ipgserviceproject.repository.ResultObjectRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ResultObjectRepository resultObjectRepository;
    ResultObject result;
    Client client;
    MetaData metaData;
    Status status;
    ResponseEntity<ResultObject> responseEntity;

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResultObject> typeMisMatched(HttpServletRequest request) {
        result = new ResultObject();
        client = new Client();
        metaData = new MetaData();
        status = new Status();
        status.setStatusCode(400);
        status.setMessage("Id should be only numbers");
        ResultObject savedObject = getResultObject(request);
        responseEntity = new ResponseEntity<>(savedObject, HttpStatus.OK);
        return responseEntity;
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResultObject> wrongPath(HttpServletRequest request) {
        result = new ResultObject();
        client = new Client();
        metaData = new MetaData();
        status = new Status();
        status.setStatusCode(404);
        status.setMessage("Not Found!");
        ResultObject savedObject = getResultObject(request);
        responseEntity = new ResponseEntity<>(savedObject, HttpStatus.OK);
        return responseEntity;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResultObject> duplicatedPrimaryKey(HttpServletRequest request) {
        result = new ResultObject();
        client = new Client();
        metaData = new MetaData();
        status = new Status();
        status.setStatusCode(400);
        status.setMessage("This id already exists!");
        ResultObject savedObject = getResultObject(request);
        responseEntity = new ResponseEntity<>(savedObject, HttpStatus.OK);
        return responseEntity;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResultObject> nullField(HttpServletRequest request) {
        result = new ResultObject();
        client = new Client();
        metaData = new MetaData();
        status = new Status();
        status.setStatusCode(400);
        status.setMessage("Fields shouldn't be null!");
        ResultObject savedObject = getResultObject(request);
        responseEntity = new ResponseEntity<>(savedObject, HttpStatus.OK);
        return responseEntity;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResultObject> wrongStructure(HttpServletRequest request) {
        result = new ResultObject();
        client = new Client();
        metaData = new MetaData();
        status = new Status();
        status.setStatusCode(400);
        status.setMessage("Wrong structure in data!");
        ResultObject savedObject = getResultObject(request);
        responseEntity = new ResponseEntity<>(savedObject, HttpStatus.OK);
        return responseEntity;
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResultObject> unSupportedMediaType(HttpServletRequest request) {
        result = new ResultObject();
        client = new Client();
        metaData = new MetaData();
        status = new Status();
        status.setStatusCode(415);
        status.setMessage("Unsupported media type!");
        ResultObject savedObject = getResultObject(request);
        responseEntity = new ResponseEntity<>(savedObject, HttpStatus.OK);
        return responseEntity;
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResultObject> unSupportedMethod(HttpServletRequest request) {
        result = new ResultObject();
        client = new Client();
        metaData = new MetaData();
        status = new Status();
        status.setStatusCode(405);
        status.setMessage("Method not allowed!");
        ResultObject savedObject = getResultObject(request);
        responseEntity = new ResponseEntity<>(savedObject, HttpStatus.OK);
        return responseEntity;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResultObject> wrongHeaders(HttpServletRequest request,
                                                     MethodArgumentNotValidException ex) {
        result = new ResultObject();
        client = new Client();
        metaData = new MetaData();
        status = new Status();
        status.setStatusCode(400);
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            status.setMessage(fieldName + " " + message);
        });
        ResultObject savedObject = getResultObject(request);
        responseEntity = new ResponseEntity<>(savedObject, HttpStatus.OK);
        return responseEntity;
    }

    private ResultObject getResultObject(HttpServletRequest request) {
        if (request.getRemoteAddr().equals("0:0:0:0:0:0:0:1")) {
            client.setClientIp("127.0.0.1");
        } else {
            client.setClientIp(request.getRemoteAddr());
        }
        client.setHttpMethod(request.getMethod());
        metaData.setClient(client);
        metaData.setStatus(status);
        result.setMetaData(metaData);
        ResultObject savedResult = resultObjectRepository.save(result);
        result.getMetaData().setRequestId(savedResult.getTransactionId());
        return result;
    }
}

