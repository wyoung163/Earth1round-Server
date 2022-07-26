package donggrami.earth1round.src.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
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
    @ColumnDefault("'ACTIVE'")
    private CourseStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date created_at;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updated_at;

    @Builder
    public Course(Long course_id, User user, Place start_place, Place end_place, double distance, Date start_date, Date end_date, CourseStatus status, Date created_at, Date updated_at) {
        this.course_id = course_id;
        this.user = user;
        this.start_place = start_place;
        this.end_place = end_place;
        this.distance = distance;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}
