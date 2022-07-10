package donggrami.earth1round.src.domain;

import org.hibernate.annotations.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.sql.Timestamp;
import org.springframework.data.geo.Point; //Point


@Entity
@Table(name = "place")
@Getter
@Setter
public class Place {
    public enum place_status{
        active, inactive
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 100, nullable = false)
    private Long place_id;

    @Column(nullable = false)
    private String place_name;

    @Column(nullable = false) // build.gradle 파일에 의존성 추가
    private Point location; //POINT(경도,위도) : latitude + longitude

    @Enumerated(EnumType.STRING)
    @ColumnDefault("active")
    private place_status status;

    @Column(nullable = false)
    private Timestamp created_at;

    @Column(nullable = false)
    private Timestamp updated_at;

    @OneToMany
    @JoinColumn(name = "course_id")
    private Course course;

    // user 와의 관계 X
    // character 와의 관계 X 
}