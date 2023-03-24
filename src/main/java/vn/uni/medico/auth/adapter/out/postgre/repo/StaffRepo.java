package vn.uni.medico.auth.adapter.out.postgre.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.uni.medico.auth.domain.entity.Staff;

public interface StaffRepo extends JpaRepository<Staff, Long> {
    @Query(value="select * from medico_auth.staffs s where s.username =?1 and ( s.email=?2 or s.phone_number=?3) limit 1" ,
            nativeQuery = true)
    Staff findUserByMailOrPhone(String userName,String phone,String mail);
}
