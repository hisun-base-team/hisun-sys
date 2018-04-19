var Login = function () {
    
    return {
        //main function to initiate the module
        init: function () {
        	
           $('.login-form').validate({
	            errorElement: 'label', //default input error message container
	            errorClass: 'help-inline', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            rules: {
	                username: {
	                    required: true
	                },
	                password: {
	                    required: true
	                },
	                remember: {
	                    required: false
	                },
	                kaptcha: {
	                	required: true
	                },
					code : {
						required: true
					}

	            },

	            messages: {
	                username: {
	                    required: "用户名不能为空."
	                },
	                password: {
	                    required: "密码不能为空."
	                },
	                kaptcha: {
	                	required: "验证码不能为空."
	                },
					code: {
						required: "短信验证码不能为空."
					}
	            },

	            invalidHandler: function (event, validator) { //display error alert on form submit   
	                //$('.alert-error', $('.login-form')).show();
	            },

	            highlight: function (element) { // hightlight error inputs
	                $(element)
	                    .closest('.control-group').addClass('error'); // set error class to the control group
	            },

	            success: function (label) {
	                label.closest('.control-group').removeClass('error');
	                label.remove();
	            },

	            errorPlacement: function (error, element) {
	                error.addClass('help-small no-left-padding').insertAfter(element.closest('.input-icon'));
	            }
	        });

	        /*$('.login-form input').keypress(function (e) {
	            if (e.which == 13) {
	                if ($('.login-form').validate().form()) {
	                    window.location.href = "index.html";
	                }
	                return false;
	            }
	        });*/

	        $('.forget-form').validate({
	            errorElement: 'label', //default input error message container
	            errorClass: 'help-inline', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            ignore: "",
	            rules: {
	                email: {
	                    required: true,
	                    email: true
	                }
	            },

	            messages: {
	                email: {
	                    required: "邮箱不能为空."
	                }
	            },

	            invalidHandler: function (event, validator) { //display error alert on form submit   

	            },

	            highlight: function (element) { // hightlight error inputs
	                $(element)
	                    .closest('.control-group').addClass('error'); // set error class to the control group
	            },

	            success: function (label) {
	                label.closest('.control-group').removeClass('error');
	                label.remove();
	            },

	            errorPlacement: function (error, element) {
	                error.addClass('help-small no-left-padding').insertAfter(element.closest('.input-icon'));
	            },

	            submitHandler: function (form) {
	                //window.location.href = "index.html";
	            	$(form).ajaxSubmit(
							{
								
								beforeSend : function() {
									$("#sendSubmitBtn").html("邮件发送中...");
									$("#sendSubmitBtn").attr("disabled", "disabled");
								},
								success : function(data) {
									if (data.success === true) {
										//$("#sendSubmitBtn").html("邮件发送成功");
										$("#successEmail").text($("#email").val());
										jQuery('.forget-form').hide();
							            jQuery('.forget-success-form').show();
										//showTip("提示", "邮件发送成功,请查收邮件！");
										//setTimeout("$('#sendSubmitBtn').modal('hide');",1000);
							            $("#sendSubmitBtn").html("提交<i class='m-icon-swapright m-icon-white'></i>");
										$("#sendSubmitBtn").removeAttr("disabled");
									}else{
										showTip("提示", data.msg);
										$("#sendSubmitBtn").html("提交<i class='m-icon-swapright m-icon-white'></i>");
										$("#sendSubmitBtn").removeAttr("disabled");
									}
									
								}

							});
	            }
	        });

	        $('.forget-form input').keypress(function (e) {
	            if (e.which == 13) {
	                if ($('.forget-form').validate().form()) {
	                    //window.location.href = "index.html";
	                	$(form).ajaxSubmit(
								{
									beforeSend : function() {
										$("#sendSubmitBtn").html("邮件发送中...");
										$("#sendSubmitBtn").attr("disabled", "disabled");
									},
									success : function(data) {
										if (data.success == "true" || data.success == true) {
											$("#sendSubmitBtn").html("邮件发送成功");
											
											showTip("提示", "邮件发送成功,请查收邮件！");
											//setTimeout("$('#sendSubmitBtn').modal('hide');",1000);
										}
										
									}

								});
	                }
	                return false;
	            }
	        });

	        jQuery('#forget-password').click(function () {
	            jQuery('.login-form').hide();
	            jQuery('.forget-form').show();
	        });

	        jQuery('#back-btn').click(function () {
	        	window.location.href = "login";
	        });
	        
	        jQuery('#back-btn1').click(function () {
	        	window.location.href = "login";
	        });
	        
	        $('.register-form').validate({
	            errorElement: 'label', //default input error message container
	            errorClass: 'help-inline', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            ignore: "",
	            rules: {
	                username: {
	                    required: true
	                },
	                password: {
	                    required: true
	                },
	                rpassword: {
	                    equalTo: "#register_password"
	                },
	                email: {
	                    required: true,
	                    email: true
	                },
	                tnc: {
	                    required: true
	                }
	            },

	            messages: { // custom messages for radio buttons and checkboxes
	                tnc: {
	                    required: "Please accept TNC first."
	                }
	            },

	            invalidHandler: function (event, validator) { //display error alert on form submit   

	            },

	            highlight: function (element) { // hightlight error inputs
	                $(element)
	                    .closest('.control-group').addClass('error'); // set error class to the control group
	            },

	            success: function (label) {
	                label.closest('.control-group').removeClass('error');
	                label.remove();
	            },

	            errorPlacement: function (error, element) {
	                if (element.attr("name") == "tnc") { // insert checkbox errors after the container                  
	                    error.addClass('help-small no-left-padding').insertAfter($('#register_tnc_error'));
	                } else {
	                    error.addClass('help-small no-left-padding').insertAfter(element.closest('.input-icon'));
	                }
	            },

	            submitHandler: function (form) {
	                window.location.href = "index.html";
	            }
	        });

	       /* jQuery('#register-btn').click(function () {
	            jQuery('.login-form').hide();
	            jQuery('.register-form').show();
	        });*/

	        jQuery('#register-back-btn').click(function () {
	            jQuery('.login-form').show();
	            jQuery('.register-form').hide();
	        });
        }

    };

}();