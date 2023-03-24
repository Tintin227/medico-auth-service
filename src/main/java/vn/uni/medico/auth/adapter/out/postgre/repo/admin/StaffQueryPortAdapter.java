package vn.uni.medico.auth.adapter.out.postgre.repo.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import vn.uni.medico.auth.application.port.out.StaffQueryPort;
import vn.uni.medico.auth.domain.entity.admin.Staff;

@Repository
@RequiredArgsConstructor
public class StaffQueryPortAdapter implements StaffQueryPort {

    private final StaffRepo staffRepo;

    @Override
    public boolean checkUserByMailOrPhone(String userName,String email,String phone) {
        Staff staff = staffRepo.findUserByMailOrPhone(userName,email,phone);
        return staff != null;
    }
}
