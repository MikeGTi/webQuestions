
var _counter = 0;
function Add() {
    _counter++;
    var oClone = document.getElementById("template").cloneNode(true);
        oClone.id += (_counter + "");
    document.getElementById("placeholder").appendChild(oClone);
}

function Remove() {
    var oClone = document.getElementById("template").cloneNode(true);
        oClone.id = (_counter + "");
    document.getElementById("placeholder").removeChild(oClone);
    _counter--;
}