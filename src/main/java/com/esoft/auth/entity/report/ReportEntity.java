package com.esoft.auth.entity.report;

import com.esoft.auth.entity.user.UserEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.List;

@Entity()
@Table(name = "report")
@Data
public class ReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String content;

    private Integer type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @OneToMany(mappedBy = "reportEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportPhotoEntity> reportPhotos;

}
