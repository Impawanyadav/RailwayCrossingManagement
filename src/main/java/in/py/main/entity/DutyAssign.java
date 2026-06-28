package in.py.main.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Table
@Entity
@Data
public class DutyAssign {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "crossing_id", nullable = false)
	private RailwayCrossing railwayCrossing;
	@ManyToOne
	@JoinColumn(name="employee_id", nullable = false)
	private User user;
	
	
	@Column
	private LocalDate dutyDate;
	
	@Column
	private String shift;
	
	@Column
	private String status;
	
	

}
