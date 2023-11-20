$(function(){

    $('.mobileButton').click(function(){
        $(".navItem").toggleClass("mobileHidden");
    });

    $('#wechat')
        .popup({
            popup: $('#instagramPic'),
            on: 'hover',
            position: 'bottom center'
        });

    $('#QQ')
        .popup({
            popup:$('#facebookPic'),
            on:"hover",
            position:"bottom center"
        });

});