package com.buguagaoshu.mall.warehouse.exception;

import com.buguagaoshu.common.exception.BizCodeEnum;
import com.buguagaoshu.common.utils.R;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

/**
 * @author Pu Zhiwei {@literal puzhiweipuzhiwei@foxmail.com}
 * create          2020-04-09 19:29
 */
@RestControllerAdvice(basePackages = "com.buguagaoshu.mall.warehouse.controller")
public class WareExceptionControllerAdvice {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        HashMap<String, String> map = new HashMap<>();
        result.getFieldErrors().forEach((item)->{
            map.put(item.getField(), item.getDefaultMessage());
        });
        return R.error(BizCodeEnum.VALID_EXCEPTION.getCode(),
                BizCodeEnum.VALID_EXCEPTION.getMessage())
                .put("data", map);
    }


    @ExceptionHandler(value = Exception.class)
    public R handException(Exception e) {
        return R.error(BizCodeEnum.UNKNOWN_EXCEPTION.getCode(),
                e.getMessage());
    }
}
