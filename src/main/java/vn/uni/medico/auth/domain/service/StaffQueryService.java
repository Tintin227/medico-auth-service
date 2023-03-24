package vn.uni.medico.auth.domain.service;

public interface StaffQueryService {
    boolean checkUserByMailOrPhone(String userName,String email,String phone);
}
