$(function(){
	$('.buy').on('click', function() {
		var type = $(this).attr('_type');
		var data1 = $(this).attr('_data1');
		var reUrl = $(this).attr('_reUrl');
		var msg = $(this).attr('_msg');
		if(confirm(msg)){
			$.ajax({
				  url: contextRoot+'/api/paycoins.json?type='+type+'&data1='+data1,
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
	
	$('.buySuperTop').on('click', function() {
		var type = $(this).attr('_type');
		var data1 = $(this).attr('_data1');
		var reUrl = $(this).attr('_reUrl');
		var msg = $(this).attr('_msg');
		$.ajax({
			  url: contextRoot+'/api/checkSuperTopCoins.json?coins='+data1,
			  type: 'POST',
			  dataType: 'json',
			  success:function(data){
				  if(data.success){
					  if(confirm(msg)){
							$.ajax({
								  url: contextRoot+'/api/paycoins.json?type='+type+'&data1='+data1,
								  type: 'POST',
								  dataType: 'json',
								  success: function(res){
									  alert(res.message);
									  if(res.success){
										  window.location.href = reUrl;
									  }
									  return;
								  }
							});
						}
				  } else {
					  showAlert(data.message,data.title,data.link);
				  }
			  }
		});
	});
});

function showAlert(msg, linkMsg, href){
	layer.open({
	    title: [
	        '提示',
	        'background-color:#F6F6F6; color:#191919;font-weight:bold;'
	    ],
	    content: '<div class="ft_detail">'+
				 	'<p>'+msg+'</p>'+
				 	'<a href=\''+href+'\'>'+
				 	'<button style=\'float: right;margin: 10px 0px 10px 0px;padding: 10px;border: 1px solid white;background: #E5247C;color: white;\'>'+linkMsg+'</button>'+
				 	'</a>'+
				 '</div>'
	});
}