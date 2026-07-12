// ==========================================
// UNIFIED DATA FETCHING LOGIC
// ==========================================

document.addEventListener("DOMContentLoaded", () => {
    // 1. Get the ID from the URL Path (e.g., /RailwayCrossing/crossing-detail/5)
    const pathParts = window.location.pathname.split('/');
    const crossingId = pathParts[pathParts.length - 1]; 

    // 2. Validate and trigger the fetch
    if (crossingId && !isNaN(crossingId)) {
        fetchCrossingDetails(crossingId); 
    } else {
        document.getElementById('box1-details').innerHTML = 
            "<h3 style='color:red;'>Error: No valid Crossing ID found in URL.</h3>";
    }
});

async function fetchCrossingDetails(crossingId) { 
    // Get the HTML containers
    const topBox = document.getElementById('box1-details');
    const scheduleBox = document.getElementById('box2-schedules');
    const todayLogBox = document.getElementById('box3-todayLogs');

    try {
        // Show loading text
        topBox.innerHTML = "<h3>Loading details...</h3>";
        scheduleBox.innerHTML = "<h3>Loading schedules...</h3>";
        todayLogBox.innerHTML = "<h3>Loading today's logs...</h3>";

        // 3. Fetch Data from endpoints using the path-based crossingId
        const [detailResponse, todayLogResponse] = await Promise.all([
            fetch(`/api/crossings/detail/${crossingId}`),
            fetch(`/api/crossings/todayLog/${crossingId}`)
        ]);

        if (!detailResponse.ok) throw new Error("Failed to fetch crossing details");
        if (!todayLogResponse.ok) throw new Error("Failed to fetch today's logs");

        const dashboardData = await detailResponse.json();
        const todayLogsData = await todayLogResponse.json();

        // ==========================================
        // BOX 1: Crossing Details & Active Duty
        // ==========================================
        const crossing = dashboardData.railwayCrossing;
        const duty = dashboardData.dutyAssign;
        
        let dutyHtml = `<p style="color: red; font-weight: bold;">No Gateman currently on duty.</p>`;
        if (duty && duty.user) {
            dutyHtml = `
                <div style="background-color: #e2f0f9; padding: 10px; border-radius: 5px; margin-top: 10px;">
                    <h4 style="margin: 0 0 5px 0;">Currently On Duty</h4>
                    <p style="margin: 0;"><strong>Name:</strong> ${duty.user.name || 'Unknown'}</p>
                    <p style="margin: 0;"><strong>Shift:</strong> ${duty.shift}</p>
                </div>
            `;
        }

        topBox.innerHTML = `
            <div style="border: 1px solid #ccc; padding: 20px; border-radius: 8px; background-color: #f9f9f9;">
                <h3 style="margin-top: 0; color: #1c7c8c;">Crossing Information</h3>
                <p><strong>ID:</strong> ${crossing.id}</p>
                <p><strong>Address:</strong> ${crossing.address}</p>
                <p><strong>Pincode:</strong> ${crossing.pincode}</p>
                <p><strong>Status:</strong> <span style="background-color: #d4edda; padding: 3px 8px; border-radius: 10px;">${crossing.status}</span></p>
                ${dutyHtml}
            </div>
        `;

        // ==========================================
        // BOX 2: Scheduled Crossing Logs
        // ==========================================
        const scheduledLogs = dashboardData.crossingLog;
        if (!scheduledLogs || scheduledLogs.length === 0) {
            scheduleBox.innerHTML = "<p style='color: #666; font-style: italic;'>No train schedules found for this crossing.</p>";
        } else {
            // Build the table structure and headers
            let schedulesHtml = `
                <div style="overflow-x: auto; background: white; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); border: 1px solid #ddd;">
                    <table style="width: 100%; border-collapse: collapse; font-family: Arial, sans-serif; text-align: left;">
                        <thead style="background-color: #2d2d2d; color: #c1c0c4;">
                            <tr>
                                <th style="padding: 15px; border-bottom: 1px solid #ddd; white-space: nowrap;">Closed From</th>
                                <th style="padding: 15px; border-bottom: 1px solid #ddd; white-space: nowrap;">Closed To</th>
                                <th style="padding: 15px; border-bottom: 1px solid #ddd;">Days</th>
                                <th style="padding: 15px; border-bottom: 1px solid #ddd; white-space: nowrap;">Train No.</th>
                                <th style="padding: 15px; border-bottom: 1px solid #ddd; white-space: nowrap;">Train Name</th>
                            </tr>
                        </thead>
                        <tbody>
            `;

            // Loop through the logs to create table rows
            scheduledLogs.forEach((log, index) => {
                // Handle nested train objects safely
                const tNum = log.train ? log.train.trainNumber : (log.trainNumber || 'N/A');
                const tName = log.train ? log.train.trainName : (log.trainName || 'Unknown');
                const daysStr = log.days ? log.days.join(', ') : 'All';
                
                // Zebra striping for rows
                const rowBg = index % 2 === 0 ? '#ffffff' : '#f9f9f9';

                schedulesHtml += `
                    <tr style="background-color: ${rowBg}; border-bottom: 1px solid #eee; transition: background-color 0.3s;" onmouseover="this.style.backgroundColor='#f1f1f1'" onmouseout="this.style.backgroundColor='${rowBg}'">
                        <td style="padding: 15px; font-weight: 500;">${log.closedFrom}</td>
                        <td style="padding: 15px; font-weight: 500;">${log.closedTo}</td>
                        <td style="padding: 15px; font-size: 0.9em; color: #555;">${daysStr}</td>
                        <td style="padding: 15px; font-weight: bold; color: #1c7c8c;">${tNum}</td>
                        <td style="padding: 15px; color: #333;">${tName}</td>
                    </tr>
                `;
            });

            schedulesHtml += `
                        </tbody>
                    </table>
                </div>
            `;
            scheduleBox.innerHTML = schedulesHtml;
        }

        // ==========================================
        // BOX 3: Today's Actual Logs
        // ==========================================
        if (!todayLogsData || todayLogsData.length === 0) {
            todayLogBox.innerHTML = "<p style='color: #666; font-style: italic;'>No completed crossings logged today.</p>";
        } else {
            let actualLogsHtml = `
                <div style="overflow-x: auto; background: white; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); border: 1px solid #ddd;">
                    <table style="width: 100%; border-collapse: collapse; font-family: Arial, sans-serif; text-align: left;">
                        <thead style="background-color: #2d2d2d; color: #c1c0c4;">
                            <tr>
                                <th style="padding: 15px; border-bottom: 1px solid #ddd; white-space: nowrap;">Train No.</th>
                                <th style="padding: 15px; border-bottom: 1px solid #ddd; white-space: nowrap;">Train Name</th>
                                <th style="padding: 15px; border-bottom: 1px solid #ddd; white-space: nowrap;">Gate Closed At</th>
                                <th style="padding: 15px; border-bottom: 1px solid #ddd; white-space: nowrap;">Gate Opened At</th>
                                <th style="padding: 15px; border-bottom: 1px solid #ddd; white-space: nowrap;">Status</th>
                            </tr>
                        </thead>
                        <tbody>
            `;

            todayLogsData.forEach((actual, index) => {
                // Safely handle nested train data
                const tNum = actual.train ? actual.train.trainNumber : (actual.trainNumber || 'N/A');
                const tName = actual.train ? actual.train.trainName : (actual.trainName || 'Unknown');
                
                // Because your DB query only returns logs where closedTo is NOT NULL, 
                // we know every record here is 100% completed.
                const statusBadge = `<span style="background-color: #d4edda; color: #155724; padding: 5px 10px; border-radius: 12px; font-size: 0.85em; font-weight: bold; box-shadow: 0 1px 2px rgba(0,0,0,0.05);">Completed</span>`;

                // Zebra striping
                const rowBg = index % 2 === 0 ? '#ffffff' : '#f9f9f9';

                actualLogsHtml += `
                    <tr style="background-color: ${rowBg}; border-bottom: 1px solid #eee; transition: background-color 0.3s;" onmouseover="this.style.backgroundColor='#f1f1f1'" onmouseout="this.style.backgroundColor='${rowBg}'">
                        <td style="padding: 15px; font-weight: bold; color: #0b6b50;">${tNum}</td>
                        <td style="padding: 15px; color: #333;">${tName}</td>
                        <td style="padding: 15px; font-weight: 500;">${actual.closedFrom}</td>
                        <td style="padding: 15px; font-weight: 500;">${actual.closedTo}</td>
                        <td style="padding: 15px;">${statusBadge}</td>
                    </tr>
                `;
            });

            actualLogsHtml += `
                        </tbody>
                    </table>
                </div>
            `;

            todayLogBox.innerHTML = actualLogsHtml;
        }

    } catch (err) {
        topBox.innerHTML = `<h3 style='color: red;'>Error loading data: ${err.message}</h3>`;
        console.error("Detail fetch error: ", err);
    }
}