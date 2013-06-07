/*! Pagination v0.1 nguyenvanmanh.com */
(function($, undefined) {
	$
			.widget(
					'ui.pagination',
					{
						version : '0.1',
						options : {
							numberPageShow : 5,
							rowOnPage: 5,
							pWrapClass : '.p-wrap',
							backwardClass : '.backward',
							forwardClass : '.forward',
							nextClass : '.next',
							lastClass : '.last',
							firstClass : '.first',
							prevClass : '.prev',
							pWidthOffset : 3,
							pIdPrefix : '#page-',
							aClass : '.pagination-p',
							idCurrentPage : '#current-page',
							activeClass : 'active',
							divTableClass : '.list-key',
							divPaginationWrap : '.pagination-wrap',
							backgroudColorDisable: '#ccc',
							backgroudColorEnable: '#fdfdfd',
							divTableClassChangeNo: '.list-key'
						},
						_create : function() {
							this._initPages();
							this._changeActivePage();
							this._pageClickHandler();
							this._nextClickHandler();
							this._prevClickHandler();
							this._lastClickHandler();
							this._firstClickHandler();
						},
						_initPages : function() {
							$('.pages').css('float', 'left');
							// Get number of pages
							var pSize = $(this.options.pWrapClass).children()
									.size();
							if(pSize <= 0){
								$(this.options.firstClass).children().first().css(
										'background-color', this.options.backgroudColorDisable);
								$(this.options.prevClass).children().first().css(
										'background-color', this.options.backgroudColorDisable);
								$(this.options.nextClass).children().first().css(
										'background-color', this.options.backgroudColorDisable);
								$(this.options.lastClass).children().first().css(
										'background-color', this.options.backgroudColorDisable);
							}
							// change number page show
							if (pSize < this.options.numberPageShow) {
								this.options.numberPageShow = pSize;
							}
							// calculate with of pages show
							var divPaginationWrapWidth = ($(
									this.options.pWrapClass).children().first()
									.outerWidth(true) + this.options.pWidthOffset)
									* this.options.numberPageShow;
							// set css for class p-wrap
							$(this.options.divPaginationWrap).css({
								width : divPaginationWrapWidth + 'px',
								float : 'left',
								overflow : 'hidden'
							});

							var pWrapWidth = ($(this.options.pWrapClass)
									.children().first().outerWidth(true) + this.options.pWidthOffset+10)
									* pSize;

							$(this.options.pWrapClass).css({
								position : 'relative',
								width : pWrapWidth + 'px'
							});

						},
						_changeActivePage : function() {
							var currentPage = 1 * $(this.options.idCurrentPage)
									.val();
							$(this.options.pIdPrefix + currentPage).addClass(
									this.options.activeClass);
						},
						_ajaxLoadData: function(v_skip) {
							var widget = this;
							var vUrl = window.location.href;
							vUrl = vUrl.replace('?limit','&limit');
							vUrl = vUrl.replace(new RegExp("&limit.*$","g"), "");
							
							$.ajax({
								  type: 'GET',
								  async: false,
								  url: vUrl,
								  data: { limit: widget.options.rowOnPage, skip: v_skip }
								}).done(function( data ) {
								  $(widget.options.divTableClass).html($($.parseHTML(data)).find(widget.options.divTableClass).html());
								});
						},
						_changeNoTask: function(currentPageNumber) {
							var widget = this;
							if($(this.options.divTableClassChangeNo +' table').html() != undefined){
								var no = 1;
								$(this.options.divTableClassChangeNo +' table tr').each(function(index){
									if(index != 0){
										
										no = $(this).children().first().text();
//										alert(no);
//										alert(widget.options.numberPageShow);
										$(this).children().first().text(1*no + (currentPageNumber-1) * widget.options.rowOnPage);
									}
								});
							}
						},
						_pageClickHandler : function() {
							var widget = this;
							$(this.options.aClass)
									.bind(
											'click',
											function() {
												
												// get value of a
												var pNumber = 1 * $(this)
														.text();
												// set current page
												$(widget.options.idCurrentPage)
														.val(pNumber);
												// remove old active
												$('.'+ widget.options.activeClass)
														.removeClass(
																widget.options.activeClass);
												$(this).html('<img alt="Page Loading" src="images/page-loading.gif" style="left: -2px;position: relative; top: 2px; width: 10px; height: 10px;" />');
												
												widget._ajaxLoadData((pNumber-1) * widget.options.rowOnPage);
												if(pNumber < 10){
													$(this).html("&nbsp;&nbsp;"+pNumber);
												}else{
													$(this).html(pNumber);
												}
												
												// change page active
												widget._changeActivePage();
												widget._disableForwardControlHandler();
												widget._disableBackwardControlHandler();
												widget._changeNoTask(pNumber);

											});

						},
						_disableForwardControlHandler : function() {
							var pSize = $(this.options.pWrapClass).children()
									.size();

							var currentPage = $('.' + this.options.activeClass)
									.text();
							var backgroudColor = this.options.backgroudColorEnable;
							if (pSize == currentPage) {
								backgroudColor = this.options.backgroudColorDisable;
							} 
							$(this.options.nextClass).children().first().css(
									'background-color', backgroudColor);
							$(this.options.lastClass).children().first().css(
									'background-color', backgroudColor);
						},
						_disableBackwardControlHandler : function() {

							var currentPage = $('.' + this.options.activeClass)
									.text();
							var backgroudColor = this.options.backgroudColorEnable;
							if (currentPage == 1) {
								backgroudColor = this.options.backgroudColorDisable;
							}
							$(this.options.firstClass).children().first().css(
									'background-color', backgroudColor);
							$(this.options.prevClass).children().first().css(
									'background-color', backgroudColor);
						},
						_nextClickHandler : function() {
							var widget = this;
							$(this.options.nextClass).bind(
									'click',
									function() {
										$('.' + widget.options.activeClass)
												.parent().next().children()
												.first().click();
										widget._scrollPageWrap(-1);
									});

						},
						_lastClickHandler : function() {
							var widget = this;
							$(this.options.lastClass).bind(
									'click',
									function() {
										$(widget.options.pWrapClass).children()
												.last().children().first()
												.click();
										widget._scrollPageWrap(-1);
									});

						},
						_prevClickHandler : function() {
							var widget = this;
							$(this.options.prevClass).bind(
									'click',
									function() {
										$('.' + widget.options.activeClass)
												.parent().prev().children()
												.first().click();
										widget._scrollPageWrap(1);
									});

						},
						_firstClickHandler : function() {
							var widget = this;
							$(this.options.firstClass).bind(
									'click',
									function() {
										$(widget.options.pWrapClass).children()
												.first().children().first()
												.click();
										widget._scrollPageWrap(1);
									});

						},
						// if offset > 0 scroll right
						// if offset < 0 scroll left
						_scrollPageWrap : function(offset) {
							var pageWith = 1 * ($(this.options.pWrapClass)
									.children().first().outerWidth(true) + this.options.pWidthOffset);
							var currentPage = 1 * $(this.options.idCurrentPage)
									.val();

							var leftValue = 0;

							var pSize = $(this.options.pWrapClass).children()
									.size();

							// change number page show
							if (pSize < this.options.numberPageShow) {
								this.options.numberPageShow = pSize;
							}

							if ((offset < 0)
									&& (currentPage
											+ this.options.numberPageShow + offset) <= pSize) {
								leftValue = pageWith * (currentPage + offset)
										* offset;
							} else if (offset < 0) {
								leftValue = pageWith
										* (pSize - this.options.numberPageShow)
										* offset
							}

							if ((offset > 0)
									&& (currentPage > this.options.numberPageShow)) {
								leftValue = (pageWith
										* (currentPage)
										- (pageWith * this.options.numberPageShow)) * (-1);

							}

							$(this.options.pWrapClass).css('left',
									leftValue + 'px');

						}
					});
})(jQuery);
