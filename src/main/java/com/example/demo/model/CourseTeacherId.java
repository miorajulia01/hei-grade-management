package com.example.demo.model;

import java.io.Serializable;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CourseTeacherId implements Serializable {
  private String courseId;
  private String teacherId;
}
