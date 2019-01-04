(function ($) {	

	// 图片旋转
	// 方案修改自：http://byzuo.com/
	$.fn.rotate = function (name, maxWidth) {

		var img = $(this)[0],
			step = img.getAttribute('step');

		if (!this.data('width') && !$(this).data('height')) {
			this.data('width', img.width);
			this.data('height', img.height);
		};

		if (step == null) step = 0;
		if (name === 'left') {
			(step == 3) ? step = 0 : step++;
		} else if (name === 'right') {
			(step == 0) ? step = 3 : step--;
		};
		img.setAttribute('step', step);
		var show_width = this.data('width'),
			show_height = this.data('height');
		if ((step == 1 || step == 3) && this.data('width') < this.data('height') && this.data('height') > maxWidth) {
			show_height = maxWidth;
			show_width = this.data('width') * maxWidth / this.data('height');
		}
		// IE浏览器使用滤镜旋转
		var isIE = !+[1];
		var isSupportCanvas=false;
		try {
			document.createElement("canvas").getContext("2d");
			isSupportCanvas=true;
		}catch(e){
		}
		if (isIE) {
			img.style.filter = 'progid:DXImageTransform.Microsoft.BasicImage(rotation=' + step + ')';
			img.width = show_width;
			img.height = show_height;
			// IE8高度设置
			if ($.browser.version == 8) {
				switch (step) {
				case 0:
					this.parent().height('');
					break;
				case 1:
					this.parent().height(this.data('width') + 10);
					break;
				case 2:
					this.parent().height('');
					break;
				case 3:
					this.parent().height(this.data('width') + 10);
					break;
				};
			};
			// 对现代浏览器写入HTML5的元素进行旋转： canvas
		} else if(isSupportCanvas) {
			var c = this.next('canvas')[0];
			if (this.next('canvas').length == 0) {
				this.css({
					'visibility': 'hidden',
					'position': 'absolute'
				});
				c = document.createElement('canvas');
				c.setAttribute('class', 'maxImg canvas');
				img.parentNode.appendChild(c);
			}
			var canvasContext = c.getContext('2d');
			var resizefactor = 1;
			show_height = img.raw_height = $(img).attr('raw_height');	//图片原始高度
			show_width = img.raw_width = $(img).attr('raw_width'); 		//原始宽度
			if ((step == 1 || step == 3) && img.raw_height > maxWidth) {
				resizefactor = maxWidth / img.raw_height;
				show_height = maxWidth;
				show_width = resizefactor * img.raw_width;
			}
			if ((step == 0 || step == 2) && img.raw_width > maxWidth) {
				resizefactor = maxWidth / img.raw_width;
				show_height = resizefactor * img.raw_height;
				show_width = maxWidth;
			}
			switch (step) {
			default:
			case 0:
				c.setAttribute('width', show_width);
				c.setAttribute('height', show_height);
				canvasContext.rotate(0 * Math.PI / 180);
				canvasContext.scale(resizefactor, resizefactor);						
				canvasContext.drawImage(img, 0, 0);
				break;
			case 1:
				c.setAttribute('width', show_height);
				c.setAttribute('height', show_width);
				canvasContext.rotate(90 * Math.PI / 180);
				canvasContext.scale(resizefactor, resizefactor);
				canvasContext.drawImage(img, 0, -img.raw_height);
				break;
			case 2:
				c.setAttribute('width', show_width);
				c.setAttribute('height', show_height);
				canvasContext.rotate(180 * Math.PI / 180);
				canvasContext.scale(resizefactor, resizefactor);
				canvasContext.drawImage(img, -img.raw_width, -img.raw_height);
				break;
			case 3:
				c.setAttribute('width', show_height);
				c.setAttribute('height', show_width);
				canvasContext.rotate(270 * Math.PI / 180);
				canvasContext.scale(resizefactor, resizefactor);
				canvasContext.drawImage(img, -img.raw_width, 0);
				break;
			};
		};
	};


})(jQuery);