var pageLoad = function(format, totalPage, currentPage) {
	// 规定格式字符串使用@来代替分割字符串
	var links = format.split('@');
	var pre = links[0];
	var next = "";
	if (links.length > 1) {
		next = links[1];
	}

	var showCount = 10;
	var halfShow = Math.floor(showCount / 2.0);

	// 计算开始页码
	var startNum = currentPage - halfShow;
	if (startNum < 1) {
		startNum = 1;
	}

	//计算结束页码
	var endNum = currentPage + halfShow;
	if (endNum > totalPage) {
		endNum = totalPage;
	}

	var sb = "";
	sb += "<ul class='pagination'>";
	if (currentPage != 1) {
		sb += "<li> <a href='" + pre + (currentPage - 1) + next + "'>上一页</a> </li>";
	}
	for (var i = startNum; i <= endNum; i++) {

		if (i == currentPage) {
			sb += "<li><span>" + currentPage + "</span></li>";

		} else {
			sb += "<li> <a href='" + pre + i + next + "' > "+ i+"</a> </li>";
		}

	}

	if (endNum < totalPage) {
		sb += "<li>... </li>";
	}

	if (currentPage != totalPage) {
		sb += "<li> <a href='" + pre + (currentPage + 1) + next + "'>下一页</a> </li>";
	}
	sb += "</ul>";

	return sb;
}