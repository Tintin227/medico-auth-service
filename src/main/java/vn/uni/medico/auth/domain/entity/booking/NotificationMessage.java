package vn.uni.medico.auth.domain.entity.booking;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@NoArgsConstructor
@Table(name = "notification_messages")
public class NotificationMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 36, nullable = false, updatable = false)
    private Long id;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_by")
    @LastModifiedBy
    private String updatedBy;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted")
    private int isDeleted;

    @Column(name = "tenant_id")
    private Long tenantId;

    @Column(name = "status")
    private Integer status;

    @Column(name = "message")
    private String message;

    @Column(name = "receiver_address")
    private String receiverAddress;

    @Column(name = "error_log")
    private String errorLog;

    @Column(name = "retry_count")
    private Integer retryCount;

    @Column(name = "config_id")
    private Long configId;

    @Column(name = "noti_type")
    private Integer notiType;

    @Column(name = "channel")
    private Integer channel;

    @Column(name = "object_id")
    private Integer objectId;
}
