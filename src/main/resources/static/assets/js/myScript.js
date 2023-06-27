
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
           if(data[1]!='danger'){
         var cleanerDiv = document.getElementById("cleanerDiv"+idCleaner);
               cleanerDiv.parentNode.removeChild(cleanerDiv);
         }

  document.body.classList.remove("modal-open");
  var modalBackdrop = document.getElementsByClassName("modal-backdrop")[0];
  modalBackdrop.parentNode.removeChild(modalBackdrop);
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



