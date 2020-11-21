/*
Form Submitting
1)Check Button
2)Check Form
*/
function myValidationButton() {
    // alert("myValidationButton");
    return true;
}

function myValidationForm() {
    if(isFileImage()){
        return true;
    } else {
        alert("File is not image!");
        return false;
    }
}

function isFileImage() {
    let file = document.getElementById("customFile").files[0];
    if(file != null){
        return file['type'].split('/')[0] === 'image';
    } else {
        return true;
    }
}