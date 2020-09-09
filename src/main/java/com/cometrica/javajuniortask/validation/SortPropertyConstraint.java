package com.cometrica.javajuniortask.validation;

import org.springframework.data.domain.Sort;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface SortPropertyConstraint {

    String value();
    Direction direction() default Direction.BOTH;
    boolean ignoreCase() default true;

    enum Direction{
        ASC(Sort.Direction.ASC),
        DESC(Sort.Direction.DESC),
        BOTH(null);
        
        private Sort.Direction allowed;
        
        Direction(Sort.Direction direction){
            allowed = direction;
        }
        
        public boolean testCompliance(Sort.Direction direction){
            if (this.equals(BOTH)) return true;
            else return this.allowed.equals(direction);
        }
    }
}