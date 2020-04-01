

function color() {
    console.log("hello")
    var code = getId("code");
    code.innerHTML = "<font id = 'red'>" + code.innerHTML + "</font>";
}



function getId(id) {
    return document.getElementById(id)
}