package de.hm.edu.carads.controller;

import de.hm.edu.carads.models.User;
import de.hm.edu.carads.transaction.Credentials;

/**
 * Die Schnittstelle zur Authentifizierung.
 * @author FS, BK
 */
public interface RealmController{
    
	public void addUser(User user) throws Exception;
    public User getUser(String username) throws Exception;
    public User getUserById(String id) throws Exception;
    public void deleteUser(String id) throws Exception;
    public void changeCredentials(String id, Credentials credidentials) throws Exception;
    public void changeUsername(String id, String username) throws Exception;
    
}
