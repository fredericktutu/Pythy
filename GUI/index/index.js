var num = "";
var btn = getId('btn');
var code = getId('code');
var file = null;



getId("code").addEventListener("keydown", keyUp);


function openFile() {
    var input = getId("file-opener");
    input.click();

}

function loadFile() {
    var input = getId("file-opener")
    file = input.files[0];
    getId("filename").innerHTML =  file.name;
    var reader = new FileReader();
    reader.readAsText(file, "utf-8")
    reader.onload = function () {
        getId("code").value = reader.result;
    }
}

function closeFile() {
    getId("filename").innerHTML = "";
    getId("code").value = "";
}

function downloadFile() {
    if(file == null) {
        var blob = new Blob([code.value], {type:"text/plain;charset=utf-8"});
        saveAs(blob, "code.py");
    } else {
        var blob = new Blob([code.value], {type:"text/plain;charset=utf-8"});
        saveAs(blob, file.name);
    }
}








function change_color(which) {
    var color;
    switch(which) {
        case 0:
            color = getId("input-color").value;
            document.getElementById("code").style.backgroundColor = color;
        break;
        case 1:
            color = getId("console-color").value;
            getId("console").style.backgroundColor = color;
            getId("input").style.backgroundColor = color;
            break
        case 2:
            color = getId("bg-color").value;
            document.getElementsByTagName("body")[0].style.backgroundColor = color;
            break;
        case 3:
            color = getId("menu-color").value;
            getId("left-menu").style.backgroundColor = color;
            getId("right-menu").style.backgroundColor = color;
            getId("input-btn").style.backgroundColor = color;
            getId("clear-btn").style.backgroundColor = color;
            break;
        default:
            break;
    }
}

function getId(obj) {
    return document.getElementById(obj);
}
function getLastIndent(str) {
    var i = str.length - 2;
    while(i >= 0) {
        if(str.substring(i, i+1) == "\n") {
            break;
        } else {
            i --;
        }
    }
    lastline = str.substring(i+1, str.length)
    var x = 0
    while(x < lastline.length) {
        if(lastline.substring(x, x+1) === "\t") {
            x ++;
        }else {
            break;
        }
    }
    return x;
}

function keyUp(e){
    var obj = getId("code")
    var x = e.keyCode;
    if (x == 9) //tab
    {
        obj.value = obj.value + "\t"; // 跳几格由你自已决定
        event.returnValue = false;
    } else if(x == 13) {
        obj.value += "\n";
        var tmp = getLastIndent(code.value);

        while(tmp > 0) {
            obj.value = obj.value + "\t";
            tmp --;
        }
        event.returnValue = false;
    }
    var str = code.value; 
    str = str.replace(/\r/gi,"");
    str = str.split("\n");
    n = str.length;
    line(n);
}
function line(n){
    var lineobj = getId("leftNum");
    for(var i = 1;i <= n;i ++){
        if(document.all){
        num += i + "\r\n";//判断浏览器是否是IE  
        }else{
        num += i + "\n";
        }
    }
    lineobj.value = num;
    num = "";
}


(function() {
    keyUp();
})();

