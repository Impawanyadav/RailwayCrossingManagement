package in.py.main.entity;

import java.security.KeyStore.PrivateKeyEntry;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Table
@Entity
@Data
public class Train {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@Column(name="train_number", unique=true, nullable=false)
	private int trainNumber;
	@Column(unique = true)
	private String trainName;
	
	@OneToMany(mappedBy = "train",  cascade = CascadeType.ALL, orphanRemoval =true, fetch = FetchType.LAZY)
	private List<CrossingLog> crossingLogs;
	@OneToMany(mappedBy ="train", cascade = CascadeType.ALL, orphanRemoval =true, fetch = FetchType.LAZY )
	private List<DailyCrossingLogsRealTime> dailyCrossingLogsRealTimes;

}
