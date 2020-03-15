package pe.com.empresa.poc.azure.core.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class AuditEntity<U> {
    @CreatedBy
    @Column(name = "CREATE_USER", updatable = false)
    protected U createdBy;

    @CreatedDate
    @Column(name = "CREATE_DATE", updatable = false)
    protected LocalDateTime createdDate;

    @LastModifiedBy
    @Column(name = "UPDATE_USER")
    protected U modifiedBy;

    @LastModifiedDate
    @Column(name = "UPDATE_DATE")
    protected LocalDateTime modifiedDate;
}
