package vn.uni.medico.middleware.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @team unikom.vn
 * @Date 2022-08-19 11:38:48
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "sms")
public class Sms implements Serializable {

    private static final long serialVersionUID = 1948537045807728608L;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "message")
    private String message;

    @Column(name = "source")
    private String source;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "error")
    private String error;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;
}
