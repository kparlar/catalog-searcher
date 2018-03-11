package com.kparlar.catalogsearcher.exception;

import java.util.ArrayList;
import java.util.List;

public class ExceptionMessage {
    private List<String> errors;
    public ExceptionMessage() {
        this.setErrors(new ArrayList<>());
    }
    public List<String> getErrors() {
            return this.errors;
    }
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
            return "ErrorMessage [errors=" + String.join(",", errors) + "]";
    }

}
