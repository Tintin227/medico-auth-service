package vn.uni.medico.auth.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@NoArgsConstructor
@Table(name = "staffs")
public class Staff extends EntityBase implements Serializable {


    public static final int PASSWORD_MAX_LENGTH1 = 200;
    public static final int RE_TYPE_PASSWORD_MAX_LENGTH1 = 200;

    public static final int NAME_MAX_LENGTH = 100;
    public static final int CODE_MAX_LENGTH = 200;
    public static final int EMAIL_MAX_LENGTH = 100;
    public static final int PHONE_NUMBER_MAX_LENGTH = 15;
    public static final int NOTE_MAX_LENGTH = 500;
    public static final int USER_NAME_MAX_LENGTH = 255;


    public static final int PASSWORD_MIN_LENGTH = 6;
    public static final int RE_TYPE_PASSWORD_MIN_LENGTH = 6;
    public static final int PASSWORD_MAX_LENGTH = 50;
    public static final int RE_TYPE_PASSWORD_MAX_LENGTH = 50;

    private static final long serialVersionUID = -1L;

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

    @Override
    public void updateData() {

    }
}
