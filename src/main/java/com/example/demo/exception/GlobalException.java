package com.example.demo.exception;

import com.example.demo.result.CodeMsg;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    private CodeMsg cm;

    public GlobalException(CodeMsg cm){
        super(cm.toString());
        this.cm = cm;
    }

}
