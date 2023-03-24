package vn.uni.medico.auth.domain.entity.booking;

import lombok.AllArgsConstructor;
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
import vn.uni.medico.shared.config.TenantContext;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "customers")
@EntityListeners({AuditingEntityListener.class})
@Filters({
        @Filter(name = "tenantFilter", condition = "tenant_id = :tenantId and is_deleted = 0 "),
})
@FilterDefs({
        @FilterDef(name = "tenantFilter", parameters = {@ParamDef(name = "tenantId", type = "long")})
})
public class Customers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 36, nullable = false, updatable = false)
    protected Long id;

    @CreatedBy
    @Column(name = "created_by")
    protected String createdBy;

    @CreatedDate
    @Column(name = "created_at")
    protected LocalDateTime createdAt;

    @Column(name = "updated_by")
    @LastModifiedBy
    protected String updatedBy;

    @Column(name = "updated_at")
    @LastModifiedDate
    protected LocalDateTime updatedAt;

    @Column(name = "is_deleted")
    protected int isDeleted;

    @Column(name = "tenant_id")
    private Long tenantId;

    @Column(name = "status")
    private Integer status;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "email")
    private String email;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "customer_code")
    private String customerCode;

    @Column(name = "note")
    private String note;

    @Column(name = "customer_image")
    private String customerImage;

    @Column(name = "province_id")
    private Long provinceId;

    @Column(name = "district_id")
    private Long districtId;

    @Column(name = "ward_id")
    private Long wardId;

    @PreUpdate
    @PreRemove
    @PrePersist
    public void setTenant() {
        final Long tenantId = TenantContext.getCurrentTenant();
        this.tenantId = tenantId;

    }
}
