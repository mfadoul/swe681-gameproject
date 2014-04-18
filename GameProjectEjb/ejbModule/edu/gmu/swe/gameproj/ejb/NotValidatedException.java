package edu.gmu.swe.gameproj.ejb;


public class NotValidatedException extends Exception{
    public NotValidatedException(){};
    public NotValidatedException(String message)
    {
        super(message);
    }
}
