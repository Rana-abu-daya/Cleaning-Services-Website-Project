// Function to calculate the distance between two sets of coordinates using the Haversine formula
function calculateDistance(lat1, lon1, lat2, lon2) {
  const R = 6371; // Radius of the Earth in kilometers

  const dLat = ((lat2 - lat1) * Math.PI) / 180;
  const dLon = ((lon2 - lon1) * Math.PI) / 180;

  const a =
    Math.sin(dLat / 2) * Math.sin(dLat / 2) +
    Math.cos((lat1 * Math.PI) / 180) *
      Math.cos((lat2 * Math.PI) / 180) *
      Math.sin(dLon / 2) *
      Math.sin(dLon / 2);

  const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

  const distance = R * c; // Distance in kilometers
  const distanceInMiles = distance * 0.621371; // Distance in miles

  return distanceInMiles;
}


function isDateInFuture(dateString) {
  var givenDate = new Date(dateString); // Convert the given date string to a Date object
  var currentDate = new Date(); // Get the current date

  // Compare the given date with the current date
  if (givenDate > currentDate) {
    return true; // The given date is in the future
  } else {
    return false; // The given date is not in the future
  }
 }
function isValidZipCode(zipCode) {
  // Regular expression pattern for ZIP code format (US)
  var zipCodePattern = /^\d{5}(?:[-\s]\d{4})?$/;

  // Check if the given ZIP code matches the pattern
  return zipCodePattern.test(zipCode);
}
function getLatLon(url) {
  return new Promise((resolve, reject) => {
    fetch(url)
      .then((response) => response.json())
      .then((data) => {

        resolve(data);
      })
      .catch((error) => {
        console.log(error);
        reject(error);
      });
  });
}

function checkZipCode(zipCode1) {
  const zipCode2 = "98101"; // Second zip code
  const url1 = "https://api.zippopotam.us/us/" + zipCode2;
  const url2 = "https://api.zippopotam.us/us/" + zipCode1;

  return getLatLon(url1)
    .then((dd1) => {
      if (dd1 === null || dd1 === undefined || Object.keys(dd1).length === 0) {
 return false;
       }
      const lat1 = parseFloat(dd1.places[0].latitude);
      const lon1 = parseFloat(dd1.places[0].longitude);

      return getLatLon(url2)
        .then((dd2) => {
          if (dd2 === null || dd2 === undefined || Object.keys(dd2).length === 0) {
            return false;
          }
          const lat2 = parseFloat(dd2.places[0].latitude);
          const lon2 = parseFloat(dd2.places[0].longitude);
          const distance = calculateDistance(lat1, lon1, lat2, lon2);

          if (distance <= 50) {
            return true; // Distance is 50 miles or less
          } else {
            return false; // Distance is more than 50 miles
          }
        })
        .catch((error) => {
          console.log(error); // Handle the error here
          return false; // Error occurred, return false
        });
    })
    .catch((error) => {
      console.log(error); // Handle the error here
      return false; // Error occurred, return false
    });
}
function createCleanerDiv(urlCleaners){
console.log(urlCleaners);
fetch(urlCleaners)
        .then(response => response.text())
        .then(data => {
          document.getElementById('cleaners').innerHTML = data;
        })
        .catch(err => console.warn(err));


}

