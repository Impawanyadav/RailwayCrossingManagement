package in.py.main.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table

@Data
public class RailwayCrossing {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	@Column
	private String address;
	@Column
	private Long pincode;
	@Column
	private String status;
	@OneToMany(mappedBy = "railwayCrossing", cascade = CascadeType.ALL, orphanRemoval =true, fetch = FetchType.LAZY)
	
	private List<CrossingLog> crossingLog;
	
	@OneToMany(mappedBy = "railwayCrossing", cascade = CascadeType.ALL, orphanRemoval =true, fetch = FetchType.LAZY)
	private List<DutyAssign> dutyAssign;
	@OneToMany(mappedBy ="railwayCrossing", cascade = CascadeType.ALL, orphanRemoval =true, fetch = FetchType.LAZY )
	private List<DailyCrossingLogsRealTime> dailyCrossingLogsRealTimes;
	
     
}
