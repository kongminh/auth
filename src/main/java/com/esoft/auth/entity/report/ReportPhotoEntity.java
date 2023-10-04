package com.esoft.auth.entity.report;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity()
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

    private String photoName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "report_id")
    private ReportEntity reportEntity;

}
