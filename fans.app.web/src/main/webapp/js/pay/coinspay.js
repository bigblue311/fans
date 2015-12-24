jQuery(function(){
	$('.buy').on('click', function() {
		var type = $(this).attr('_type');
		var data1 = $(this).attr('_data1');
		var reUrl = $(this).attr('_reUrl');
		var msg = $(this).attr('_msg');
		if(confirm(msg)){
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
		}
	});
});