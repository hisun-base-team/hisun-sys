/**
* This file is part of Qunee for HTML5.
* Copyright (c) 2015 by qunee.com
**/
$(function () {
	function byId(id) {
        return document.getElementById(id);
    }
	var canvas = byId("canvas");
	var toolbar = Q.createToolbar(window.graph, byId("toolbar"));
});

$(function () {
    var scrollAtTop;

    function checkScroll() {
        var atTop = $(window).scrollTop() < 20;
        if (scrollAtTop === atTop) {
            return;
        }
        scrollAtTop = atTop;
        if (scrollAtTop) {
            $('header').removeClass('scroll');
        } else {
            $('header').addClass('scroll');
        }
    }

    $(window).scroll(function (evt) {
        checkScroll();
    });
    checkScroll();
})