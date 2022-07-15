package donggrami.earth1round.src.domain.entity;

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
@DynamicInsert // default 처리를 위해
public class Place {
    public enum PlaceStatus{
        ACTIVE, INACTIVE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 100, nullable = false)
    private Long place_id;

    @Column(nullable = false)
    private String place_name;

    @Column(nullable = false) // build.gradle 파일에 의존성 추가
    private Point location; // POINT(경도,위도) : latitude + longitude

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'ACTIVE'")
    private PlaceStatus status;

    @Column(nullable = false)
    private Date created_at;

    @Column(nullable = false)
    private Date updated_at;
}