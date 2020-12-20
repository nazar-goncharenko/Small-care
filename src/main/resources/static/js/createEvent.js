function myValidationForm() {
    if($('html :checkbox:checked').length > 0){
        return true;
    } else {
        alert('Ви не можете створити без тваринки');
        return false;
    }
}