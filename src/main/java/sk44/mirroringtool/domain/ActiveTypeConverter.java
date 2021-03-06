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
@Converter //(autoApply = true)
public class ActiveTypeConverter implements AttributeConverter<ActiveType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ActiveType attribute) {
        return attribute.getTypeValue();
    }

    @Override
    public ActiveType convertToEntityAttribute(Integer dbData) {
        return ActiveType.activeTypeOfValue(dbData);
    }
}
