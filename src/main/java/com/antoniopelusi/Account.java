package com.antoniopelusi;

/**
 * @author Antonio Pelusi
 *
 */
public class Account
{
    private String name;
    private String email;
    private String password;
    /**
     * @param name
     * @param email
     * @param password
     */
    public Account(String name, String email, String password)
    {
        super();
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString()
    {
        return "Account [name=" + name + ", email=" + email + ", password=" + password + "]\n";
    }

    public String getName()
    {
        return name;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }
}