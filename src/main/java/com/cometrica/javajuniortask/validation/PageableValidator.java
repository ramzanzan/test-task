package com.cometrica.javajuniortask.validation;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;

public class PageableValidator implements ConstraintValidator<PageableConstraint, Pageable> {

    private PageableConstraint constraint;

    @Override
    public void initialize(PageableConstraint constraintAnnotation) {
        constraint=constraintAnnotation;
        if(constraint.minSize() < 1)
            throw new IllegalArgumentException("minSize can't be < 1");
        if( (constraint.maxSize() & constraint.maxPage() & constraint.minPage()) < 0)
            throw new IllegalArgumentException("maxSize or minPage or maxPage can't be < 0");
        if(constraint.minSize() > constraint.maxSize())
            throw new IllegalArgumentException("minSize can't be > maxSize");
        if(constraint.minPage() > constraint.maxPage())
            throw new IllegalArgumentException("minPage can't be > maxPage");

    }

    @Override
    public boolean isValid(Pageable pg, ConstraintValidatorContext context) {
        if(pg.isUnpaged()) return constraint.allowUnpaged();
        if(pg.getPageNumber()<constraint.minPage() || pg.getPageNumber()>constraint.maxPage()
            || pg.getPageSize()<constraint.minSize() || pg.getPageSize()>constraint.maxSize())
            return false;
        if(constraint.orders().length != 0){
            HashMap<String, SortPropertyConstraint> socs = new HashMap<>();
            for(SortPropertyConstraint soc : constraint.orders()) socs.put(soc.value(),soc);
            for(Sort.Order order: pg.getSort()){
                SortPropertyConstraint soc = socs.get(order.getProperty());
                if( soc==null || !soc.direction().testCompliance(order.getDirection())
                        || (order.isIgnoreCase() && !soc.ignoreCase()) )
                    return false;
            }
        }
        return true;
    }
}
