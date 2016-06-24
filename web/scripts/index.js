(function() {
		var className;
		var divs;
		$(function() {

			className = ["content1", "content2", "content3"];
			divs = $(".content>div");

			var content = $(".content");

			var signIn = $("#signIn");

			// ��¼
			signIn.click(function() {
				$("#modal").css("display", "block");
				$("#loginDialog").fadeIn(150);

			});
			// ��¼�ر�
			$("#closeDig").click(function() {
				$("#modal").css("display", "none");
				$("#loginDialog").fadeOut(150);
				return false;
			});

			// ajax ��¼

			$('#btnSignin').click(function() {
				var username = $('#username').val();
				var password = $('#password').val();
				var remeber = $('#password').get(0).checked;

				if (!username || !password) {
					$('.status').html("�������û���������");
					return false;
				}

				var json = {
					"username": username,
					"password": password,
					"remember": remeber
				};

				$.post("/account/login.action", json, function(data) {
					data = $.parseJSON(data);
					if (!data) {
						$('.status').html("�û������������");
						return false;
					}
					if (data.name) {
						$('.welcome>span').html("hello:000");
						$('.arrow').fadeIn(200);
						$('.content1').css('opacity', "1");
						$('.content3').css('opacity', "1");
                        $('.content2').css('opacity', "1");
						$('#closeDig').trigger("click");
					} else {
						$('.status').html("�û������������");
					}
				});

				return false;

			});

			// ajax Search

			$('#btnSearch').click(function() {
				var spelling = $('#searchContent').val();
				if (!spelling) {
					return;
				}
				search(spelling);

            });



			// �������

			$('#sortAsc').click(function() {
				var totalPage = -1;
				if (totalPage === -1) {
					$.getJSON("/words/manipulate4", {}, function(e) {
						totalPage = e.totalPage+0;
						var page = pageLoad("", totalPage, 1);
						$(".page").html(page);
						loadByAsc(1);
					});
				}

			});


            $('#sortTime').click(function() {
                var totalPage = -1;
                if (totalPage === -1) {
                    $.getJSON("/words/manipulate4", {}, function(e) {
                        totalPage = e.totalPage+0;
                        var page = pageLoad("", totalPage, 1);
                        $(".page").html(page);
                        loadByTime(1);
                    });
                }

            });
			$('#sortTime').click(function() {

			});

			//
			var flag = true;

			$("#signUp").click(function() {
				window.location.href = "register.html"
			});



			// �ղغ��޸�
			$('#btnCollect').click(function() {
               var spelling =  $('.detail_head>input').val();
                var definition = $('.detail_tr>input').val();
               var sentences=  $('.detail_ex>textarea').val();

                $.getJSON("/words/manipulate1",{"spelling":spelling,
                    "definition":definition,
                    "sentences":sentences,
                },function (e) {

                })
			});

            $('#btnModify').click(function () {
                var spelling =  $('.detail_head>input').val();
                var definition = $('.detail_tr>input').val();
                var sentences=  $('.detail_ex>textarea').val();

                $.getJSON("/words/manipulate3",{"spelling":spelling,
                    "definition":definition,
                    "sentences":sentences,
                },function (e) {

                })
            })
			// ���ҵ���¼�
			var flag = true;
			$('.arrow>.prev').click(function() {
				if (flag) {
					flag = false;
					className.push(className.shift());

					for (var i = 0; i < divs.length; i++) {
						divs[i].className = className[i];
					}

					setTimeout(function() {
						flag = true;
					}, 500);
				}
			});

			$('.arrow>.next').click(function() {
				if (flag) {
					flag = false;
					className.unshift(className.pop());

					for (var i = 0; i < divs.length; i++) {
						divs[i].className = className[i];
					}

					setTimeout(function() {
						flag = true;
					}, 500);
				}
			});

			$('#btnInsert').click(function () {
				turnTo(1);
				$('.detail_head').val("");
				$('.detail_tr>span').html("����");
				$('.detail_tr>input').val("");
				$('.detail_ex>span').html("����");
				$('.detail_ex>textarea').val("");

				
			});
            var lis = $(".searchRes>ul li");
            // �б�
/*
            for(var k  = 0;k<lis.length;k++){

                lis[k].hover = function () {
                    $(lis[k]).addClass("list-group-item-success").siblings().removeClass("list-group-item-success");
                }
                lis[k].blur = function () {
                    $(lis[k]).removeClass("list-group-item-success");
                }
                lis[k].click = function () {
                    // $(lis[k]).addClass("list-group-item-success").siblings().removeClass("list-group-item-success");
                    search($(lis[k]).children('span').get(0).innerHTML);
                }
            }*/
			$(document).on("hover",lis,function(){
				$(this).addClass("list-group-item-success").siblings().removeClass("list-group-item-success");
			});
            l
			$(document).on("blur",lis,function(){
				 $(this).removeClass("list-group-item-success");
			});
            $(document).on("click",lis,function(){
				 search($(this).children('span').get(0).innerHTML);
			});
            
		})
		var translate = function() {
			turnTo(1);
			$(".translate>.english").val($("#btnSearch").val());
		}
		var turnTo = function(index) {
			while (true) {
				className.push(className.shift());
				for (var i = 0; i < divs.length; i++) {
					divs[i].className = className[i];
				}
				if (divs[index - 1].className.indexOf("content2") != -1) {
					break;
				}
			}
		};

		var loadByAsc = function(currentPage) {
			$.getJSON("/words/manipulate2", {
				"totalPage": currentPage,
				"parameter": 1
			}, function(e) {
                $('.searchRes>ul').empty();
				$.each(e, function(i, v) {

					var li = $("<li>");
					li.addClass("list-group-item");
					var span = $('<span>');
					span.html(v.spelling);
					span.appendTo(li);
					span = $('<span>');
					span.html(v.definition);
					span.appendTo(li);
					li.appendTo($('.searchRes>ul'));
				});
			});
		};

    var loadByTime = function(currentPage) {
        $.getJSON("/words/manipulate2", {
            "totalPage": currentPage,
            "parameter": 0
        }, function(e) {
            $('.searchRes>ul').empty();
            $.each(e, function(i, v) {

                var li = $("<li>");
                li.addClass("list-group-item");
                var span = $('<span>');
                span.html(v.spelling);
                span.appendTo(li);
                span = $('<span>');
                span.html(v.definition);
                span.appendTo(li);
                li.appendTo($('.searchRes>ul'));
            });
        });
    };

    var search = function(spelling){
			$.getJSON("/words/manipulate2", {
					"spelling": spelling
				}, function(e) {
				turnTo(1);
				$('.detail_head>input').val(spelling);
				$('.detail_tr>span').html("spelling�ķ���");
				$('.detail_tr>input').val(e.definition);
				$('.detail_ex>span').html("spelling������");
				$('.detail_ex>textarea').val(e.sentences);
			});
		}

})();