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
public class ActiveTypeConverter implements AttributeConverter<ActiveType, String> {

    @Override
    public String convertToDatabaseColumn(ActiveType attribute) {
        return attribute.getTypeValue().toString();
    }

    @Override
    public ActiveType convertToEntityAttribute(String dbData) {
        return ActiveType.activeTypeOfValue(Integer.valueOf(dbData));
    }
}
