formatterDate = function(date) {
	var year = date.getFullYear();// 年
	var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0" + (date.getMonth() + 1);// 月
	var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();// 日
	var hour = date.getHours() > 9 ? date.getHours() : "0" + date.getHours();// 时 
	var minute = date.getMinutes() > 9 ? date.getMinutes() : "0" + date.getMinutes();// 分
	var second = date.getSeconds() > 9 ? date.getSeconds() : "0" + date.getSeconds(); // 秒
	return year + '-' + month + '-' + day + " " + hour + ":" + minute + ":" + second;
};