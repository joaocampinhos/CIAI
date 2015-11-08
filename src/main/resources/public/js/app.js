// GOAT logo
console.log("%c  __  __          __           ___ \n /\\ \\/\\ \\        /\\ \\__       /\\_ \\ \n \\ \\ \\_\\ \\    ___\\ \\ ,_\\    __\\//\\ \\    _ __ \n  \\ \\  _  \\  / __`\\ \\ \\/  /'__`\\\\ \\ \\  /\\`'__\\ \n   \\ \\ \\ \\ \\/\\ \\ \\ \\ \\ \\_/\\  __/ \\_\\ \\_\\ \\ \\/ \n    \\ \\_\\ \\_\\ \\____/\\ \\__\\ \\____\\/\\____\\\\ \\_\\ \n     \\/_/\\/_/\\/___/  \\/__/\\/____/\\/____/ \\/_/ \n ", 'color: #66557d');

// get GET parameter value with name from url
function gup( name, url ) {
  if (!url) url = location.href;
  name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
  var regexS = "[\\?&]"+name+"=([^&#]*)";
  var regex = new RegExp( regexS );
  var results = regex.exec( url );
  return results == null ? null : results[1];
}

// Set departure date to tomorrow if empty
var departure = gup('departure')
if (!departure) {
  var tomorrow = new Date(new Date().getTime() + 24 * 60 * 60 * 1000).toISOString().substring(0,10);
  var d = document.getElementsByName('departure');
  if (d.length > 0) d[0].value = tomorrow;
}

// pass get parameters to hotel page
Array.prototype.slice.call(document.querySelectorAll('a.gohotel')).forEach(function(e) {
  var url = location.href;
  if (url.indexOf('?') !== -1) {
    var req = url.substring(url.indexOf('?'));
    e.href += req;
  }
});

// change room price based on room type
var e = document.getElementsByName("roomtype")[0];
if (e) { e.onchange=updatePrice; }
function updatePrice() {
  var p = document.getElementById('price');
  if (p) {
    p.textContent = e.options[e.selectedIndex].getAttribute("data-price");
  }
};updatePrice();

// Dashboard expand button
Array.prototype.slice.call(document.querySelectorAll('button.view')).forEach(function(e) {
  e.addEventListener("click", llog(e));
});
function llog(i) {
  return function () {
    i.parentNode.parentNode.nextElementSibling.classList.toggle('hidden');
  };
};

// Close flash messages
Array.prototype.slice.call(document.querySelectorAll('.message-close')).forEach(function(e) {
  e.addEventListener("click", function(){
    document.querySelectorAll('.message')[0].classList.toggle('hidden');
  });
});


var hotelselect = document.getElementById("hotel-ajax");

hotelselect.addEventListener("change", function() {
    var options = hotelselect.querySelectorAll("option");
    console.log("/hotels/"+options[this.selectedIndex].value+"/roomtypes.json");
});
