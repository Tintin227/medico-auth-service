package vn.uni.medico.auth.domain.entity.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import vn.uni.medico.shared.config.TenantContext;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@NoArgsConstructor
@Table(name = "tenant_params")


@Filters({
        @Filter(name = "tenantFilter", condition = "tenant_id = :tenantId and is_deleted = 0 "),
})
@FilterDefs({
        @FilterDef(name = "tenantFilter", parameters = {@ParamDef(name = "tenantId", type = "long")}),
})
@EntityListeners({AuditingEntityListener.class})
public class TenantParam implements Serializable {

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

    @Column(name = "status", nullable = false)
    private Integer status = 1;

    @Column(name = "par_type", nullable = false)
    private String parType;

    @Column(name = "par_name")
    private String parName;


    @Column(name = "par_code")
    private String parCode;


    @Column(name = "par_value")
    private String parValue;


    @Column(name = "full_text_search")
    @JsonIgnore
    private String fullTextSearch;

    @Column(name = "note")
    private String note;

    @PreUpdate
    @PreRemove
    @PrePersist
    public void setTenant() {
        final Long tenantId = TenantContext.getCurrentTenant();
        if(ObjectUtils.isEmpty(this.tenantId)){
        this.tenantId = tenantId;}
    }
}
