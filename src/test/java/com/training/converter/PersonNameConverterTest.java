package com.training.converter;

import com.training.model.PersonName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PersonNameConverterTest {

    @InjectMocks
    private PersonNameConverter personNameConverter;

    private static final String DB_PERSON_NAME = "De Niro, Robert";
    private static final String PERSON_NAME = "Robert";
    private static final String PERSON_LAST_NAME = "De Niro";

    @Test
    public void givenAPersonNameWhenConvertToDatabaseColumnThenReturnString() {
        //given
        PersonName personName = PersonName.builder()
            .name(PERSON_NAME)
            .lastName(PERSON_LAST_NAME)
            .build();

        //when
        String dataBasePersonName = personNameConverter.convertToDatabaseColumn(personName);

        //then
        Assertions.assertNotNull(dataBasePersonName);
        Assertions.assertEquals(DB_PERSON_NAME, dataBasePersonName);
    }

    @Test
    public void givenAStringPersonNameWhenConvertToEntityAttributeThenReturnPersonName() {
        //given
        PersonName expectedPersonName = PersonName.builder()
            .name(PERSON_NAME)
            .lastName(PERSON_LAST_NAME)
            .build();

        //when
        PersonName personName = personNameConverter.convertToEntityAttribute(DB_PERSON_NAME);

        //then
        Assertions.assertNotNull(personName);
        Assertions.assertEquals(expectedPersonName, personName);
    }

}
