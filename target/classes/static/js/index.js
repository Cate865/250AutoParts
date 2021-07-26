// Authors: Liplan Lekipising and catherine Muthoni

function showOrders() {
  document.getElementById("myordersac").style.display = "block";
  document.getElementById("acc").style.display = "none";
}

function showAccount() {
  document.getElementById("acc").style.display = "block";
  document.getElementById("myordersac").style.display = "none";
}

function enableBtn() {
  document.getElementById("submit").disabled = false;
}

var myVar;

function myFunction() {
  myVar = setTimeout(showPage, 3000);
}

function showPage() {
  document.getElementById("loader").style.display = "none";
  document.getElementById("myDiv").style.display = "block";
}

function makePayment() {
    FlutterwaveCheckout({
        public_key: "FLWPUBK_TEST-8b102812cf681f6b6d267318ec2ae133-X",
        tx_ref: "CM001",
        amount: parseFloat(document.getElementById("totalPay").rows[0].cells[1].innerHTML),
        currency: "RWF",
        country: "RW",
        payment_options: " ",
        redirect_url: // specified redirect URL
            "localhost:9000",
        // meta: {
        //     consumer_id: 23,
        //     consumer_mac: "92a3-912ba-1192a",
        // },
        customer: {
            email: console.log(document.getElementById("myemail").innerHTML),
            phone_number: console.log(document.getElementById("myphone").innerHTML),
            name: console.log(document.getElementById("myfirstname").innerHTML),
        },
        // callback: function (data) {
        //     console.log(data);
        // },
        // onclose: function() {
        //     // close modal
        // },
        customizations: {
            title: "AutoParts 250",
            description: "Payment for items in cart",
            logo: "src/main/resources/static/assets/logo.png",
        },
    });
}
