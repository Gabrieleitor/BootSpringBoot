package com.training.converter;

import com.training.model.PersonName;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static java.util.Objects.isNull;

@Converter
public class PersonNameConverter implements AttributeConverter<PersonName, String> {

    private static final String NAME_SEPARATOR = ", ";

    @Override
    public String convertToDatabaseColumn(PersonName personName) {
        if (isNull(personName)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        if (!StringUtils.isEmpty(personName.getName())) {
            sb.append(personName.getName());
            sb.append(NAME_SEPARATOR);
        }

        if (!StringUtils.isEmpty(personName.getLastname())) {
            sb.append(personName.getName());
        }

        return sb.toString();
    }

    @Override
    public PersonName convertToEntityAttribute(String dbPersonName) {
        if (StringUtils.isEmpty(dbPersonName)) {
            return null;
        }

        String[] pieces = dbPersonName.split(NAME_SEPARATOR);

        if (pieces == null || pieces.length == 0) {
            return null;
        }

        PersonName personName = new PersonName();
        String firstPiece = !pieces[0].isEmpty() ? pieces[0] : null;
        if (dbPersonName.contains(NAME_SEPARATOR)) {
            personName.setName(firstPiece);

            if (pieces.length >= 2 && !StringUtils.isEmpty(pieces[1])) {
                personName.setLastname(pieces[1]);
            }
        } else {
            personName.setLastname(firstPiece);
        }

        return personName;
    }

}
