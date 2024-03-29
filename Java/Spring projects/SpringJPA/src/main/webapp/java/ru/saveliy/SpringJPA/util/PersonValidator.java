package ru.saveliy.SpringJPA.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.saveliy.SpringJPA.model.Person;
import ru.saveliy.SpringJPA.services.PeopleService;

@Component
public class PersonValidator implements Validator{

    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        Person personCheck = peopleService.show(person.getFullName());
        if (personCheck != null && personCheck.getId() != ((Person) o).getId()){
            errors.rejectValue("fullName", "", "Данные ФИО уже заняты!");
        }
    }
}