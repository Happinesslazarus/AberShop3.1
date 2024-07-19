package com.example.abershop.authenticate;

import com.example.abershop.core.EmployeeManager;
import com.example.abershop.core.Filters;
import com.example.abershop.core.PasswordHash;

import java.util.ArrayList;
import java.util.List;

public class PassEmail {
    private String passwordHash ;
    private  String email ;
    private int state;
    private EmployeeManager manager;
    private List<String> passwordHashes;
    private List<String> emails;

    public PassEmail(){
        passwordHashes = new ArrayList<>();
        emails = new ArrayList<>();
         manager = EmployeeManager.getInstance();
         manager.getEmployees().forEach(employee -> {
             passwordHashes.add(employee.getPass());
             emails.add(employee.getEmail());
         });

    }

    public void checkPassEmail(String password ,String email){
        this.passwordHash = PasswordHash.computeHash(password);

        if (!password.isEmpty() || !email.isEmpty()){
            emails.forEach(e -> {
               if( e.equals(email)){
                   passwordHashes.forEach(hash ->{
                       if (hash.equals(passwordHash)) {
                           state = 1;
                       }
                   });
               }
               else {
                   state = 2;
               }

            });
        }
        else {
            state = 0;
        }

    }

    public int getState() {
        return state;
    }

}
