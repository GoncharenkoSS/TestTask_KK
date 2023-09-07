package GoncharenkoSS.test.Services;


import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceErrors {
    public String returnErrors(BindingResult bindingResult){
        List<String> list = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(f -> list.add(f.getDefaultMessage()));

        return String.join("\n", list);
    }
}
