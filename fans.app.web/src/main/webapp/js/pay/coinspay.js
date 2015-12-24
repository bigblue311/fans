jQuery(function(){
	$('.buy').on('click', function() {
		var type = $(this).attr('type');
		var data1 = $(this).attr('data1');
		var reUrl = $(this).attr('reUrl');
		$.ajax({
			  url: '/api/paycoins.json?type='+type+'&data1='+data1,
			  type: 'POST',
			  success: function(res){
				  alert(res.message);
				  if(res.success){
					  window.location.href = reUrl;
				  }
				  return;
			  },
			  dataType: "json"
		});
	});
});