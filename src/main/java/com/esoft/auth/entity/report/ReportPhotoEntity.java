package com.esoft.auth.entity.report;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "report_photo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(
        callSuper = true,
        exclude = {"reportEntity"})
@EqualsAndHashCode(of = "id", callSuper = false)
public class ReportPhotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

 //   @Column(name = "photo_name")
    private String photoName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "report_id")
    private ReportEntity reportEntity;

}
