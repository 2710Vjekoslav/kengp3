package org.example.persistence.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "currency", uniqueConstraints = @UniqueConstraint(columnNames = {"code", "locale"}))
@EntityListeners(EntityListeners.class)
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 32)
    private String code;

    @Column(nullable = false, length = 16)
    private String locale;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(name = "create_by", nullable = false, length = 255, updatable = false)
    private String createBy;

    @Column(name = "update_by", nullable = false, length = 255)
    private String updateBy;

    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;

    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createTime = now;
        this.updateTime = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updateTime = LocalDateTime.now();
    }
}
