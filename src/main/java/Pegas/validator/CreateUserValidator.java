package Pegas.validator;

import Pegas.DTO.CreateUserDTO;
import Pegas.entity.Gender;
import Pegas.entity.Role;
import Pegas.utils.LocalDateFormatter;

public final class CreateUserValidator implements Validator<CreateUserDTO> {
    private static volatile CreateUserValidator INSTACE;

    public ValidationResult isValid(CreateUserDTO createUserDTO){
        ValidationResult validationResult = new ValidationResult();
        if(!LocalDateFormatter.isValid(createUserDTO.birthday())){
            validationResult.add(Error.of("invalid.birthday", "Birthday is invalid"));
        }if(Gender.find(String.valueOf(createUserDTO.gender())).isEmpty()){
            validationResult.add(Error.of("invalid.gender", "Gender is invalid"));
        }
        if(Role.find(String.valueOf(createUserDTO.role())).isEmpty()){
            validationResult.add(Error.of("invalid.role", "Role is invalid"));
        }
        if(createUserDTO.user_name().isEmpty()){
            validationResult.add(Error.of("invalid.user_name", "User_name is invalid"));
        }
        if(createUserDTO.email().isEmpty()){
            validationResult.add(Error.of("invalid.email", "Email is invalid"));
        }
        if(createUserDTO.password().isEmpty()){
            validationResult.add(Error.of("invalid.password", "Password is invalid"));
        }
        return validationResult;
    }


    private CreateUserValidator() {}
    public static CreateUserValidator getINSTACE(){
        if(INSTACE==null){
            synchronized (CreateUserValidator.class){
                if(INSTACE==null){
                    INSTACE=new CreateUserValidator();
                }
            }
        }
        return INSTACE;
    }
}
