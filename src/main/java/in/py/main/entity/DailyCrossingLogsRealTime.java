package in.py.main.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table
@Data
public class DailyCrossingLogsRealTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	Long id;
	@ManyToOne
	@JoinColumn(name = "crossing_id", nullable = false)
	private RailwayCrossing railwayCrossing;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="train_number",referencedColumnName = "train_number", nullable = false)
	private Train train;
	@Column
	private LocalDate date;

    @Column
    private LocalTime closedFrom;

    @Column
    private LocalTime closedTo;

}
