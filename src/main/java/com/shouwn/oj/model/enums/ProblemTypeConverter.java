package com.shouwn.oj.model.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ProblemTypeConverter implements AttributeConverter<ProblemType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ProblemType attribute){
        switch(attribute){
            case PRACTICE : return 0;
            case HOMEWORK : return 1;
            case EXAM : return 2;
        }
        return -1;
    }
    @Override
    public ProblemType  convertToEntityAttribute(Integer dbData){
        switch(dbData){
            case 0 : return ProblemType.PRACTICE;
            case 1 : return ProblemType.HOMEWORK;
            case 2 : return ProblemType.EXAM;
        }
        return null;
    }
}


