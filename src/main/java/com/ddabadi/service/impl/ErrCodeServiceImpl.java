package com.ddabadi.service.impl;


import com.ddabadi.model.ErrCode;
import com.ddabadi.repository.ErrorCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ErrCodeServiceImpl {


    @Autowired private ErrorCodeRepository repository;

    @Transactional
    public ErrCode save(ErrCode errCode){
        return repository.save(errCode);
    }

    @Transactional(readOnly = true)
    public ErrCode findByCode(String errCode){
        return repository.findByCode(errCode);
    }

}
