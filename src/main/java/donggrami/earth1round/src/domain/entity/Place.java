package donggrami.earth1round.src.domain.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.sql.Date;
import java.sql.Timestamp;
import org.springframework.data.geo.Point; //Point


@Entity
@Table(name = "Place")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert // default 처리를 위해
public class Place {
    public enum PlaceStatus{
        ACTIVE, INACTIVE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 100, nullable = false)
    private Long place_id;

    @Column(nullable = false, name ="place_name")
    private String placeName;

//    @Column(nullable = false) // build.gradle 파일에 의존성 추가
//    private Point location; // POINT(경도,위도) : latitude + longitude

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'ACTIVE'")
    private PlaceStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private java.util.Date created_at;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private java.util.Date updated_at;

    @Builder
    public Place(Long place_id, String placeName, Double latitude, Double longitude,
                 PlaceStatus status, Date created_at, Date updated_at) {
        this.place_id = place_id;
        this.placeName = placeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}