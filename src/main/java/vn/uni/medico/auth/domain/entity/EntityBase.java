package vn.uni.medico.auth.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import vn.uni.medico.shared.config.TenantContext;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
//@Filter(name = "tenantFilter", condition = "tenant_id = :tenantId and is_deleted = 0")
//@FilterDef(name = "tenantFilter", parameters = {@ParamDef(name = "tenantId", type = "long")})

@Filters({
        @Filter(name = "tenantFilter", condition = "tenant_id = :tenantId and is_deleted = 0 "),
        @Filter(name = "branchFilter", condition = " branch_id = :branchId")
})
@FilterDefs({
        @FilterDef(name = "tenantFilter", parameters = {@ParamDef(name = "tenantId", type = "long")}),
        @FilterDef(name = "branchFilter", parameters = {@ParamDef(name = "branchId", type = "long")}),
})
@EntityListeners({AuditingEntityListener.class})
public abstract class EntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 36, nullable = false, updatable = false)
    protected Long id;

    @CreatedBy
    @NotNull
    @Column(name = "created_by", updatable = false, nullable = false)
    protected String createdBy;

    @CreatedDate
    @NotNull
    @Column(name = "created_at", updatable = false, nullable = false)
    protected LocalDateTime createdAt;

    @Column(name = "updated_by")
    @LastModifiedBy
    @NotNull
    protected String updatedBy;

    @Column(name = "updated_at")
    @LastModifiedDate
    protected LocalDateTime updatedAt;

    @Column(name = "is_deleted")
    @NotNull
    protected int isDeleted;

    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @Column(name = "branch_id", nullable = false)
    private Long branchId;

    @PreUpdate
    @PreRemove
    @PrePersist
    public void setTenant() {
        final Long tenantId = TenantContext.getCurrentTenant();
        this.tenantId = tenantId;

        final Long branchId = TenantContext.getCurrentBranch();
        this.branchId = branchId;


    }

    public abstract void updateData();

}
