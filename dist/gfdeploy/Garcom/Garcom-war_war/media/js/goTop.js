function goTop(y) {
    if(y === undefined){
        y = 0;
    }    
    $('html, body').animate({
        scrollTop : y
    },200);
}