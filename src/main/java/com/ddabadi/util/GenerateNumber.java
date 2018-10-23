package com.ddabadi.util;

import com.ddabadi.model.PaymentHd;
import com.ddabadi.model.Registration;
import com.ddabadi.model.Student;
import com.ddabadi.model.Teacher;
import com.ddabadi.repository.PaymentHdRepository;
import com.ddabadi.repository.RegistrationRepository;
import com.ddabadi.repository.StudentRepository;
import com.ddabadi.repository.TeacherRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static com.ddabadi.config.BaseConstant.PAYMENT_HEADER_EXTENTION;

@Service
public class GenerateNumber {

    private static Logger logger = LoggerFactory.getLogger(GenerateNumber.class);
    @Autowired private TeacherRepository teacherRepository;
    @Autowired private StudentRepository studentRepository;
    @Autowired private RegistrationRepository registrationRepository;
    @Autowired private PaymentHdRepository paymentHdRepository;

    public String randomize(){
        String random = UUID.randomUUID().toString();
        return random.substring(0, 8);
    }

    public String generateTeacherCode(String nama){
        String newCode;
        Sort sort = Sort.by(Sort.Direction.DESC, "teacherCode");
        PageRequest pageRequest = PageRequest.of(0,1, sort);
        logger.info("substring dari nama===============>{}" ,nama.substring(0, 1));
        Page<Teacher> teacherPage = teacherRepository.findByKarakter(nama.substring(0, 1).toUpperCase(), pageRequest);
        if (teacherPage.hasContent() ){
            Teacher teacher = teacherPage.iterator().next();
            Integer curNumb = Integer.valueOf(teacher.getTeacherCode().substring(1,5));
            logger.info("next numb bef ===============>{}" ,curNumb);
            curNumb++;
            logger.info("next numb after ===============>{}" ,curNumb);
            Integer curNumbLen = curNumb.toString().length();
            newCode = nama.substring(0, 1).toUpperCase() + ("0000" + curNumb).substring(curNumbLen, curNumbLen+4);
            logger.info("new code ===============>{}" ,newCode);
        } else {
            newCode = nama.substring(0, 1).toUpperCase() +"0001";
        }
        return newCode;
    }

    public String generateStudentCode(String name) {
        String newCode;
        SimpleDateFormat sdf = new SimpleDateFormat("yyMM");
        String tahunBulan = sdf.format(new Date());
        Sort sort = Sort.by(Sort.Direction.DESC, "studentCode");
        PageRequest pageRequest = PageRequest.of(0,1, sort);
        Page<Student> studentPage = studentRepository.findByTahunBulan(tahunBulan, pageRequest);
        if (studentPage.hasContent()){
            Student student = studentPage.iterator().next();
            Integer curNumb = Integer.valueOf(student.getStudentCode().substring(4,8)) +1;
            Integer curNumbLen = curNumb.toString().length();
            newCode = tahunBulan + ("0000" + curNumb).substring(curNumbLen, curNumbLen+4);
        } else {
            newCode = tahunBulan +"0001";
        }
        return newCode;
    }

    public String GenerateRegistrationNumber() {
        String newCode;
        SimpleDateFormat sdf = new SimpleDateFormat("yy");
        String tahun = sdf.format(new Date());
        Sort sort = Sort.by(Sort.Direction.DESC, "registrationNum");
        PageRequest pageRequest = PageRequest.of(0,1, sort);
        Page<Registration> registrationPage = registrationRepository.findByTahun(tahun, pageRequest);
        if (registrationPage.hasContent()){
            Registration registration = registrationPage.iterator().next();
            Integer curNumb = Integer.valueOf(registration.getRegistrationNum().substring(3,6)) +1;
            Integer curNumbLen = curNumb.toString().length();
            newCode = tahun + ("0000" + curNumb).substring(curNumbLen, curNumbLen+4);
        } else {
            newCode = tahun +"0001";
        }
        return newCode;
    }

    public String generatePaymentNumber() {
        String newCode;
        SimpleDateFormat sdf = new SimpleDateFormat("yyMM");
        String tahunBulan = sdf.format(new Date());
        Sort sort = Sort.by(Sort.Direction.DESC, "paymentNumber");
        PageRequest pageRequest = PageRequest.of(0,1, sort);
        Page<PaymentHd> paymentHdPage = paymentHdRepository.findByTahunBulan(tahunBulan, pageRequest);
        if (paymentHdPage.hasContent()){
            PaymentHd paymentHd = paymentHdPage.iterator().next();
            Integer curNumb = Integer.valueOf(paymentHd.getPaymentNumber().substring(5,9)) +1;
            Integer curNumbLen = curNumb.toString().length();
            newCode = PAYMENT_HEADER_EXTENTION + tahunBulan + ("0000" + curNumb).substring(curNumbLen, curNumbLen+4);
        } else {
            newCode = PAYMENT_HEADER_EXTENTION + tahunBulan +"0001";
        }
        return newCode;
    }
}
