package in.py.main.entity;

import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class CrossingLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	@ElementCollection(fetch = FetchType.LAZY) 
	@CollectionTable(
			name = "crossing_log_days", 
			joinColumns = @JoinColumn(name = "crossing_log_id")
	)
	@Column(name = "day_of_week") 
	private List<String> days;
	@Column
	private LocalTime closedFrom;
	@Column
	private LocalTime closedTo;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="crossing_id", nullable=false)
	private RailwayCrossing railwayCrossing;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="train_number",referencedColumnName = "train_number", nullable = false)
	private Train train;
	
	

}
