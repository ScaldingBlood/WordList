(function() {
	var className;
	var divs;
	$(function() {

		className = ["content1", "content2", "content3"];
		divs = $(".content>div");

		var content = $(".content");

		// turnTo(3);
		var signIn = $("#signIn");

		// 登录
		signIn.click(function() {
			$("#modal").css("display", "block");
			$("#loginDialog").fadeIn(150);

		});
		// 登录关闭
		$("#closeDig").click(function() {
			$("#modal").css("display", "none");
			$("#loginDialog").fadeOut(150);
			return false;
		});

		// ajax 登录

		$('#btnSignin').click(function() {
			var username = $('#username').val();
			var password = $('#password').val();
			var remeber = $('#password').get(0).checked;

			if (!username || !password) {
				$('.status').html("请输入用户名或密码");
				return false;
			}

			var json = {
				"username": username,
				"password": password,
				"remember": remeber
			};

			$.post("/account/login.action", json, function(data) {
				data = $.parseJSON(data);
				if(!data){
					$('.status').html("用户名或密码错误");
					return false;
				}
				if (data.name) {
					$('.welcome>span').html("hello:000");
					$('.arrow').fadeIn(200);
					$('.content1').css('opacity', "0.5");
					$('.content3').css('opacity', "0.5");
				} else {
					$('.status').html("用户名或密码错误");
				}
			});

			return false;

		});
		
		// ajax Search

		$('#btnSearch').click(function(){
			var spelling = $('#searchContent').val();
			if(!content){
				return;
			}
			$.getJSON("",{"content":spelling},function(e){
					var li = $("<li>");
					li.className = "list-group-item";
					$('span').innerHTML(e.word).appendTo(li);
					$('span').innerHTML(e.translate).appendTo(li);
					li.appendTo($('.searchRes>ul'));
			});
			
		});
		
		// 排序查找
		
		$('#sortAsc').click(function(){
			pageLoadByAsc();
			
			
		});
		$('#sortTime').click(function(){
			pageLoadByTime();
		});


		//
		var flag = true;

		$("#signUp").click(function() {
			window.location.href = "register.html"
		});
		var lis = $(".searchRes>ul>li");
		// 列表
		lis.hover(function() {

			$(this).addClass("list-group-item-success").siblings().removeClass("list-group-item-success");
		}).blur(function() {
			$(this).removeClass("list-group-item-success");
		});
		lis.click(function() {
			turnTo(1);
			$.ajax("",{"word":$(this).children("span").get(0).innerHTML},function(e){
				$('.detail_head>span').html(e.word);
				$('.detail_tr>span').html(e.word+"的翻译");
				$('.detail_tr>input').val(e.translate);
				// $('detail_ex>span').html(e.word+"的例句");
				$('.detail_ex>textarea').html(e.example);
				
			});
			
		});
		
		// 收藏和修改
		$('#btnCollect').click(function(){
			// var
			
		});
		
		

		// 左右点击事件
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
	
	// var pageLoadByAsc = function(index){
	// 	$.ajax("",{"index":index,"sort:asc"},function(e){
	// 		$.each(e, function(v,i) {
	// 				var li = $("<li>");
	// 				li.className = "list-group-item";
	// 				$('span').innerHTML(e.word).appendTo(li);
	// 				$('span').innerHTML(e.translate).appendTo(li);
	// 				li.appendTo($('.searchRes>ul'));
	// 			});
	// 	});
	// };
	// var pageLoadByTime = function(index){
	// 	$.ajax("",{"index":index,"sort":time},function(e){
	// 		$.each(e, function(v,i) {
	// 				var li = $("<li>");
	// 				li.className = "list-group-item";
	// 				$('span').innerHTML(e.word).appendTo(li);
	// 				$('span').innerHTML(e.translate).appendTo(li);
	// 				li.appendTo($('.searchRes>ul'));
	// 			});
	// 	});
	// };
	

})();