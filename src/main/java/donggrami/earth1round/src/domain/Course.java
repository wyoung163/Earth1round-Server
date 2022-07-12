package donggrami.earth1round.src.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "Course")
@Getter
@Setter
@DynamicInsert
public class Course {
    public enum CourseStatus {
        ACTIVE, INACTIVE, COMPLETE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long course_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "start_place_id")
    private Place start_place;

    @ManyToOne
    @JoinColumn(name = "end_place_id")
    private Place end_place;

    @Column(nullable = false)
    private double distance;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date start_date;

    @Temporal(TemporalType.TIMESTAMP)
    private Date end_date;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("ACTIVE")
    private CourseStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date created_at;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updated_at;
}
