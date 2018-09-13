package com.ddabadi.service.impl.trans.master;

import com.ddabadi.model.Outlet;
import com.ddabadi.model.dto.OutletDto;
import com.ddabadi.repository.OutletRepository;
import com.ddabadi.service.CustomService;
import com.ddabadi.util.SmStockUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.ddabadi.config.BaseErrorCode.*;

@Service
public class OutletService implements CustomService<OutletDto> {

    @Autowired private OutletRepository repository;

    @Override
    public OutletDto addnew(OutletDto outletDto) {

        Outlet outletNew = repository.save(outletDto.getOutlet());
        outletDto.getOutlet().setId(outletNew.getId());
        outletDto.setErrCode(ERR_CODE_SUCCESS);
        outletDto.setErrDesc(ERR_MSG_SUCCESS);
        return outletDto;
    }

    @Override
    public OutletDto update(OutletDto outlet, Long idOld) { return null; }

    @Override
    public OutletDto update(OutletDto outletDto) {
        Optional<Outlet> updateDataOpt = repository.findById(outletDto.getOutlet().getId());
        if (updateDataOpt.isPresent()){
            Outlet updateOutlet = updateDataOpt.get();
//            Copying properties from fromBean to toBean
//            BeanUtils.copyProperties(source, target);
            SmStockUtil.copyBean(outletDto.getOutlet(), updateDataOpt);
            repository.save(updateOutlet);
            outletDto.setErrCode(ERR_CODE_SUCCESS);
            outletDto.setErrDesc(ERR_MSG_SUCCESS);
        } else {
            outletDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
            outletDto.setErrDesc(ERR_MSG_ID_NOT_FOUND);
        }

        return outletDto;
    }

    public OutletDto findAll(Integer page, Integer count) {

        Sort sort = Sort.by("id");
        PageRequest pageRequest = PageRequest.of(page -1 , count, sort);
        OutletDto result = new OutletDto();
        result.setErrCode(ERR_CODE_SUCCESS);
        result.setErrDesc(ERR_MSG_SUCCESS);
        result.setResultPage(repository.findAll(pageRequest));
        return result;
    }
}
