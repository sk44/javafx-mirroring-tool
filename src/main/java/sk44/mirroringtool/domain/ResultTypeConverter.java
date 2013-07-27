/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk44.mirroringtool.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author sk
 */
@Converter
public class ResultTypeConverter implements AttributeConverter<ResultType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ResultType attribute) {
        return attribute.getTypeValue();
    }

    @Override
    public ResultType convertToEntityAttribute(Integer dbData) {
        return ResultType.resultTypeOfValue(dbData);
    }
}
