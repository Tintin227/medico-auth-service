package vn.uni.medico.auth.adapter.out.postgre.repo.admin;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import vn.uni.medico.auth.domain.entity.admin.TenantParam;


public interface TenantParamRepo extends JpaRepository<TenantParam, Long>, JpaSpecificationExecutor<TenantParam> {

    @Query("select t.parName from TenantParam t where t.tenantId = ?1 and t.parType = ?2 and t.parValue = ?3")
    String findParNameByTenantIdAndParTypeAndParValue(Long tenantId, String parType, String parValue);
}
