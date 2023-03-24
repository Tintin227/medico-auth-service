package vn.uni.medico.auth.domain.entity.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@NoArgsConstructor
@Table(name = "staffs")
public class Staff implements Serializable {
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

    @Column(name = "status")
    private Integer status;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "full_text_search")
    @JsonIgnore
    private String fullTextSearch;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "position")
    private Integer position;

    @Column(name = "note")
    private String note;

    @Column(name = "is_doctor")
    private Integer isDoctor;

    @Column(name = "username")
    private String username;

    @Column(name = "gender")
    private Integer gender;

}
