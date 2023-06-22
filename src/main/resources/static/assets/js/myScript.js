
function deleteAdmin(linkElement) {
 var parentElement = linkElement.parentElement;
 event.preventDefault(); // Prevent the link from navigating elsewhere
        event.preventDefault(); // Prevent the link from navigating elsewhere
        var idAdminInput = parentElement.querySelector('input[type="hidden"]');

         var idAdmin = idAdminInput.value;
        var urlDeletion =" /admins/delete/"+idAdmin;
       fetch(urlDeletion)
       .then(response => response.json())
       .then(data => {

       let  notification = document.getElementsByClassName('notificationPlace');
               let modal  = document.getElementById("exampleModal"+idAdmin);
                        modal.classList.remove("show");
        //  console.log(notification);
             notification[0].querySelector(('span')).innerHTML=data[0];
           notification[0].classList.add('alert-'+data[1]);
           if(data[1]!='danger'){
         var adminDiv = document.getElementById("adminDiv"+idAdmin);
               adminDiv.parentNode.removeChild(adminDiv);
         }
//         let modal  = document.getElementById("exampleModal"+idAdmin);
//         modal.classList.remove("show");
//      var modalBackdrop = document.getElementsByClassName('modal-backdrop')[0];
//      modalBackdrop.classList.remove("show");
  document.body.classList.remove("modal-open");
  var modalBackdrop = document.getElementsByClassName("modal-backdrop")[0];
  modalBackdrop.parentNode.removeChild(modalBackdrop);
        //   document.getElementById('divContent').innerHTML = data;
         })
         .catch(err => console.warn(err));
}

function deleteService(linkElement) {
//get the parant div to get the id ;
 var parentElement = linkElement.parentElement;
 event.preventDefault(); // Prevent the link from navigating elsewhere  
        event.preventDefault(); // Prevent the link from navigating elsewhere
        var idServiceInput = parentElement.querySelector('input[type="hidden"]');
//call the deletion url
         var idService = idServiceInput.value;
        var urlDeletion =" /services/delete/"+idService;
       fetch(urlDeletion)
         .then(response => response.json())
         .then(data => {
//data will be array [0] index for message ,, [1] index for alert type
          let  notification = document.getElementsByClassName('notificationPlace');
               let modal  = document.getElementById("exampleModal"+idService);
                        modal.classList.remove("show");
                   // let myarrayData  = data.split("**");
                      notification[0].querySelector(('span')).innerHTML=data[0];
                    notification[0].classList.add('alert-'+data[1]);
            if(data[1]!='danger'){
         var serviceDiv = document.getElementById("serviceDiv"+idService);
               serviceDiv.parentNode.removeChild(serviceDiv);
}


//      var modalBackdrop = document.getElementsByClassName('modal-backdrop');
//      modalBackdrop[0].classList.remove("show");
  document.body.classList.remove("modal-open");
  var modalBackdrop = document.getElementsByClassName("modal-backdrop")[0];
  modalBackdrop.parentNode.removeChild(modalBackdrop);
         })
         .catch(err => console.warn(err));
}

