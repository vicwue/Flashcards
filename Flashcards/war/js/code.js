$(document).ready(function () {
	$("#results").addClass("table table-condensed table-hover table-bordered table-striped");
	$("#results tr").each(function (key,value) {
		var string = $(value).attr("id");
		if (string != undefined) {
		string = string.substring(4);
			$(value).on("click", function () {
				window.location.href = "getFlight?key="+string;
			});
		}
	});
});