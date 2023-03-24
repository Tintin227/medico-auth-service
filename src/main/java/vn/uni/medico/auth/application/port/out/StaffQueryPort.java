package vn.uni.medico.auth.application.port.out;

public interface StaffQueryPort {
    boolean checkUserByMailOrPhone(String userName,String email,String phone);
}
