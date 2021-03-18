package com.softlynx.bs.domain.usm.login;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.SessionAttributes;

@Component
@Scope("session")
@SessionAttributes("userPreferences")
public class UserPreferences {

}