package com.softlynx.bs.services.usm.login;

import java.util.Collection;
import java.util.HashMap;

import com.softlynx.bs.domain.usm.login.sc.LoginSC;

public interface LoginBO
{
    /**
     * Gets some of the User details like user code and user language given its user name.
     * @param empUsrLogin String of the userSC
     * @return Collection
     * @throws SoftSolException - DAO exception
     */
    Collection<HashMap<String,String>> getUserInfo(LoginSC criteria) throws Exception;
    
    /**
     * Returns the Number Formats assigned to a certain User. If user has no
     * predefined number formats, then the default Number Formats will be
     * returned.
     * @param empId String
     * @return HashMap
     * @throws SoftSolException - DAO exception
     */
    HashMap<String,String> getNbrFormatForUser(LoginSC criteria) throws Exception;
}