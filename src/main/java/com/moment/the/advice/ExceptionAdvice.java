package com.moment.the.advice;

import com.moment.the.advice.exception.*;
import com.moment.the.domain.response.CommonResult;
import com.moment.the.domain.response.ListResult;
import com.moment.the.domain.response.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {
    private final ResponseService responseService;
    private final MessageSource messageSource;

    // code 정보에 해당하는 메시지를 조회한다.
    private String getMessage(String code){
        return getMessage(code, null);
    }

    // code 정보, 추가 argument로 현재 locale에 맞는 메시지를 조회합니다.
    private String getMessage(String code, Object[] args){
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    // 예외 처리 메시지를 MessageSource 에서 가져오도록 수정
    @ExceptionHandler(Exception.class)
    protected CommonResult defaultException(HttpServletRequest request, Exception e){
        return responseService.getFailResult(Integer.valueOf(getMessage("unKnown.code")), e.getMessage());
    }
    // 사용자를 찾을 수 업습니다.
    @ExceptionHandler(UserNotFoundException.class)
    protected CommonResult userNotFoundException(HttpServletRequest request, UserNotFoundException e){
        return responseService.getFailResult(Integer.valueOf(getMessage("userNotFound.code")), getMessage("userNotFound.msg"));
    }
    // 유저가 이미 존재합니다.
    @ExceptionHandler(UserAlreadyExistsException.class)
    protected CommonResult userAlreadyExistsException(HttpServletRequest request, UserAlreadyExistsException e){
        return responseService.getFailResult(Integer.valueOf(getMessage("userAlreadyExists.code")), getMessage("userAlreadyExists.msg"));
    }
    // 해당 게시글을 찾을 수 업습니다.
    @ExceptionHandler(NoPostException.class)
    protected CommonResult noPostException(HttpServletRequest request, NoPostException e){
        return responseService.getFailResult(Integer.valueOf(getMessage("noPost.code")), getMessage("noPost.msg"));
    }
    // 해당 답변을 찾을 수 없습니다.
    @ExceptionHandler(NoCommentException.class)
    protected CommonResult noCommentException(HttpServletRequest request, NoCommentException e){
        return responseService.getFailResult(Integer.valueOf(getMessage("noComment.code")), getMessage("noComment.msg"));
    }
    // 해당 개선 사례를 찾을 수 업습니다.
    @ExceptionHandler(NoImprovementException.class)
    protected CommonResult noImprovementException(HttpServletRequest request, NoImprovementException e){
        return responseService.getFailResult(Integer.valueOf(getMessage("noImprovement.code")), getMessage("noImprovement.msg"));
    }
    // 요청 형식에 알맞지 않습니다.(MethodArgumentNotValidException)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected CommonResult customMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException ex){
        return responseService.getFailResult(Integer.valueOf(getMessage("method-argument-not-valid.code")), getMessage("method-argument-not-valid.msg"));
    }
    // 요청 형식에 알맞지 않습니다.(CustomMethodArgumentNotValidException)
    @ExceptionHandler(CustomMethodArgumentNotValidException.class)
    protected CommonResult customMethodArgumentNotValidException(HttpServletRequest req, CustomMethodArgumentNotValidException ex){
        return responseService.getFailResult(Integer.valueOf(getMessage("method-argument-not-valid.code")), getMessage("method-argument-not-valid.msg"));
    }
    //추천할 수 없습니다.
    @ExceptionHandler(GoodsNotCancelException.class)
    protected CommonResult goodsNotCancelException(HttpServletRequest request, GoodsNotCancelException e){
        return responseService.getFailResult(Integer.valueOf(getMessage("goods-not-cancel.code")), getMessage("goods-not-cancel.msg"));
    }
    //accessToken 이 만료되었습니다.
    @ExceptionHandler(AccessTokenExpiredException.class)
    protected CommonResult accessTokenExpiredException(HttpServletRequest req, AccessTokenExpiredException e){
        return responseService.getFailResult(Integer.valueOf(getMessage("access-token-expired.code")), getMessage("access-token-expired.msg"));
    }
}
