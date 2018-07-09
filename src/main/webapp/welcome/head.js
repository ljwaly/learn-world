function domyOrder(test){
	alert(test);//验证jsp页面传递参数可以读取
	alert(_webRootPath);//验证全局变量传递的可读性
	
	//验证ajax异步请求可执行型
	$.ajax({
		url :_webRootPath+ "/word_info/find",
		type:"POST",
		data : {
			
		},
		success : function(data) {
			if (data.resultCode=="success") {
				alert(data)
				getData(data);
			} else{
				alert("生效失败！")
			}	
		}
	});
}

/**
 * 遍历结果集，组合字符串，并打印
 * @param data
 */
function getData(data){
	var sb='';
	
//	$.each(data.studentList, function(i, item) {
//		sb = sb+ "Student=[studentId:" + item.studentId+ ",name:" + item.name + ",age:"+ item.age+ ",sex:"+ item.sex + "];";
//	});
	alert(sb.toString());
}


//@ sourceURL=CommonBarrageInfo.js
