package com.ddabadi.service.impl.trans;

import com.ddabadi.model.*;
import com.ddabadi.model.dto.PaymentDtDto;
import com.ddabadi.model.enu.PaymentType;
import com.ddabadi.repository.PaymentDtRepository;
import com.ddabadi.service.CustomService;
import com.ddabadi.service.impl.ErrCodeServiceImpl;
import com.ddabadi.service.impl.UserServiceImpl;
import com.ddabadi.service.impl.master.OutletService;
import com.ddabadi.service.impl.master.StudentDtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.ddabadi.config.BaseConstant.*;
import static com.ddabadi.config.BaseErrorCode.ERR_CODE_ID_NOT_FOUND;
import static com.ddabadi.config.BaseErrorCode.ERR_CODE_PAYMENT_FAIL;
import static com.ddabadi.config.BaseErrorCode.ERR_CODE_SUCCESS;

@Service
public class PaymentDtService implements CustomService<PaymentDtDto> {

    @Autowired private PaymentDtRepository repository;
    @Autowired private UserServiceImpl userService;
    @Autowired private ErrCodeServiceImpl errorCodeService;
    @Autowired private RegistrationService registrationService;
    @Autowired private StudentDtService studentDtService;
    @Autowired private PaymentHdService paymentHdService;
    @Autowired private OutletService outletService;

    @Override
    @Transactional
    public PaymentDtDto addnew(PaymentDtDto paymentDtDto) {

        Optional<PaymentHd> optionalPayment = paymentHdService.findById(paymentDtDto.getPaymentHdId());
        if (!optionalPayment.isPresent()){
            paymentDtDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
            paymentDtDto.setErrDesc("Payment HD ["+paymentDtDto.getPaymentHdId()+"] "+errorCodeService.findByCode(ERR_CODE_ID_NOT_FOUND).getDescription());
            return paymentDtDto;
        }
        User user = userService.getCurUserAsObj();
        String paymentDesc = null;
        BigDecimal amount = BigDecimal.ZERO;

        Registration registration = null;
        List<String> months = new ArrayList<>();

        switch (paymentDtDto.getPaymentType()){
            case MONTHLY_FREE_REG:
                paymentDesc = PAYMENT_DESC_FREE_REGISTRATION;
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date dt1 = sdf.parse(paymentDtDto.getDateStartFreeReg());
                    for (int i = 0; i<= 6; i++) {
                        SimpleDateFormat monthFmt = new SimpleDateFormat("yyyyMM");
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(dt1);
                        cal.add(Calendar.MONTH,i);
                        String month = monthFmt.format(cal.getTime());
                        months.add(month);
                    }
                    Optional<StudentDt> optionalDtStudent= studentDtService.findByIdStudent(optionalPayment.get().getStudent().getId());
                    if (!optionalDtStudent.isPresent()){
                        paymentDtDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
                        paymentDtDto.setErrDesc("Student / classes "+errorCodeService.findByCode(ERR_CODE_PAYMENT_FAIL).getDescription());
                        return paymentDtDto;
                    }
                    amount = optionalDtStudent.get().getClasses().getMonthlyFee();
                } catch (ParseException e) {
                    paymentDtDto.setErrCode(ERR_CODE_PAYMENT_FAIL);
                    paymentDtDto.setErrDesc("Invalid date free reg ["+paymentDtDto.getPaymentHdId()+"] "+errorCodeService.findByCode(ERR_CODE_ID_NOT_FOUND).getDescription());
                    return paymentDtDto;
                }
//                paymentDesc = PAYMENT_DESC_MONTHLY;
                break;
            case REGISTRATION:
                Optional<Registration> optionalRegistration = registrationService.findById(paymentDtDto.getRegistrationId());
                if (!optionalRegistration.isPresent()){
                    paymentDtDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
                    paymentDtDto.setErrDesc("Registration ["+paymentDtDto.getRegistrationId()+"] "+errorCodeService.findByCode(ERR_CODE_ID_NOT_FOUND).getDescription());
                    return paymentDtDto;
                }
                registration = optionalRegistration.get();
                amount = outletService.findById(user.getOutletId()).get().getRegistrationFee();
                paymentDesc = PAYMENT_DESC_REGISTRATION;
                break;
            case MONTHLY:
                Optional<StudentDt> optionalDtStudent= studentDtService.findByIdStudent(optionalPayment.get().getStudent().getId());
                if (!optionalDtStudent.isPresent()){
                    paymentDtDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
                    paymentDtDto.setErrDesc("Student / classes "+errorCodeService.findByCode(ERR_CODE_PAYMENT_FAIL).getDescription());
                    return paymentDtDto;
                }
                amount = optionalDtStudent.get().getClasses().getMonthlyFee();
                paymentDesc = PAYMENT_DESC_MONTHLY;
                break;
        }

        paymentDtDto.setRegistration(registration);
        paymentDtDto.setPaymentHd(optionalPayment.get());
        PaymentDt newRec = null;
        if (paymentDtDto.getPaymentType()== PaymentType.MONTHLY_FREE_REG){
            for (int i =0; i<=6; i++ ){
                paymentDtDto.setPaymentMonth(months.get(i));
                if (i==6){
                    paymentDtDto.setDescription(PAYMENT_DESC_FREE_REGISTRATION);
                    newRec = this.addPaymentDt(paymentDtDto, BigDecimal.ZERO, paymentDesc , user);
                } else {
                    paymentDtDto.setDescription(PAYMENT_DESC_MONTHLY_REGISTRATION);
                    newRec = this.addPaymentDt(paymentDtDto, amount, paymentDesc , user);
                }
                newRec = repository.save(newRec);
            }
        } else {
            newRec = this.addPaymentDt(paymentDtDto, amount, paymentDesc, user);
            newRec = repository.save(newRec);
        }
        paymentDtDto.setId(newRec.getId());
        paymentDtDto.setErrCode(ERR_CODE_SUCCESS);
        paymentDtDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_SUCCESS).getDescription());

        return paymentDtDto;
    }

    PaymentDt addPaymentDt(PaymentDtDto paymentDtDto, BigDecimal amount, String desc, User user){

        PaymentDt newRec = new PaymentDt();
        newRec.setAmount(amount);
        newRec.setDescription(desc);
        newRec.setPaymentMonth(paymentDtDto.getPaymentMonth());
        newRec.setRegistration(paymentDtDto.getRegistration());
        newRec.setPaymentHd(paymentDtDto.getPaymentHd());
        newRec.setPaymentType(paymentDtDto.getPaymentType());
        newRec.setUpdatedBy(user);
        newRec.setCreatedBy(user);
        return newRec;
    }

    @Override
    public PaymentDtDto update(PaymentDtDto paymentDtDto, Long idOld) {
        return null;
    }

    @Override
    public PaymentDtDto update(PaymentDtDto paymentDtDto) {
        return null;
    }
}