//if ( document.getElementById('divContent').textContent.trim() === '' ){
//fetch('/dashboard/dash')
//        .then(response => response.text())
//        .then(data => {
//          document.getElementById('divContent').innerHTML = data;
//        })
//        .catch(err => console.warn(err));
//}
//     document.getElementById('dashboard').addEventListener('click', function(event) {
//      event.preventDefault(); // Prevent the link from navigating elsewhere
//      let gg = document.getElementsByClassName('nav-link active');
//           gg[0].classList.remove("active");
//           document.getElementById('dashboard').classList.add("active");
//      fetch('/dashboard/dash')
//        .then(response => response.text())
//        .then(data => {
//          document.getElementById('divContent').innerHTML = data;
//        })
//        .catch(err => console.warn(err));
//    });
//
//    document.getElementById('employee').addEventListener('click', function(event) {
//      event.preventDefault(); // Prevent the link from navigating elsewhere
//        let gg = document.getElementsByClassName('nav-link active');
//           gg[0].classList.remove("active");
//           document.getElementById('employee').classList.add("active");
//      fetch('../pages/cleaner.html')
//        .then(response => response.text())
//        .then(data => {
//          document.getElementById('divContent').innerHTML = data;
//        })
//        .catch(err => console.warn(err));
//
//    });
//     document.getElementById('profile').addEventListener('click', function(event) {
//      event.preventDefault(); // Prevent the link from navigating elsewhere
//          let gg = document.getElementsByClassName('nav-link active');
//           gg[0].classList.remove("active");
//           document.getElementById('profile').classList.add("active");
//      fetch(' ../pages/profile2.html')
//        .then(response => response.text())
//        .then(data => {
//          document.getElementById('divContent').innerHTML = data;
//        })
//        .catch(err => console.warn(err));
//    });
//
//     document.getElementById('booking').addEventListener('click', function(event) {
//      event.preventDefault(); // Prevent the link from navigating elsewhere
//          let gg = document.getElementsByClassName('nav-link active');
//           gg[0].classList.remove("active");
//           document.getElementById('booking').classList.add("active");
//      fetch(' ../pages/bookings.html')
//        .then(response => response.text())
//        .then(data => {
//          document.getElementById('divContent').innerHTML = data;
//        })
//        .catch(err => console.warn(err));
//    });
//
//    document.getElementById('customer').addEventListener('click', function(event) {
//      event.preventDefault(); // Prevent the link from navigating elsewhere
//          let gg = document.getElementsByClassName('nav-link active');
//           gg[0].classList.remove("active");
//           document.getElementById('customer').classList.add("active");
//      fetch(' ../pages/customer.html')
//        .then(response => response.text())
//        .then(data => {
//          document.getElementById('divContent').innerHTML = data;
//        })
//        .catch(err => console.warn(err));
//    });
//
//      document.getElementById('services').addEventListener('click', function(event) {
//      event.preventDefault(); // Prevent the link from navigating elsewhere
//          let gg = document.getElementsByClassName('nav-link active');
//           gg[0].classList.remove("active");
//           document.getElementById('services').classList.add("active");
//      fetch('/services/services')
//        .then(response => response.text())
//        .then(data => {
//        console.log(data);
//          document.getElementById('divContent').innerHTML = data;
//        })
//        .catch(err => console.warn(err));
//    });
//function ClickTheEditLink(){
//
//event.preventDefault(); // Prevent the link from navigating elsewhere
//       event.preventDefault(); // Prevent the link from navigating elsewhere
//           document.getElementById('employee').classList.add("active");
//      fetch(' ../pages/becomeCleanerAdmin.html')
//        .then(response => response.text())
//        .then(data => {
//          document.getElementById('divContent').innerHTML = data;
//        })
//        .catch(err => console.warn(err));
//}
//
//function ClickTheEditBooking(){
//
//event.preventDefault(); // Prevent the link from navigating elsewhere
//       event.preventDefault(); // Prevent the link from navigating elsewhere
//           document.getElementById('booking').classList.add("active");
//      fetch(' ../pages/BookingEdit.html')
//        .then(response => response.text())
//        .then(data => {
//          document.getElementById('divContent').innerHTML = data;
//        })
//        .catch(err => console.warn(err));
//}
//
//
//function ClickTheEditCustomer(){
//
//event.preventDefault(); // Prevent the link from navigating elsewhere
//       event.preventDefault(); // Prevent the link from navigating elsewhere
//           document.getElementById('customer').classList.add("active");
//      fetch(' ../pages/CustomerAdmin.html')
//        .then(response => response.text())
//        .then(data => {
//          document.getElementById('divContent').innerHTML = data;
//        })
//        .catch(err => console.warn(err));
//}
//
//
//
//function newService(){
//event.preventDefault(); // Prevent the link from navigating elsewhere
//       event.preventDefault(); // Prevent the link from navigating elsewhere
//           document.getElementById('services').classList.add("active");
//      fetch('/services/add-service')
//        .then(response => response.text())
//        .then(data => {
//          document.getElementById('divContent').innerHTML = data;
//        })
//        .catch(err => console.warn(err));
//}

