package com.antoniopelusi;
public class Launcher
{
    //for jar packaging: set this launcher, not the one on App, otherwise the sdk will not recognize JavaFX because the one from main depend from Application, and cause this bug.
    public static void main(String[] args)
    {
        App.main(args);
    }
}