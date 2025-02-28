package org.interview.vehicleregistration;

import org.interview.vehicleregistration.model.dto.requests.AuthenticationRequest;
import org.interview.vehicleregistration.model.dto.requests.UserRegistrationRequest;

public class TestUtils {

    public static AuthenticationRequest generateAuthRequest(String accId, String pass) {
        var auth = new AuthenticationRequest();
        auth.setAccountId(accId);
        auth.setPassword(pass);
        return auth;
    }

    public static UserRegistrationRequest generateUserRegistrationRequest(String accId, String fName, String lName) {
        var auth = new UserRegistrationRequest();
        auth.setAccountId(accId);
        auth.setFirstName(fName);
        auth.setLastName(lName);
        return auth;
    }
}