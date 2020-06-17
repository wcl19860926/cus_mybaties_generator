package com.study.generator.exception;

public class GeneratorException  extends  RuntimeException {


    public GeneratorException(String msg, Throwable e) {
        super(msg ,e);
    }


    public GeneratorException(String msg) {
        super(msg);
    }


    public GeneratorException(Throwable e) {
        super(e);
    }
}
