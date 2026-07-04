package in.py.main.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import in.py.main.entity.*;
import in.py.main.repository.*;
import lombok.AllArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepo;
    private final TrainRepository trainRepo;
    private final RailwayCrossingRepository crossingRepo;
    private final CrossingLogRepository crossingLogRepo; 
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Only seed data if database is empty
        if (userRepo.count() > 0) return;

        // 1. Create Admin (Gets ID = 1)
        User admin = new User();
        admin.setName("System Admin");
        admin.setEmail("admin@railway.com");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRole("ROLE_ADMIN");
        admin.setStatus("ACTIVE");
        userRepo.save(admin);

        // 2. Create 50 Gatemen with Real Names
        List<String> shifts = Arrays.asList("MORNING", "EVENING", "NIGHT");
        
        String[] realIndianNames = {
            "Ramesh Kumar", "Suresh Singh", "Anil Gupta", "Manoj Tiwari", "Rajesh Yadav", 
            "Sunil Mishra", "Vikas Chauhan", "Sanjay Patel", "Amit Sharma", "Rahul Verma",
            "Deepak Reddy", "Ajay Joshi", "Vijay Kumar", "Dinesh Singh", "Mahesh Gupta", 
            "Ashok Tiwari", "Ravi Yadav", "Prakash Mishra", "Santosh Chauhan", "Ganesh Patel",
            "Pramod Sharma", "Arvind Verma", "Rajendra Reddy", "Shiv Joshi", "Kailash Kumar", 
            "Mohan Singh", "Mukesh Gupta", "Naveen Tiwari", "Pankaj Yadav", "Pramod Mishra",
            "Praveen Chauhan", "Rajeev Patel", "Rakesh Sharma", "Ramesh Verma", "Rohit Reddy", 
            "Sachin Joshi", "Sameer Kumar", "Sandeep Singh", "Sanjay Gupta", "Santosh Tiwari",
            "Satish Yadav", "Saurabh Mishra", "Shivam Chauhan", "Siddharth Patel", "Sudhir Sharma", 
            "Sumeet Verma", "Sunil Reddy", "Suraj Joshi", "Sushil Kumar", "Tarun Singh"
        };

        // Starts at i=2 so Gateman gets ID 2
        for (int i = 2; i <= 51; i++) {
            User gateman = new User();
            gateman.setName(realIndianNames[i - 2]); 
            gateman.setEmail("gateman" + i + "@railway.com");
            gateman.setPassword(passwordEncoder.encode(String.valueOf(i)));
            gateman.setRole("ROLE_GATEMAN");
            gateman.setStatus("ACTIVE");
            gateman.setCurrShift(shifts.get(i % shifts.size()));
            
            userRepo.save(gateman);
        }

        // 3. Create Real Trains passing through LKO (Lucknow)
        Object[][] realTrainsData = {
            {12229, "Lucknow Mail"},
            {12004, "LKO Swarna Shatabdi Exp"},
            {12533, "Pushpak Express"},
            {14205, "Ayodhya Express"},
            {12419, "Gomti Express"},
            {13005, "Howrah Amritsar Mail"},
            {14207, "Padmavat Express"},
            {12231, "LKO Chandigarh Express"},
            {12583, "Lucknow Anand Vihar Double Decker"},
            {15015, "Gorakhpur Yashvantpur Exp"},
            {14215, "Ganga Gomti Express"},
            {14257, "Kashi Vishwanath Exp"},
            {12233, "Mahamana Express"},
            {14203, "Lucknow Intercity"},
            {19306, "Kamakhya Indore Exp"}
        };

        List<Train> savedTrains = new ArrayList<>();
        for (Object[] data : realTrainsData) {
            Train t = new Train();
            t.setTrainNumber((Integer) data[0]);
            t.setTrainName((String) data[1]);
            savedTrains.add(trainRepo.save(t));
        }

        // 4. Create Real Railway Crossings in LKO (Lucknow)
        String[] lkoCrossingsData = {
            "Malhaur Crossing, Lucknow",
            "Gomti Nagar Crossing, Lucknow",
            "Daliganj Crossing, Lucknow",
            "Alamnagar Crossing, Lucknow",
            "Uthretia Crossing, Lucknow",
            "Dilkusha Crossing, Lucknow",
            "Aishbagh Crossing, Lucknow",
            "Manak Nagar Crossing, Lucknow",
            "Safedabad Crossing, Lucknow",
            "Jugaur Crossing, Lucknow"
        };

        List<RailwayCrossing> savedCrossings = new ArrayList<>();
        long pincodeStart = 226001L; 
        for (String address : lkoCrossingsData) {
            RailwayCrossing rc = new RailwayCrossing();
            rc.setAddress(address);
            rc.setPincode(pincodeStart++);
            rc.setStatus("ACTIVE");
            savedCrossings.add(crossingRepo.save(rc));
        }

        // 5. Create Exactly 8 Scheduled Logs for EVERY Crossing
        List<String> activeDays = Arrays.asList(
                "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"
        );
        
        // Loop through each of the 10 Crossings
        for (int c = 0; c < savedCrossings.size(); c++) {
            RailwayCrossing crossing = savedCrossings.get(c);
            
            // Start the first train schedule at 5:00 AM, slightly offset for each crossing
            LocalTime startTime = LocalTime.of(5, 0).plusMinutes(c * 5); 
            
            // Assign exactly 8 trains to this crossing
            for (int l = 0; l < 8; l++) {
                // Select a train dynamically from our list of 15 using modulo 
                // so we don't go out of bounds, ensuring a mix of trains at every crossing.
                Train train = savedTrains.get((c * 3 + l) % savedTrains.size());
                
                CrossingLog scheduleLog = new CrossingLog();
                scheduleLog.setTrain(train);
                scheduleLog.setRailwayCrossing(crossing);
                scheduleLog.setDays(activeDays);
                scheduleLog.setClosedFrom(startTime);
                scheduleLog.setClosedTo(startTime.plusMinutes(15)); // Gate closed for 15 mins
                
                crossingLogRepo.save(scheduleLog);
                
                // Add 2 hours and 10 minutes gap before the next scheduled train at this crossing
                startTime = startTime.plusHours(2).plusMinutes(10); 
            }
        }

        System.out.println("LKO Data Seeding Complete!");
    }
}