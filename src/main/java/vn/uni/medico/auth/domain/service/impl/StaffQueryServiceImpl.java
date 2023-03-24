package vn.uni.medico.auth.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.uni.medico.auth.adapter.in.rest.dto.OtpRequestDto;
import vn.uni.medico.auth.application.port.out.StaffQueryPort;
import vn.uni.medico.auth.domain.service.StaffQueryService;

@Service
@RequiredArgsConstructor
public class StaffQueryServiceImpl implements StaffQueryService {

    private final StaffQueryPort staffQueryPort;

    @Override
    public boolean checkUserByMailOrPhone(String userName,String email,String phone) {
        return staffQueryPort.checkUserByMailOrPhone(userName,email,phone);
    }
}
