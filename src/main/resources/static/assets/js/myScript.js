
function activateCustomer(linkElement) {
 var parentElement = linkElement.parentElement;
 event.preventDefault(); // Prevent the link from navigating elsewhere
        event.preventDefault(); // Prevent the link from navigating elsewhere
        var idCustomerInput = parentElement.querySelector('input[type="hidden"]');

         var idCustomer = idCustomerInput.value;
        var urlDeletion =" /customers/deletedCustomer/activate/"+idCustomer;
       fetch(urlDeletion)
       .then(response => response.json())
       .then(data => {

       let  notification = document.getElementsByClassName('notificationPlace');
               let modal  = document.getElementById("exampleModal"+idCustomer);
                        modal.classList.remove("show");
        //  console.log(notification);
             notification[0].querySelector(('span')).innerHTML=data[0];
           notification[0].classList.add('alert-'+data[1]);
           if(data[1]!='danger'){
         var customerDiv = document.getElementById("customerDiv"+idCustomer);
               customerDiv.parentNode.removeChild(customerDiv);
         }

  document.body.classList.remove("modal-open");
  var modalBackdrop = document.getElementsByClassName("modal-backdrop")[0];
  modalBackdrop.parentNode.removeChild(modalBackdrop);
    document.body.style.overflow = 'auto'; // 'auto' or 'scroll'
         })
         .catch(err => console.warn(err));
}
function startBooking(linkElement) {
 var parentElement = linkElement.parentElement;
 event.preventDefault(); // Prevent the link from navigating elsewhere
        event.preventDefault(); // Prevent the link from navigating elsewhere
        var idBookingInput = parentElement.querySelector('input[type="hidden"]');

         var idBooking = idBookingInput.value;
        var urlstart =" /bookings/start/"+idBooking;
        console.log(urlstart)
       fetch(urlstart)
       .then(response => response.json())
       .then(data => {

       let  notification = document.getElementsByClassName('notificationPlace');
               let modal  = document.getElementById("exampleModal"+idBooking);
                        modal.classList.remove("show");
        //  console.log(notification);
             notification[0].querySelector(('span')).innerHTML=data[0];
           notification[0].classList.add('alert-'+data[1]);
           if(data[1]!='danger'){
//         var bookingDiv = document.getElementById("bookingDiv"+idBooking);
//               bookingDiv.parentNode.removeChild(bookingDiv);
                var statusDiv =  document.getElementById("status"+idBooking);
                statusDiv.classList.add("alert-in_progress");
                statusDiv.textContent = "IN_PROGRESS";
                 var startBTN = document.getElementById("start"+idBooking);
                  if(startBTN ){
                  startBTN.parentNode.removeChild(startBTN);
                                                     }
                  var cancelBTN = document.getElementById("cancel"+idBooking);
                    if(cancelBTN ){
                  cancelBTN.parentNode.removeChild(cancelBTN);
                                                                       }
                    var doneBTN = document.getElementById("done"+idBooking);
                        if(doneBTN ){
                                       doneBTN.classList.remove("none");
                                        }
var editBTN = document.getElementById("edit"+idBooking);
                    if(editBTN ){
                  editBTN.parentNode.removeChild(editBTN);
                                                                       }


         }

  document.body.classList.remove("modal-open");
  var modalBackdrop = document.getElementsByClassName("modal-backdrop")[0];
  modalBackdrop.parentNode.removeChild(modalBackdrop);
    document.body.style.overflow = 'auto'; // 'auto' or 'scroll'
         })
         .catch(err => console.warn(err));
}
function doneBooking(linkElement) {
 var parentElement = linkElement.parentElement;
 event.preventDefault(); // Prevent the link from navigating elsewhere
        event.preventDefault(); // Prevent the link from navigating elsewhere
        var idBookingInput = parentElement.querySelector('input[type="hidden"]');

         var idBooking = idBookingInput.value;
        var urlDone =" /bookings/done/"+idBooking;
        console.log(urlDone)
       fetch(urlDone)
       .then(response => response.json())
       .then(data => {

       let  notification = document.getElementsByClassName('notificationPlace');
               let modal  = document.getElementById("exampleModal"+idBooking);
                        modal.classList.remove("show");
        //  console.log(notification);
             notification[0].querySelector(('span')).innerHTML=data[0];
           notification[0].classList.add('alert-'+data[1]);
           if(data[1]!='danger'){
//         var bookingDiv = document.getElementById("bookingDiv"+idBooking);
//               bookingDiv.parentNode.removeChild(bookingDiv);
                var statusDiv =  document.getElementById("status"+idBooking);
                statusDiv.classList.add("alert-success");
                statusDiv.textContent = "SUCCESS";
                 var doneBTN = document.getElementById("done"+idBooking);
                  if(doneBTN ){
doneBTN.parentNode.removeChild(doneBTN);
                                    }
                var editBTN = document.getElementById("edit"+idBooking);
                                    if(editBTN ){
                                  editBTN.parentNode.removeChild(editBTN);
                                                                                       }


         }

  document.body.classList.remove("modal-open");
  var modalBackdrop = document.getElementsByClassName("modal-backdrop")[0];
  modalBackdrop.parentNode.removeChild(modalBackdrop);
    document.body.style.overflow = 'auto'; // 'auto' or 'scroll'
         })
         .catch(err => console.warn(err));
}
function deleteBooking(linkElement) {
 var parentElement = linkElement.parentElement;
 event.preventDefault(); // Prevent the link from navigating elsewhere
        event.preventDefault(); // Prevent the link from navigating elsewhere
        var idBookingInput = parentElement.querySelector('input[type="hidden"]');

         var idBooking = idBookingInput.value;
        var urlDeletion =" /bookings/delete/"+idBooking;
       fetch(urlDeletion)
       .then(response => response.json())
       .then(data => {

       let  notification = document.getElementsByClassName('notificationPlace');
               let modal  = document.getElementById("exampleModal"+idBooking);
                        modal.classList.remove("show");
        //  console.log(notification);
             notification[0].querySelector(('span')).innerHTML=data[0];
           notification[0].classList.add('alert-'+data[1]);
           if(data[1]!='danger'){
//         var bookingDiv = document.getElementById("bookingDiv"+idBooking);
//               bookingDiv.parentNode.removeChild(bookingDiv);
                var statusDiv =  document.getElementById("status"+idBooking);
                statusDiv.classList.add("alert-cancelled");
                statusDiv.textContent = "CANCELLED";
                 var cancelBTN = document.getElementById("cancel"+idBooking);
                  if(cancelBTN ){
                        cancelBTN.parentNode.removeChild(cancelBTN);
                   }

                  var startBTN = document.getElementById("start"+idBooking);
                  if(startBTN ){
                                    startBTN.parentNode.removeChild(startBTN);
                                    }
                var editBTN = document.getElementById("edit"+idBooking);
                                    if(editBTN ){
                                  editBTN.parentNode.removeChild(editBTN);
                                                                                       }

         }

  document.body.classList.remove("modal-open");
  var modalBackdrop = document.getElementsByClassName("modal-backdrop")[0];
  modalBackdrop.parentNode.removeChild(modalBackdrop);
    document.body.style.overflow = 'auto'; // 'auto' or 'scroll'
         })
         .catch(err => console.warn(err));
}
function deleteCleaner(linkElement) {
 var parentElement = linkElement.parentElement;
 event.preventDefault(); // Prevent the link from navigating elsewhere
        event.preventDefault(); // Prevent the link from navigating elsewhere
        var idCleanerInput = parentElement.querySelector('input[type="hidden"]');

         var idCleaner = idCleanerInput.value;
        var urlDeletion =" /cleaners/delete/"+idCleaner;
       fetch(urlDeletion)
       .then(response => response.json())
       .then(data => {

       let  notification = document.getElementsByClassName('notificationPlace');
               let modal  = document.getElementById("exampleModal"+idCleaner);
                        modal.classList.remove("show");
        //  console.log(notification);
             notification[0].querySelector(('span')).innerHTML=data[0];
           notification[0].classList.add('alert-'+data[1]);
           if(data[1] == 'info'){
            document.getElementById('active'+idCleaner).innerHTML="inactive";
             document.getElementById('active'+idCleaner).classList.add('alert-warning');

           }else if(data[1]!='danger'){
         var cleanerDiv = document.getElementById("cleanerDiv"+idCleaner);
               cleanerDiv.parentNode.removeChild(cleanerDiv);
         }

  document.body.classList.remove("modal-open");
  var modalBackdrop = document.getElementsByClassName("modal-backdrop")[0];
  modalBackdrop.parentNode.removeChild(modalBackdrop);
    document.body.style.overflow = 'auto'; // 'auto' or 'scroll'
         })
         .catch(err => console.warn(err));
}
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
          document.body.style.overflow = 'auto'; // 'auto' or 'scroll'
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
    document.body.style.overflow = 'auto'; // 'auto' or 'scroll'
         })
         .catch(err => console.warn(err));
}

