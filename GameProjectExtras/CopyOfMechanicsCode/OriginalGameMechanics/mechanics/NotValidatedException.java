package edu.gmu.swe.gameproj.mechanics;


public class NotValidatedException extends Exception{
    public NotValidatedException(){};
    public NotValidatedException(String message)
    {
        super(message);
    }
}
