package com.ddabadi.service;


public interface CustomService<Cls, Pg >  {

    public Cls addnew(Cls cls);
    public Cls update(Cls cls, Long idOld);
}
