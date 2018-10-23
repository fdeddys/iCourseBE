package com.ddabadi.service.impl.trans;

import com.ddabadi.model.AttendanceHd;
import com.ddabadi.model.PaymentHd;
import com.ddabadi.model.Student;
import com.ddabadi.model.User;
import com.ddabadi.model.dto.PaymentHdDto;
import com.ddabadi.model.enu.PaymentStatus;
import com.ddabadi.model.enu.PaymentType;
import com.ddabadi.repository.PaymentHdRepository;
import com.ddabadi.service.CustomService;
import com.ddabadi.service.impl.ErrCodeServiceImpl;
import com.ddabadi.service.impl.UserServiceImpl;
import com.ddabadi.service.impl.master.OutletService;
import com.ddabadi.service.impl.master.StudentService;
import com.ddabadi.util.GenerateNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static com.ddabadi.config.BaseErrorCode.ERR_CODE_ID_NOT_FOUND;
import static com.ddabadi.config.BaseErrorCode.ERR_CODE_SUCCESS;

@Service
public class PaymentHdService implements CustomService<PaymentHdDto> {

    @Autowired private PaymentHdRepository repository;
    @Autowired private PaymentDtService paymentDtService;
    @Autowired private StudentService studentService;
    @Autowired private ErrCodeServiceImpl errorCodeService;
    @Autowired private UserServiceImpl userService;
    @Autowired private GenerateNumber generator;


    @Override
    @Transactional
    public PaymentHdDto addnew(PaymentHdDto paymentHdDto) {

        Optional<Student> studentOpt = studentService.findById(paymentHdDto.getStudentId());
        if (!studentOpt.isPresent()){
            paymentHdDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
            paymentHdDto.setErrDesc("Student ["+paymentHdDto.getStudentId()+"] "+errorCodeService.findByCode(ERR_CODE_ID_NOT_FOUND).getDescription());
            return paymentHdDto;
        }

        User user = userService.getCurUserAsObj();

        paymentHdDto.setId(null);
        PaymentHd newRec = new PaymentHd();
        newRec.setStudent(studentOpt.get());
        newRec.setDescription(paymentHdDto.getDescription());
        newRec.setPaymentDate(new Date());
        newRec.setPaymentStatus(PaymentStatus.OUTSTANDING);
        newRec.setOutlet( userService.getCurOutlet());
        newRec.setUpdatedBy(user);
        newRec.setCreatedBy(user);
        newRec = repository.save(newRec);

        BigDecimal total = BigDecimal.ZERO;
//        paymentHdDto.getPaymentDtDtoList().forEach(
//            paymentDtDto -> {
//                paymentDtService.addnew(paymentDtDto);
//
////                    paymentDtDto.setPaymentHd(newRec);
////                    switch (paymentDtDto.getPaymentType()){
////                        case REGISTRATION:
////                            paymentDtDto = paymentDtService.addNew()
////                            break;
////                        case MONTHLY:
////                            break;
////                        case MONTHLY_FREE_REG:
////                            break;
////                    }
////                    total = total + paymentDtDto.getAmount();
//                addTotal(total, paymentDtDto.getAmount());
//            }
//        );

        paymentHdDto.setStudent(studentOpt.get());
        paymentHdDto.setId(newRec.getId());
        paymentHdDto.setErrCode(ERR_CODE_SUCCESS);
        paymentHdDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_SUCCESS).getDescription());
        return paymentHdDto;
    }

    BigDecimal addTotal(BigDecimal adder, BigDecimal total){
        return total.add(adder);
    }


    @Transactional
    public PaymentHdDto changeStatus(PaymentHdDto paymentHdDto, PaymentStatus paymentStatus) {
        Optional<PaymentHd> paymentHdOptional = repository.findById(paymentHdDto.getId());
        if (!paymentHdOptional.isPresent()){
            paymentHdDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
            paymentHdDto.setErrDesc("Payment ["+paymentHdDto.getStudentId()+"] "+errorCodeService.findByCode(ERR_CODE_ID_NOT_FOUND).getDescription());
            return paymentHdDto;
        }

        User user = userService.getCurUserAsObj();
//        paymentHdDto.setId(null);
//        PaymentHd newRec = new PaymentHd();
        PaymentHd newRec = paymentHdOptional.get();
        if ( paymentStatus==(PaymentStatus.APPROVED)) {
            newRec.setPaymentDate(new Date());
            newRec.setPaymentNumber(generator.generatePaymentNumber());
        }
        newRec.setPaymentStatus(paymentStatus);
        newRec.setUpdatedBy(user);
        newRec.setCreatedBy(user);
        newRec = repository.save(newRec);

        paymentHdDto.setPaymentDate(newRec.getPaymentDate());
        paymentHdDto.setPaymentNumber(newRec.getPaymentNumber());
        paymentHdDto.setId(newRec.getId());
        paymentHdDto.setPaymentStatus(newRec.getPaymentStatus());
        paymentHdDto.setErrCode(ERR_CODE_SUCCESS);
        paymentHdDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_SUCCESS).getDescription());
        return paymentHdDto;
    }

    @Override
    public PaymentHdDto update(PaymentHdDto paymentHdDto, Long idOld) {
        return null;
    }

    @Override
    public PaymentHdDto update(PaymentHdDto paymentHdDto) {

        Optional<Student> studentOpt = studentService.findById(paymentHdDto.getStudentId());
        if (!studentOpt.isPresent()) {
            paymentHdDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
            paymentHdDto.setErrDesc("Student [" + paymentHdDto.getStudentId() + "] " + errorCodeService.findByCode(ERR_CODE_ID_NOT_FOUND).getDescription());
            return paymentHdDto;
        }

        Optional<PaymentHd> paymentHdOptional = repository.findById(paymentHdDto.getId());
        if (!paymentHdOptional.isPresent()) {
            paymentHdDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
            paymentHdDto.setErrDesc("Payment [" + paymentHdDto.getId() + "] " + errorCodeService.findByCode(ERR_CODE_ID_NOT_FOUND).getDescription());
            return paymentHdDto;
        }

        User user = userService.getCurUserAsObj();
        Optional<PaymentHd> rec = repository.findById(paymentHdDto.getId());
        if (rec.isPresent()) {
            PaymentHd newRec = rec.get();
            newRec.setStudent(studentOpt.get());
            newRec.setDescription(paymentHdDto.getDescription());
            newRec.setUpdatedBy(user);
            newRec.setCreatedBy(user);
            newRec = repository.save(newRec);

            paymentHdDto.setErrCode(ERR_CODE_SUCCESS);
            paymentHdDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_SUCCESS).getDescription());
        }
        else {
            paymentHdDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
            paymentHdDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_ID_NOT_FOUND).getDescription());
        }
        return paymentHdDto;
    }

    public Optional<PaymentHd> findById(String registrationId) {
        return repository.findById(registrationId);
    }
}
