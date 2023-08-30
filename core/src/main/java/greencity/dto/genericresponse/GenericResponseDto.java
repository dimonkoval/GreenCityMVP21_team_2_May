package greencity.dto.genericresponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponseDto {
    List<FieldErrorDto> errors = new ArrayList<>();

    /**
     * Method which build GenericResponseDto and add fieldErrors.
     *
     * @param bindingResult {@link BindingResult}
     * @return {@link GenericResponseDto}
     */
    public static GenericResponseDto buildGenericResponseDto(BindingResult bindingResult) {
        GenericResponseDto genericResponseDto = new GenericResponseDto();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            genericResponseDto.getErrors().add(
                new FieldErrorDto(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return genericResponseDto;
    }
}
