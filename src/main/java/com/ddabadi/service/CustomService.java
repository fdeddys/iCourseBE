package com.ddabadi.service;


public interface CustomService<Cls >  {

    public Cls addnew(Cls cls);
    public Cls update(Cls cls, Long idOld);
    public Cls update(Cls cls);
}
