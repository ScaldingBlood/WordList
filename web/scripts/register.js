(function() {
	var className;
	var divs;
	$(function() {
		className = ["content1", "content2", "content3"];
		divs = $(".content>div");
		//		turnTo(1);
		var signIn = $("#signIn");
		// ������¼�Ի���
		signIn.click(function() {
			$("#modal").css("display", "block");
			$("#loginDialog").fadeIn(150);

		});
		// ��¼�Ի���ر�
		$("#closeDig").click(function() {
			$("#modal").css("display", "none");
			$("#loginDialog").fadeOut(150);
			return false;
		});

		$("#btnSearch").keydown(function(e) {
			if (e.keyCode == 13) {
				// �˴�д������������
				translate();
			}

		});

		var flag = true;
		$("#signUp").click(function() {
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
			if (divs[index-1].className.indexOf("content2") != -1) {
				break;
			}
		}
	};
	
})();