function deleteCustomer(linkElement) {
 var parentElement = linkElement.parentElement;
 event.preventDefault(); // Prevent the link from navigating elsewhere
        event.preventDefault(); // Prevent the link from navigating elsewhere
        var idCustomerInput = parentElement.querySelector('input[type="hidden"]');

         var idCustomer = idCustomerInput.value;
        var urlDeletion =" /customers/delete/"+idCustomer;
       fetch(urlDeletion)
       .then(response => response.json())
       .then(data => {

       let  notification = document.getElementsByClassName('notificationPlace');
               let modal  = document.getElementById("exampleModal"+idCustomer);
                        modal.classList.remove("show");
             notification[0].querySelector(('span')).innerHTML=data[0];
           notification[0].classList.add('alert-'+data[1]);
           if(data[1]!='danger'){
         var customerDiv = document.getElementById("customerDiv"+idCustomer);
               customerDiv.parentNode.removeChild(customerDiv);
         }

  document.body.classList.remove("modal-open");
  var modalBackdrop = document.getElementsByClassName("modal-backdrop")[0];
  modalBackdrop.parentNode.removeChild(modalBackdrop);
  document.body.style.overflow = 'auto'; // 'auto' or 'scroll'
console.log(window.getComputedStyle(document.body).overflow);
         })
         .catch(err => console.warn(err));

}
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



