window.onload = () => {
    fetchCrossings(null); 
};

// Triggered by clicking the "Search" button in your HTML
function searchCrossing() {
    // Grabs the value from your <input type="number" id="ID">
    const idInput = document.getElementById('ID').value;
    fetchCrossings(idInput);
}

// Main function that talks to your Spring Boot Backend
async function fetchCrossings(id) {
    
    // If an ID exists in the search box, use the search/{id} URL. 
    // Otherwise, fetch all from /search
    const apiUrl = (id && id.trim() !== '') ? `/api/crossings/search/${id}` : `/api/crossings/search`;
    
    // Grabs the exact <div> you specified to display the data
    const container = document.getElementById('displayRailwayCrossing');
    container.innerHTML = "<h3 style='text-align:center;'>Loading data...</h3>";

    try {
        const response = await fetch(apiUrl);
        
        if (!response.ok) {
            throw new Error("Failed to fetch data from the server.");
        }
        
        const data = await response.json();
        
        container.innerHTML = ""; // Clear the loading text
        
        if (data.length === 0) {
            container.innerHTML = "<h3 style='text-align:center;'>No railway crossings found.</h3>";
            return;
        }

        // Loop through the data and create a nice card for each crossing
        data.forEach(crossing => {
            container.innerHTML += `
                <div style="border: 1px solid #ccc; padding: 20px; margin-bottom: 15px; border-radius: 8px; background-color: #f9f9f9; box-shadow: 0 2px 4px rgba(0,0,0,0.1);">
                    <h3 style="margin-top: 0; color: rgb(35, 34, 34);">Crossing ID: ${crossing.id}</h3>
                    <p><strong>Address:</strong> ${crossing.address}</p>
                    <p><strong>Pincode:</strong> ${crossing.pincode}</p>
                    <p><strong>Status:</strong> ${crossing.status}</p>
					<a href="/RailwayCrossing/crossing-detail/${crossing.id}" class="detail-link" style="display: inline-block; margin-top: 10px; color: rgb(13, 89, 114); text-decoration: none; font-weight: bold;">
					    View More Info <i class="fa-solid fa-arrow-right"></i>
					</a>
                </div>
            `;
        });

    } catch (err) {
        container.innerHTML = "<h3 style='color: red; text-align:center;'>Error loading railway crossings. Please try again.</h3>";
        console.error("Fetch error: ", err);
    }
}