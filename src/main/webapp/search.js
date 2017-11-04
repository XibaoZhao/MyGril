function Csearch() {
	$("tbody").html("");
	var pageNo = getHashPage();
	if (pageNo == null) {
		pageNo = 1;
	}
	$
			.ajax({
				type : "get",
				url : "/all",
				data : {
					"pageNo" : pageNo
				},
				success : function(bean) {
					var data = bean.rows;
					for (var i = 0; i < data.length; i++) {
						var row = "";
						row += "<tr><td>" + data[i].id + "</td><td>"
								+ data[i].age + "</td><td>" + data[i].cupSize
								+ "</td><td>" + data[i].isUse + "</td>";
						var edit = "<td><button class='btn btn-default' id='edit_"
								+ data[i].id + "'>修改</button></td>";
						var del = "<td><button class='btn btn-default' id='del_"
								+ data[i].id + "'>删除</button></td>";
						row += edit + del + "</tr>"
						$("tbody").append(row);
						$("#edit_" + data[i].id).click(
								function() {
									// 打开新增窗口
									var id = $(this).parent().parent()
											.children().eq(0).html();
									var age = $(this).parent().parent()
											.children().eq(1).html();
									var cupSize = $(this).parent().parent()
											.children().eq(2).html();
									$("#id").val(id);
									$("#age").val(age);
									$("#cup_size").val(cupSize);
									$("#myModal").modal("show");
								})
						$("#del_" + data[i].id).click(
								function() {
									var id = $(this).parent().parent()
											.children().eq(0).html();
									$.ajax({
										type : "get",
										url : "/delete	",
										data : {
											"id" : id
										},
										success : function(data) {
											Csearch();
										}
									})
								})
					}
					// 初始化页脚
					if (bean.total < 1) {
						$("#yejiao").html("");
					} else {
						$("#yejiao").html("");
						var bar = "";
						bar += "<li><a href='javascript:void(0)' onclick='prePage()'>&laquo;</a></li>";
						for (var i = 0; i < bean.total; i++) {
							bar += "<li><a href='javascript:void(0)'>"
									+ (i + 1) + "</a></li>";
						}
						bar += "<li><a href='javascript:void(0)' onclick='nextPage()'>&raquo;</a></li>";
						$("#yejiao").html(bar);
						if (pageNo == 1) {
							$("#yejiao li:eq(0)").addClass("disabled");
							$("#yejiao a:eq(0)").removeAttr("onclick");	
						} else if (pageNo == bean.total) {
							$("#yejiao li:eq("+(parseInt(pageNo)+1)+")").addClass("disabled");
							$("#yejiao a:eq("+(parseInt(pageNo)+1)+")").removeAttr("onclick");	
						}
						$("#yejiao").children("li").eq(pageNo).addClass("active");
						$("#yejiao a:gt(0):lt(" + bean.total + ")").click(function() {
							var p = parseInt($(this).html());
							window.location.hash = "#" + p;
							Csearch();
						})
					}

				}
			})
}

function Insert() {
	$("#age").val("");
	$("#cup_size").val("");
	$("#errorText").hide();
	$("#myModal").modal("show");
}

function confirm_save() {
	var id = $("#id").val();
	var age = $("#age").val();
	var cupSize = $("#cup_size").val();
	$.ajax({
		type : "get",
		url : "/add",
		data : {
			"id" : id,
			"age" : age,
			"cupSize" : cupSize
		},
		success : function(data) {
			if (data == "SUCCESS") {
				$("#myModal").modal("hide");
				Csearch();
			} else {
				$("#errorText").show();
			}
		}
	})
}

function Aedit() {
	var td = $(this).parent().parent();
	console.log(td);
}

function getHashPage() {
	var page = null;
	if (window.location.hash) {
		page = window.location.hash;
		if (page.indexOf("#") != -1) {
			page = page.substring(1);
		}
	} else {
		page = 1;
	}
	return page;
}

function prePage() {
	var p = getHashPage() - 1;
	window.location.hash = "#" + p;
	Csearch();
}

function nextPage() {
	var p = parseInt(getHashPage()) + 1;
	window.location.hash = "#" + p;
	Csearch();
}