package ir.behzadnikbin.oauth2example.service.serviceresult;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/*
    used as the response to reject user inputs
 */

@NoArgsConstructor
@AllArgsConstructor
public class ValidationError {
    public String token;        //  user field which is the cause of rejection
    public String message;      //  rejection message
}
